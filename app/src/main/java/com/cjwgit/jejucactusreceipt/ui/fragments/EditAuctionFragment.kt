package com.cjwgit.jejucactusreceipt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjwgit.jejucactusreceipt.databinding.FragmentEditAuctionBinding
import com.cjwgit.jejucactusreceipt.ui.adapter.CactusAuctionRecyclerViewAdapter
import com.cjwgit.jejucactusreceipt.ui.viewmodel.EditAuctionFragmentVM
import org.koin.android.ext.android.inject

class EditAuctionFragment : Fragment() {
    private var _binding: FragmentEditAuctionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditAuctionFragmentVM by inject()
    private val cactusRecyclerViewAdapter by lazy {
        CactusAuctionRecyclerViewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAuctionBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.cactusRecyclerViewAdapter = cactusRecyclerViewAdapter
        binding.lifecycleOwner = viewLifecycleOwner

        init()

        return binding.root
    }

    private fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val cactusRecyclerView = binding.cactusRecyclerView
        cactusRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cactusRecyclerView.animation = null
        cactusRecyclerView.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        activity?.title = "제주농원 경매장 편집 화면"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}