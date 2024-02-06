package com.cjwgit.jejucactusreceipt.ui.viewmodel.layout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjwgit.jejucactusreceipt.domain.CactusBasketVO
import com.cjwgit.jejucactusreceipt.model.CactusBasketModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class CactusPrintUiState {
    data object Nothing : CactusPrintUiState()
    data class Print(val items: ArrayList<CactusBasketVO>) : CactusPrintUiState()


}

class CactusPrintFormVM(
    private val cactusBasketModel: CactusBasketModel
) : ViewModel() {

    private val _uiState = MutableLiveData<CactusPrintUiState>()
    val uiState: LiveData<CactusPrintUiState> get() = _uiState


    private val _nowTime = MutableLiveData<String>()
    val nowTime: LiveData<String> get() = _nowTime


    private val _totalBoxCount = MutableLiveData<Long>()
    val totalBoxCount: LiveData<Long> get() = _totalBoxCount

    private val _totalPrice = MutableLiveData<Long>()
    val totalPriceCount: LiveData<Long> get() = _totalPrice

    // TODO CJW WORK : Model에서 가져와야 한다.
    private val basketList: ArrayList<CactusBasketVO> = arrayListOf()

    // TODO CJW WORK : Model에서 가져와야 한다.
    fun init(basketList: ArrayList<CactusBasketVO>) {
        this.basketList.clear()
        this.basketList.addAll(basketList)

        calcBasketList()
    }


    private fun resetUiState() {
        _uiState.value = CactusPrintUiState.Nothing
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
            var totalPrice = 0L
            for (item in basketList) {
                totalBoxCount += item.boxCount
                totalPrice += item.total
            }
            _totalBoxCount.value = totalBoxCount
            _totalPrice.value = totalPrice


            _uiState.value = CactusPrintUiState.Print(basketList)
        } finally {
            resetUiState()
        }
    }
}