package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cjwgit.jejucactusreceipt.domain.CactusBasketVO
import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.exec.CactusException
import com.cjwgit.jejucactusreceipt.exec.ErrorMessage
import com.cjwgit.jejucactusreceipt.model.common.BasketModel
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.DialButtonVM
import kotlinx.coroutines.launch
import java.text.DecimalFormat

sealed class CactusFragmentUiState {
    data object Nothing : CactusFragmentUiState()
    data object PrintBasket : CactusFragmentUiState()

    data class ShowMessage(val message: String) : CactusFragmentUiState()
    data class SetCactusList(val data: List<CactusEntity>) : CactusFragmentUiState()
    data class SetBasketList(val items: List<CactusBasketVO>) : CactusFragmentUiState()
}

class CactusFragmentVM(
    private val basketModel: BasketModel<CactusBasketVO>
) : DialButtonVM() {
    private val _uiState = MutableLiveData<CactusFragmentUiState>()
    val uiState: LiveData<CactusFragmentUiState> get() = _uiState

    // 현재 입력된 박스 수량
    private val _countText = MutableLiveData("")
    val countText: LiveData<String> = _countText

    private val _selectItemNameText = MutableLiveData("")
    val selectItemNameText: LiveData<String> = _selectItemNameText

    private val _selectItemPriceText = MutableLiveData("")
    val selectItemPriceText: LiveData<String> = _selectItemPriceText

    // 장바구니에 등록된 총 박스 개수
    private val _basketTotalBoxCount = MutableLiveData(0L)
    val basketTotalBoxCount: LiveData<Long> = _basketTotalBoxCount

    // 장바구니에 등록된 총 가격
    private val _basketTotalPrice = MutableLiveData(0L)
    val basketTotalPrice: LiveData<Long> = _basketTotalPrice

    // 현재 선택된 선인장 항목
    private var selectionCactusItem: CactusEntity? = null

    fun setCactusItem(item: CactusEntity) {
        selectionCactusItem = item

        _selectItemNameText.value = item.name
        _selectItemPriceText.value = DecimalFormat("###,###").format(item.price)

    }

    fun removeBasketItem(item: CactusBasketVO) {
        basketModel.removeItem(item)

        refreshBasketAdapterItems()
    }

    private fun getCactusList(): List<CactusEntity> {
        return listOf(
            CactusEntity(0, "커다란 용심목 씨앗", 10000),
            CactusEntity(1, "선인장2", 20000),
            CactusEntity(2, "선인장3", 30000),
        )
    }

    fun init() {
        viewModelScope.launch(exceptionHandler) {
            try {
                _uiState.value = CactusFragmentUiState.SetCactusList(getCactusList())
            } finally {
                resetUiState()
            }
        }
    }

    private fun refreshBasketAdapterItems() {
        _basketTotalBoxCount.value = basketModel.getTotalBoxCount()
        _basketTotalPrice.value = basketModel.getTotalPrice()

        _uiState.value = CactusFragmentUiState.SetBasketList(basketModel.getItems())
    }

    private fun resetSelection() {
        selectionCactusItem = null
        _selectItemNameText.value = ""
        _selectItemPriceText.value = ""

        _countText.value = ""
    }

    fun resetUiState() {
        _uiState.postValue(CactusFragmentUiState.Nothing)
    }

    fun clearBasket() {
        try {
            basketModel.clear()
            refreshBasketAdapterItems()
        } finally {
            resetUiState()
        }
    }

    fun print() {
        try {
            _uiState.value = CactusFragmentUiState.PrintBasket
        } finally {
            resetUiState()
        }
    }

    override fun click(number: Int) {
        try {
            println("숫자 클릭 : $number")
            val currentText = _countText.value ?: ""
            if (currentText.length >= 5) {
                _uiState.value = CactusFragmentUiState.ShowMessage("수량이 너무 많습니다.")
                return
            }

            if (currentText.isEmpty() && number == 0) {
                _uiState.value = CactusFragmentUiState.ShowMessage("0으로 시작 할 수 없습니다.")
            } else {
                _countText.value = currentText + number.toString()
            }
        } finally {
            resetUiState()
        }
    }

    override fun click(command: String) {
        println("문자 클릭 $command")
        viewModelScope.launch(exceptionHandler) {
            try {
                when (command) {
                    "D" -> {
                        // DELETE
                        val currentText = _countText.value ?: ""
                        if (currentText.isNotEmpty()) {
                            _countText.value = currentText.substring(0, currentText.length - 1)
                        }
                    }

                    "E" -> {
                        // ENTER
                        if (selectionCactusItem == null) {
                            throw CactusException(ErrorMessage.NOT_SELECT_ITEM)
                        }

                        if (countText.value?.length!! <= 0) {
                            throw CactusException(ErrorMessage.NEED_INPUT_AMOUNT)
                        }

                        if (basketModel.getSize() >= 24) {
                            throw CactusException(ErrorMessage.EXCEED_ITEM_COUNT)
                        }

                        val cactus = selectionCactusItem!!
                        val price = cactus.price
                        val count = countText.value!!.toLong()
                        val total = price * count
                        val item = CactusBasketVO(
                            cactus.uid,
                            cactus.name,
                            cactus.price,
                            count,
                            total
                        )
                        basketModel.addItem(item)

                        refreshBasketAdapterItems()
                        resetSelection()
                    }
                }
            } finally {
                resetUiState()
            }
        }
    }

    override fun handleException(exception: CactusException) {
        when (exception.errorMessage.code) {
            ErrorMessage.NOT_SELECT_ITEM.code -> {
                _uiState.value = CactusFragmentUiState.ShowMessage("항목을 선택해 주세요.")
            }

            ErrorMessage.EXCEED_ITEM_COUNT.code -> {
                _uiState.value = CactusFragmentUiState.ShowMessage("더 이상은 담을 수 없습니다.")
            }

            ErrorMessage.NEED_INPUT_AMOUNT.code -> {
                _uiState.value = CactusFragmentUiState.ShowMessage("수량을 입력해 주세요.")
            }

            else -> {

            }
        }
    }
}