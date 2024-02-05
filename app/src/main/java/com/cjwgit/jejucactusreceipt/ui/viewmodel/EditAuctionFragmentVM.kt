package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditAuctionFragmentVM : ViewModel() {

    val nameEditText = MutableLiveData<String>()
    val amountEditText = MutableLiveData<String>()
    val priceEditText = MutableLiveData<String>()

}