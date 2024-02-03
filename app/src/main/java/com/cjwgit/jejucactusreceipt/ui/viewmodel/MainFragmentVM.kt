package com.cjwgit.jejucactusreceipt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cjwgit.jejucactusreceipt.domain.CactusEntity
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.DialButtonVM
import kotlinx.coroutines.launch
import java.text.DecimalFormat

sealed class MainFragmentUiState {
    data object Nothing : MainFragmentUiState()
    data class ShowMessage(val message: String) : MainFragmentUiState()
    data class SetCactusList(val data: List<CactusEntity>) : MainFragmentUiState()
}

class MainFragmentVM : DialButtonVM() {
    private val _uiState = MutableLiveData<MainFragmentUiState>()
    val uiState: LiveData<MainFragmentUiState> get() = _uiState

    private val _countText = MutableLiveData("")
    val countText: LiveData<String> = _countText

    private val _selectItemNameText = MutableLiveData("")
    val selectItemNameText: LiveData<String> = _selectItemNameText

    private val _selectItemPriceText = MutableLiveData("")
    val selectItemPriceText: LiveData<String> = _selectItemPriceText

    private var selectionCactusItem: CactusEntity? = null

    fun setCactusItem(item: CactusEntity) {
        selectionCactusItem = item

        _selectItemNameText.value = item.name
        _selectItemPriceText.value = DecimalFormat("###,###").format(item.price)

    }

    private fun getCactusList(): List<CactusEntity> {
        return listOf(
            CactusEntity(0, "선인장1", 10000),
            CactusEntity(1, "선인장2", 20000),
            CactusEntity(2, "선인장3", 30000),
        )
    }

    fun init() {
        viewModelScope.launch {
            _uiState.postValue(MainFragmentUiState.SetCactusList(getCactusList()))
        }
    }

    override fun click(number: Int) {
        println("숫자 클릭 : $number")
        val currentText = _countText.value ?: ""
        if (currentText.length >= 5) {
            _uiState.value = MainFragmentUiState.ShowMessage("수량이 너무 많습니다.")
            return
        }

        if (currentText.isEmpty() && number == 0) {
            _uiState.value = MainFragmentUiState.ShowMessage("0으로 시작 할 수 없습니다.")
        } else {
            _countText.value = currentText + number.toString()
        }
    }

    override fun click(command: String) {
        println("문자 클릭 $command")
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
                    _uiState.value = MainFragmentUiState.ShowMessage("항목을 선택해 주세요.")
                    return
                }

                if (countText.value?.length!! <= 0) {
                    _uiState.value = MainFragmentUiState.ShowMessage("수량을 입력해 주세요.")
                    return
                }



                resetSelection()
            }
        }
    }

    private fun resetSelection() {
        selectionCactusItem = null
        _selectItemNameText.value = ""
        _selectItemPriceText.value = ""

        _countText.value = ""
    }

    fun resetUiState() {
        _uiState.value = MainFragmentUiState.Nothing
    }
}