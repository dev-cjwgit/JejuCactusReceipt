package com.cjwgit.jejucactusreceipt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cjwgit.jejucactusreceipt.databinding.FragmentEditCactusBinding
import com.cjwgit.jejucactusreceipt.ui.viewmodel.EditCactusFragmentVM

class EditCactusFragment : Fragment() {
    private var _binding: FragmentEditCactusBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditCactusFragmentVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCactusBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.title = "제주농원 영수증 편집 화면"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}