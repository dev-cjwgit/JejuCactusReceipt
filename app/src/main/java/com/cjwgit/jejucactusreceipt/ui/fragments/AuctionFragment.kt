package com.cjwgit.jejucactusreceipt.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjwgit.jejucactusreceipt.databinding.FragmentAuctionBinding
import com.cjwgit.jejucactusreceipt.ui.adapter.CactusAuctionBasketRecyclerViewAdapter
import com.cjwgit.jejucactusreceipt.ui.adapter.CactusAuctionRecyclerViewAdapter
import com.cjwgit.jejucactusreceipt.ui.dialog.NotificationDialog
import com.cjwgit.jejucactusreceipt.ui.layout.AuctionPrintFormLayout
import com.cjwgit.jejucactusreceipt.ui.viewmodel.AuctionFragmentUiState
import com.cjwgit.jejucactusreceipt.ui.viewmodel.AuctionFragmentVM
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AuctionFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentAuctionBinding? = null

    private val viewModel: AuctionFragmentVM by inject()

    companion object {
        private val cactusRecyclerViewAdapter by lazy {
            CactusAuctionRecyclerViewAdapter()
        }

        private val cactusBasketRecyclerViewAdapter by lazy {
            CactusAuctionBasketRecyclerViewAdapter()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuctionBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.cactusRecyclerViewAdapter = cactusRecyclerViewAdapter

        cactusRecyclerViewAdapter.setOnClickListener { clickItem ->
            viewModel.setCactusItem(clickItem)
        }

        binding.basketRecyclerViewAdapter = cactusBasketRecyclerViewAdapter
        cactusBasketRecyclerViewAdapter.setOnRemoveClickListener { removeItem ->
            viewModel.removeBasketItem(removeItem)
        }

        binding.lifecycleOwner = viewLifecycleOwner
        init()

        activity?.title = "제주농원 경매장 화면"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is AuctionFragmentUiState.ShowMessage -> {
                            showMessage(state.message)
                        }

                        is AuctionFragmentUiState.SetCactusList -> {
                            cactusRecyclerViewAdapter.initData(state.data.toMutableList())
                        }

                        is AuctionFragmentUiState.PrintBasket -> {
                            printBasket()
                        }

                        is AuctionFragmentUiState.SetBasketList -> {
                            cactusBasketRecyclerViewAdapter.initData(state.items.toMutableList())
                        }

                        else -> {

                        }
                    }
                }
            }
        }

        viewModel.init()
    }

    private fun printBasket() {
        val intent = Intent(requireContext(), AuctionPrintFormLayout::class.java)
        intent.putParcelableArrayListExtra(
            "items",
            ArrayList(cactusBasketRecyclerViewAdapter.getItems())
        )
        startActivity(intent)
    }

    private fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val cactusRecyclerView = binding.cactusRecyclerView
        cactusRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cactusRecyclerView.animation = null
        cactusRecyclerView.setHasFixedSize(true)


        val basketRecyclerView = binding.basketRecyclerView
        basketRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        basketRecyclerView.animation = null
        basketRecyclerView.setHasFixedSize(true)
    }

    private fun showMessage(message: String) {
        val dialog = NotificationDialog()
        val bundle = Bundle()
        bundle.putString("message", message)

        dialog.arguments = bundle
        dialog.show(requireActivity().supportFragmentManager, "NotificationDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetUiState()
        _binding = null
    }
}