package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cjwgit.jejucactusreceipt.domain.CactusAuctionBasketVO
import com.cjwgit.jejucactusreceipt.domain.CactusAuctionEntity
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.DialButtonVM
import kotlinx.coroutines.launch
import java.text.DecimalFormat

sealed class AuctionFragmentUiState {
    data object Nothing : AuctionFragmentUiState()
    data object ClearBasketList : AuctionFragmentUiState()

    data class ShowMessage(val message: String) : AuctionFragmentUiState()
    data class SetCactusList(val data: List<CactusAuctionEntity>) : AuctionFragmentUiState()
    data class AddBasketCactus(val data: CactusAuctionBasketVO) : AuctionFragmentUiState()

}

class AuctionFragmentVM : DialButtonVM() {
    // 장바구니에 쌓인 아이템 개수
    private var basketCount = 0

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

    private fun getCactusList(): List<CactusAuctionEntity> {
        return listOf(
            CactusAuctionEntity(0, "선인장1", 11, 10000),
            CactusAuctionEntity(1, "선인장2", 6, 20000),
            CactusAuctionEntity(2, "선인장3", 14, 30000),
        )
    }


    fun init() {
        try {
            viewModelScope.launch {
                _uiState.postValue(AuctionFragmentUiState.SetCactusList(getCactusList()))
            }
        } finally {
            resetUiState()
        }
    }


    fun setCactusItem(item: CactusAuctionEntity) {
        selectionCactusItem = item

        _selectItemNameText.value = item.name
        _selectItemPriceText.value = DecimalFormat("###,###").format(item.price)

    }


    fun clearBasket() {
        try {
            _uiState.value = AuctionFragmentUiState.ClearBasketList
            _basketTotalBoxCount.value = 0
            basketCount = 0
        } finally {
            resetUiState()
        }
    }

    fun resetUiState() {
        _uiState.value = AuctionFragmentUiState.Nothing
    }

    fun removeBasketItem(item: CactusAuctionBasketVO) {
        val currentTotalBoxCount = _basketTotalBoxCount.value!!

        _basketTotalBoxCount.value = currentTotalBoxCount - item.boxCount

        basketCount -= 1
    }

    private fun resetSelection() {
        selectionCactusItem = null
        _selectItemNameText.value = ""
        _selectItemPriceText.value = ""

        _countText.value = ""
    }

    fun clearViewData() {
        clearBasket()
        resetSelection()
        resetUiState()
    }

    fun print() {

    }

    override fun click(number: Int) {
        println("숫자 클릭 : $number")
        try {
            val currentText = _countText.value ?: ""
            if (currentText.length >= 5) {
                _uiState.value = AuctionFragmentUiState.ShowMessage("수량이 너무 많습니다.")
                return
            }

            if (currentText.isEmpty() && number == 0) {
                _uiState.value = AuctionFragmentUiState.ShowMessage("0으로 시작 할 수 없습니다.")
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
                        _uiState.value = AuctionFragmentUiState.ShowMessage("항목을 선택해 주세요.")
                        return
                    }

                    if (countText.value?.length!! <= 0) {
                        _uiState.value = AuctionFragmentUiState.ShowMessage("수량을 입력해 주세요.")
                        return
                    }

                    val cactus = selectionCactusItem!!
                    val count = countText.value!!.toInt()
                    _uiState.value = AuctionFragmentUiState.AddBasketCactus(
                        CactusAuctionBasketVO(
                            cactus.uid,
                            cactus.name,
                            cactus.amount,
                            count,
                            cactus.price
                        )
                    )

                    val currentTotalBoxCount = _basketTotalBoxCount.value!!

                    _basketTotalBoxCount.value = currentTotalBoxCount + count

                    basketCount += 1

                    resetSelection()
                }
            }
        } finally {
            resetUiState()
        }
    }

}