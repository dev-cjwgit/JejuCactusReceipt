package com.cjwgit.jejucactusreceipt.ui.viewmodel.layout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjwgit.jejucactusreceipt.domain.AuctionBasketVO
import com.cjwgit.jejucactusreceipt.model.common.BasketModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class AuctionPrintFormUiState {
    data object Nothing : AuctionPrintFormUiState()
    data class PrintForm(val items: ArrayList<AuctionBasketVO>) : AuctionPrintFormUiState()


}

class AuctionPrintFormVM(
    private val auctionBasketModel: BasketModel<AuctionBasketVO>
) : ViewModel() {

    private val _uiState = MutableLiveData<AuctionPrintFormUiState>()
    val uiState: LiveData<AuctionPrintFormUiState> get() = _uiState


    private val _nowTime = MutableLiveData<String>()
    val nowTime: LiveData<String> get() = _nowTime


    private val _totalBoxCount = MutableLiveData<Long>()
    val totalBoxCount: LiveData<Long> get() = _totalBoxCount

    // TODO CJW WORK : Model에서 가져와야 한다.
    private val basketList: ArrayList<AuctionBasketVO> = arrayListOf()

    // TODO CJW WORK : Model에서 가져와야 한다.
    fun init(basketList: ArrayList<AuctionBasketVO>) {
        this.basketList.clear()
        this.basketList.addAll(basketList)

        calcBasketList()
    }


    private fun resetUiState() {
        _uiState.value = AuctionPrintFormUiState.Nothing
    }

    private fun calcBasketList() {
        try {
            // 로케일을 기본 로케일로 설정
            val locale = Locale.getDefault()
            // SimpleDateFormat을 사용하여 날짜 및 시간 형식화
            val dateFormat = SimpleDateFormat("yyyy년MM월dd일 HH시mm분ss초", locale)
            val formattedDate = dateFormat.format(Date())

            _nowTime.value = formattedDate
            var totalBoxCount = 0L
            for (item in basketList) {
                totalBoxCount += item.boxCount
            }
            _totalBoxCount.value = totalBoxCount


            _uiState.value = AuctionPrintFormUiState.PrintForm(basketList)
        } finally {
            resetUiState()
        }
    }
}