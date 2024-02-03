package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cjwgit.jejucactusreceipt.domain.CactusBasketVO
import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.DialButtonVM
import kotlinx.coroutines.launch
import java.text.DecimalFormat

sealed class MainFragmentUiState {
    data object Nothing : MainFragmentUiState()

    data object ClearBasketList : MainFragmentUiState()
    data class ShowMessage(val message: String) : MainFragmentUiState()
    data class SetCactusList(val data: List<CactusEntity>) : MainFragmentUiState()
    data class AddBasketCactus(val data: CactusBasketVO) : MainFragmentUiState()
}

class MainFragmentVM : DialButtonVM() {
    // 장바구니에 쌓인 아이템 개수
    private var basketCount = 0

    private val _uiState = MutableLiveData<MainFragmentUiState>()
    val uiState: LiveData<MainFragmentUiState> get() = _uiState

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
        val currentTotalBoxCount = _basketTotalBoxCount.value!!
        val currentTotalPrice = _basketTotalPrice.value!!

        _basketTotalBoxCount.value = currentTotalBoxCount - item.boxCount
        _basketTotalPrice.value = currentTotalPrice - item.total

        basketCount -= 1
    }

    private fun getCactusList(): List<CactusEntity> {
        return listOf(
            CactusEntity(0, "선인장1", 10000),
            CactusEntity(1, "선인장2", 20000),
            CactusEntity(2, "선인장3", 30000),
        )
    }

    fun init() {
        try {
            viewModelScope.launch {
                _uiState.postValue(MainFragmentUiState.SetCactusList(getCactusList()))
            }
        } finally {
            resetUiState()
        }
    }


    private fun resetSelection() {
        selectionCactusItem = null
        _selectItemNameText.value = ""
        _selectItemPriceText.value = ""

        _countText.value = ""
    }

    fun resetUiState() {
        _uiState.value = MainFragmentUiState.Nothing
    }

    fun clearBasket() {
        try {
            _uiState.value = MainFragmentUiState.ClearBasketList
            _basketTotalPrice.value = 0
            _basketTotalBoxCount.value = 0
            basketCount = 0
        } finally {
            resetUiState()
        }
    }

    fun clearViewData() {
        clearBasket()
        resetSelection()
        resetUiState()
    }


    override fun click(number: Int) {
        try {
            println("숫자 클릭 : $number")
            val currentText = _countText.value ?: ""
            if (currentText.length >= 5) {
                _uiState.value = MainFragmentUiState.ShowMessage("수량이 너무 많습니다.")
                return
            }

            if (currentText.isEmpty() && number == 0) {
                _uiState.value = MainFragmentUiState.ShowMessage("0으로 시작 할 수 없습니다.")
            } else {
                _countText.value = currentText + number.toString()
            }
        } finally {
            resetUiState()
        }
    }

    override fun click(command: String) {
        println("문자 클릭 $command")
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
                        _uiState.value = MainFragmentUiState.ShowMessage("항목을 선택해 주세요.")
                        return
                    }

                    if (countText.value?.length!! <= 0) {
                        _uiState.value = MainFragmentUiState.ShowMessage("수량을 입력해 주세요.")
                        return
                    }

                    val cactus = selectionCactusItem!!
                    val price = cactus.price
                    val count = countText.value!!.toInt()
                    val total = price * count.toLong()
                    _uiState.value = MainFragmentUiState.AddBasketCactus(
                        CactusBasketVO(
                            cactus.uid,
                            cactus.name,
                            cactus.price,
                            count,
                            total
                        )
                    )

                    val currentTotalBoxCount = _basketTotalBoxCount.value!!
                    val currentTotalPrice = _basketTotalPrice.value!!

                    _basketTotalBoxCount.value = currentTotalBoxCount + count
                    _basketTotalPrice.value = currentTotalPrice + total

                    basketCount += 1

                    resetSelection()
                }
            }
        } finally {
            resetUiState()
        }

    }
}