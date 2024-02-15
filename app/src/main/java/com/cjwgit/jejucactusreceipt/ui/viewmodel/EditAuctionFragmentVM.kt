package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cjwgit.jejucactusreceipt.domain.AuctionEntity
import com.cjwgit.jejucactusreceipt.exec.CactusException
import com.cjwgit.jejucactusreceipt.model.AuctionProductModel
import com.cjwgit.jejucactusreceipt.ui.viewmodel.common.BaseViewModel
import kotlinx.coroutines.launch

sealed class EditAuctionFragmentUiState {
    data object Nothing : EditAuctionFragmentUiState()
    data class SetCactusList(val data: List<AuctionEntity>) : EditAuctionFragmentUiState()

    data class ShowMessage(val message: String) : EditAuctionFragmentUiState()
}

class EditAuctionFragmentVM(
    private val auctionModel: AuctionProductModel
) : BaseViewModel() {
    private val _uiState = MutableLiveData<EditAuctionFragmentUiState>()
    val uiState: LiveData<EditAuctionFragmentUiState> get() = _uiState

    val nameEditText = MutableLiveData<String>()
    val amountEditText = MutableLiveData<String>()
    val priceEditText = MutableLiveData<String>()

    fun init() {
        viewModelScope.launch(exceptionHandler) {
            try {
                _uiState.value = EditAuctionFragmentUiState.SetCactusList(
                    auctionModel.getItems()
                )
            } finally {
                resetUiState()
            }
        }
    }

    fun swipe(from: Int, to: Int) {
        viewModelScope.launch(exceptionHandler) {
            try {
                auctionModel.swipe(from, to)

                refreshAdapter()
            } finally {
                resetUiState()
            }
        }
    }

    fun removeItem(position: Int) {
        viewModelScope.launch(exceptionHandler) {
            try {
                auctionModel.removeItem(position)

                refreshAdapter()
            } finally {
                resetUiState()
            }
        }
    }

    override fun handleException(exception: CactusException) {
        when (exception.errorMessage.code) {
            else -> {
                _uiState.value = exception.message?.let { EditAuctionFragmentUiState.ShowMessage(it) }
            }
        }
    }

    private fun refreshAdapter() {
        _uiState.value = EditAuctionFragmentUiState.SetCactusList(auctionModel.getItems())
    }

    private fun resetUiState() {
        _uiState.postValue(EditAuctionFragmentUiState.Nothing)
    }
}