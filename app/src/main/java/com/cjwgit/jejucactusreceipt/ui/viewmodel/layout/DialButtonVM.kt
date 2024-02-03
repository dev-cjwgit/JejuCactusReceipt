package com.cjwgit.jejucactusreceipt.ui.viewmodel.layout

import androidx.lifecycle.ViewModel

abstract class DialButtonVM : ViewModel() {
    abstract fun click(number: Int)

    abstract fun click(command: String)
    fun clickButton(number: Int) {
        click(number)
    }

    fun clickButton(command: String) {
        click(command)
    }
}