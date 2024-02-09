package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cjwgit.jejucactusreceipt.domain.AuctionBasketVO
import com.cjwgit.jejucactusreceipt.domain.CactusAuctionEntity
import com.cjwgit.jejucactusreceipt.exec.CactusException
import com.cjwgit.jejucactusreceipt.exec.ErrorMessage
import com.cjwgit.jejucactusreceipt.model.common.BasketBaseModel
import com.cjwgit.jejucactusreceipt.model.common.BasketModel
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.DialButtonVM
import kotlinx.coroutines.launch
import java.text.DecimalFormat

sealed class AuctionFragmentUiState {
    data object Nothing : AuctionFragmentUiState()
    data object PrintBasket : AuctionFragmentUiState()

    data class ShowMessage(val message: String) : AuctionFragmentUiState()
    data class SetCactusList(val data: List<CactusAuctionEntity>) : AuctionFragmentUiState()
    data class SetBasketList(val items: List<AuctionBasketVO>) : AuctionFragmentUiState()
}

class AuctionFragmentVM(
    private val basketModel: BasketModel<AuctionBasketVO>
) : DialButtonVM() {
    private val _uiState = MutableLiveData<AuctionFragmentUiState>()
    val uiState: LiveData<AuctionFragmentUiState> get() = _uiState

    // 장바구니에 등록된 총 박스 개수
    private val _basketTotalBoxCount = MutableLiveData(0L)
    val basketTotalBoxCount: LiveData<Long> = _basketTotalBoxCount

    // 현재 입력된 박스 수량
    private val _countText = MutableLiveData("")
    val countText: LiveData<String> = _countText

    private val _selectItemNameText = MutableLiveData("")
    val selectItemNameText: LiveData<String> = _selectItemNameText

    private val _selectItemPriceText = MutableLiveData("")
    val selectItemPriceText: LiveData<String> = _selectItemPriceText

    // 현재 선택된 선인장 항목
    private var selectionCactusItem: CactusAuctionEntity? = null


    fun setCactusItem(item: CactusAuctionEntity) {
        viewModelScope.launch(exceptionHandler) {
            selectionCactusItem = item

            _selectItemNameText.value = item.name
            _selectItemPriceText.value = DecimalFormat("###,###").format(item.price)
        }
    }

    fun removeBasketItem(item: AuctionBasketVO) {
        viewModelScope.launch(exceptionHandler) {
            basketModel.removeItem(item)
            refreshBasketAdapterItems()
        }
    }

    private fun getCactusList(): List<CactusAuctionEntity> {
        return listOf(
            CactusAuctionEntity(0, "선인장1", 11, 10000),
            CactusAuctionEntity(1, "선인장2", 6, 20000),
            CactusAuctionEntity(2, "선인장3", 14, 30000),
        )
    }


    fun init() {
        viewModelScope.launch(exceptionHandler) {
            try {
                _uiState.postValue(AuctionFragmentUiState.SetCactusList(getCactusList()))
            } finally {
                resetUiState()
            }
        }
    }

    fun resetUiState() {
        _uiState.value = AuctionFragmentUiState.Nothing
    }


    private fun refreshBasketAdapterItems() {
        _basketTotalBoxCount.value = basketModel.getTotalBoxCount()

        _uiState.value = AuctionFragmentUiState.SetBasketList(basketModel.getItems())
    }

    private fun resetSelection() {
        selectionCactusItem = null
        _selectItemNameText.value = ""
        _selectItemPriceText.value = ""

        _countText.value = ""
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
            _uiState.value = AuctionFragmentUiState.PrintBasket
        } finally {
            resetUiState()
        }
    }

    override fun click(number: Int) {
        println("숫자 클릭 : $number")
        viewModelScope.launch(exceptionHandler) {
            try {
                val currentText = _countText.value ?: ""
                if (currentText.length >= 5) {
                    throw CactusException(ErrorMessage.EXCEED_ITEM_AMOUNT)
                }

                if (currentText.isEmpty() && number == 0)
                    throw CactusException(ErrorMessage.NOT_START_INPUT_0)

                _countText.value = currentText + number.toString()
            } finally {
                resetUiState()
            }
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
                            throw CactusException(ErrorMessage.EXCEED_ITEM_COUNT)
                        }


                        if (basketModel.getSize() >= BasketBaseModel.MAX_ITEM_SIZE) {
                            throw CactusException(ErrorMessage.EXCEED_ITEM_COUNT)
                        }

                        val cactus = selectionCactusItem!!
                        val count = countText.value!!.toLong()

                        val item = AuctionBasketVO(
                            cactus.uid,
                            cactus.name,
                            cactus.amount,
                            count,
                            cactus.price
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
        try {
            when (exception.errorMessage.code) {
                else -> {
                    _uiState.value = exception.message?.let { AuctionFragmentUiState.ShowMessage(it) }
                }
            }
        } finally {
            resetUiState()
        }
    }

}