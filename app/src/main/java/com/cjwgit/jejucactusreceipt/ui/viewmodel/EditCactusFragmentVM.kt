package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditCactusFragmentVM : ViewModel() {

    val nameEditText = MutableLiveData<String>()
    val priceEditText = MutableLiveData<String>()


}