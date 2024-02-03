package com.cjwgit.jejucactusreceipt.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

// region 더블 클릭을 방지 해주는 바인딩 아답터
@BindingAdapter("android:onClickPrevent")
fun setOnClickPrevent(view: View, clickListener: View.OnClickListener?) {
    view.setOnClickListener { v ->
        if (clickListener != null && !isDoubleClick()) {
            clickListener.onClick(v)
        }
    }
}

private var lastClickTime: Long = 0
private const val doubleClickInterval: Long = 150 // 더블 클릭 간격 (밀리초)

fun isDoubleClick(): Boolean {
    val currentTime = System.currentTimeMillis()
    val elapsedTime = currentTime - lastClickTime
    if(elapsedTime >= doubleClickInterval)
        lastClickTime = currentTime
    return elapsedTime < doubleClickInterval
}

// endregion

// region Int Text를 ###,### 포맷팅 하는 바인딩 아답터
@BindingAdapter("android:formattedText")
fun setFormattedText(view: TextView, value: Int) {
    val formattedValue = DecimalFormat("###,###").format(value)
    view.text = formattedValue
}
