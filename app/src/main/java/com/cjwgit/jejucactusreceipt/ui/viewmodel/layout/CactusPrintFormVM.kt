package com.cjwgit.jejucactusreceipt.ui.viewmodel.layout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjwgit.jejucactusreceipt.domain.CactusBasketVO
import com.cjwgit.jejucactusreceipt.model.CactusBasketModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CactusPrintFormVM(
    private val cactusBasketModel: CactusBasketModel
) : ViewModel() {
    private val _basketItems = MutableLiveData<List<CactusBasketVO>>()
    val basketItems: LiveData<List<CactusBasketVO>> get() = _basketItems

    private val _nowTime = MutableLiveData<String>()
    val nowTime: LiveData<String> get() = _nowTime

    private val _totalBoxCount = MutableLiveData<Long>()
    val totalBoxCount: LiveData<Long> get() = _totalBoxCount

    private val _totalPrice = MutableLiveData<Long>()
    val totalPriceCount: LiveData<Long> get() = _totalPrice

    fun init() {
        loadPrintDataByModel()
    }

    private fun getNowDate(): String {
        // 로케일을 기본 로케일로 설정
        val locale = Locale.getDefault()
        // SimpleDateFormat을 사용하여 날짜 및 시간 형식화
        val dateFormat = SimpleDateFormat("yyyy년MM월dd일 HH시mm분ss초", locale)
        return dateFormat.format(Date())
    }

    private fun loadPrintDataByModel() {
        _basketItems.value = cactusBasketModel.getItemsToPadding()

        _nowTime.value = getNowDate()

        _totalBoxCount.value = cactusBasketModel.getTotalBoxCount()
        _totalPrice.value = cactusBasketModel.getTotalPrice()
    }
}