package com.cjwgit.jejucactusreceipt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjwgit.jejucactusreceipt.databinding.FragmentAuctionBinding
import com.cjwgit.jejucactusreceipt.ui.adapter.CactusAuctionBasketRecyclerViewAdapter
import com.cjwgit.jejucactusreceipt.ui.adapter.CactusAuctionRecyclerViewAdapter
import com.cjwgit.jejucactusreceipt.ui.dialog.NotificationDialog
import com.cjwgit.jejucactusreceipt.ui.viewmodel.AuctionFragmentUiState
import com.cjwgit.jejucactusreceipt.ui.viewmodel.AuctionFragmentVM
import kotlinx.coroutines.launch

class AuctionFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentAuctionBinding? = null

    private val viewModel: AuctionFragmentVM by viewModels()

    private val cactusRecyclerViewAdapter by lazy {
        CactusAuctionRecyclerViewAdapter { clickItem ->
            viewModel.setCactusItem(clickItem)
        }
    }

    private val cactusBasketRecyclerViewAdapter by lazy {
        CactusAuctionBasketRecyclerViewAdapter { removeItem ->
            viewModel.removeBasketItem(removeItem)
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
        binding.basketRecyclerViewAdapter = cactusBasketRecyclerViewAdapter
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

                        is AuctionFragmentUiState.AddBasketCactus -> {
                            cactusBasketRecyclerViewAdapter.add(state.data)
                        }

                        is AuctionFragmentUiState.ClearBasketList -> {
                            cactusBasketRecyclerViewAdapter.clear()
                        }

                        else -> {

                        }
                    }
                }
            }
        }

        viewModel.init()
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