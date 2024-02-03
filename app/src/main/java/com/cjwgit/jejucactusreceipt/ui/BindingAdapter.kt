package com.cjwgit.jejucactusreceipt.ui

import android.view.View
import androidx.databinding.BindingAdapter

// 더블 클릭을 방지 해주는 바인딩 아답터
@BindingAdapter("android:onClickPrevent")
fun setOnClickPrevent(view: View, clickListener: View.OnClickListener?) {
    view.setOnClickListener { v ->
        if (clickListener != null && !isDoubleClick()) {
            clickListener.onClick(v)
        }
    }
}

private var lastClickTime: Long = 0
private const val doubleClickInterval: Long = 200 // 더블 클릭 간격 (밀리초)

fun isDoubleClick(): Boolean {
    val currentTime = System.currentTimeMillis()
    val elapsedTime = currentTime - lastClickTime
    if(elapsedTime >= doubleClickInterval)
        lastClickTime = currentTime
    return elapsedTime < doubleClickInterval
}
