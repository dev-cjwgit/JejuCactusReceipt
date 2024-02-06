package com.cjwgit.jejucactusreceipt.ui.viewmodel.common

import androidx.lifecycle.ViewModel
import com.cjwgit.jejucactusreceipt.exec.CactusException
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is CactusException -> {
                handleException(exception)
            }

            else -> {
                exception.printStackTrace()
            }
        }
    }

    abstract fun handleException(exception: CactusException)

}