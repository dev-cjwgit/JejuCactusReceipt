package com.cjwgit.jejucactusreceipt.ui.viewmodel.layout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjwgit.jejucactusreceipt.domain.AuctionBasketVO
import com.cjwgit.jejucactusreceipt.model.common.BasketModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AuctionPrintFormVM(
    private val auctionBasketModel: BasketModel<AuctionBasketVO>
) : ViewModel() {
    private val _basketItems = MutableLiveData<List<AuctionBasketVO>>()
    val basketItems: LiveData<List<AuctionBasketVO>> get() = _basketItems

    private val _nowTime = MutableLiveData<String>()
    val nowTime: LiveData<String> get() = _nowTime


    private val _totalBoxCount = MutableLiveData<Long>()
    val totalBoxCount: LiveData<Long> get() = _totalBoxCount


    fun init() {
        _basketItems.value = auctionBasketModel.getItems()
        calcBasketList()
    }

    private fun calcBasketList() {
        // 로케일을 기본 로케일로 설정
        val locale = Locale.getDefault()
        // SimpleDateFormat을 사용하여 날짜 및 시간 형식화
        val dateFormat = SimpleDateFormat("yyyy년MM월dd일 HH시mm분ss초", locale)
        val formattedDate = dateFormat.format(Date())

        _nowTime.value = formattedDate

        _totalBoxCount.value = auctionBasketModel.getTotalBoxCount()
    }
}