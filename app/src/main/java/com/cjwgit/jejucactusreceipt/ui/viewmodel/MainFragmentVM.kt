package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.DialButtonVM

sealed class MainFragmentUiState {
    data class ShowMessage(val message: String) : MainFragmentUiState()


}

class MainFragmentVM : DialButtonVM() {
    private val _uiState = MutableLiveData<MainFragmentUiState>()
    val uiState: LiveData<MainFragmentUiState> get() = _uiState

    private val _numberText = MutableLiveData("")
    val numberText: LiveData<String> = _numberText

    override fun click(number: Int) {
        println("숫자 클릭 : $number")
        val currentText = _numberText.value ?: ""
        if (currentText.length >= 5) {
            _uiState.value = MainFragmentUiState.ShowMessage("수량이 너무 많습니다.")
            return
        }

        if (currentText.isEmpty() && number == 0) {
            _uiState.value = MainFragmentUiState.ShowMessage("0으로 시작 할 수 없습니다.")
        } else {
            _numberText.value = currentText + number.toString()
        }
    }

    override fun click(command: String) {
        println("문자 클릭 $command")
        when (command) {
            "D" -> {
                // DELETE
                val currentText = _numberText.value ?: ""
                if (currentText.isNotEmpty()) {
                    _numberText.value = currentText.substring(0, currentText.length - 1)
                }
            }

            "E" -> {
                // ENTER

            }
        }
    }

}