package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.exec.CactusException
import com.cjwgit.jejucactusreceipt.model.CactusProductModel
import com.cjwgit.jejucactusreceipt.ui.viewmodel.common.BaseViewModel
import kotlinx.coroutines.launch

sealed class EditCactusFragmentUiState {
    data object Nothing : EditCactusFragmentUiState()
    data class SetCactusList(val data: List<CactusEntity>) : EditCactusFragmentUiState()

    data class ShowMessage(val message: String) : EditCactusFragmentUiState()
}

class EditCactusFragmentVM(
    private val cactusModel: CactusProductModel
) : BaseViewModel() {
    private val _uiState = MutableLiveData<EditCactusFragmentUiState>()
    val uiState: LiveData<EditCactusFragmentUiState> get() = _uiState

    val nameEditText = MutableLiveData<String>()
    val priceEditText = MutableLiveData<String>()

    // 현재 선택된 선인장 항목
    private var selectionCactusItem: CactusEntity? = null

    fun init() {
        viewModelScope.launch(exceptionHandler) {
            try {
                _uiState.value = EditCactusFragmentUiState.SetCactusList(
                    cactusModel.getItems()
                )
            } finally {
                resetUiState()
            }
        }
    }

    fun setCactusItem(item: CactusEntity) {
        viewModelScope.launch(exceptionHandler) {
            selectionCactusItem = item

            nameEditText.value = item.name
            priceEditText.value = item.price.toString()
        }
    }

    override fun handleException(exception: CactusException) {
        when (exception.errorMessage.code) {
            else -> {
                _uiState.value = exception.message?.let { EditCactusFragmentUiState.ShowMessage(it) }
            }
        }
    }

    fun swipe(from: Int, to: Int) {
        viewModelScope.launch(exceptionHandler) {
            try {
                cactusModel.swipe(from, to)

                refreshAdapter()
            } finally {
                resetUiState()
            }
        }
    }

    fun removeItem(position: Int) {
        viewModelScope.launch(exceptionHandler) {
            try {
                cactusModel.removeItem(position)

                refreshAdapter()
            } finally {
                resetUiState()
            }
        }
    }

    fun onClickAddButton() {
        viewModelScope.launch(exceptionHandler) {
            try {
                val name = nameEditText.value!!
                val price = priceEditText.value!!.toLong()

                cactusModel.addItem(CactusEntity(name, price))

                refreshAdapter()
                resetEditText()
            } finally {
                resetUiState()
            }
        }
    }

    fun onClickCancelButton() {
        viewModelScope.launch(exceptionHandler) {
            try {

            } finally {
                resetEditText()
            }
        }
    }

    private fun refreshAdapter() {
        _uiState.value = EditCactusFragmentUiState.SetCactusList(cactusModel.getItems())
    }

    private fun resetEditText() {
        nameEditText.value = ""
        priceEditText.value = ""
    }

    private fun resetUiState() {
        _uiState.postValue(EditCactusFragmentUiState.Nothing)
    }
}