package com.cjwgit.jejucactusreceipt.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cjwgit.jejucactusreceipt.databinding.DialogNotificationBinding

class NotificationDialog : DialogFragment() {
    private val binding get() = _binding!!
    private var _binding: DialogNotificationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        binding.okButton.setOnClickListener {
            dismiss()
        }

        val bundle = arguments
        bundle?.let {
            binding.messageTextView.text = it.getString("message")
        }

        // 여기서 뷰 초기화 및 이벤트 리스너 설정 등을 할 수 있습니다.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}