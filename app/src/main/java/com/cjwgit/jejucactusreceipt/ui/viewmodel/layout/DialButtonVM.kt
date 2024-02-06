package com.cjwgit.jejucactusreceipt.ui.viewmodel.layout

import com.cjwgit.jejucactusreceipt.ui.viewmodel.common.BaseViewModel

abstract class DialButtonVM : BaseViewModel() {
    abstract fun click(number: Int)

    abstract fun click(command: String)
    fun clickButton(number: Int) {
        click(number)
    }

    fun clickButton(command: String) {
        click(command)
    }
}