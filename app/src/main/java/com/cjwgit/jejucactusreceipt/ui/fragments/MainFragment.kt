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
import com.cjwgit.jejucactusreceipt.databinding.FragmentMainBinding
import com.cjwgit.jejucactusreceipt.ui.adapter.CactusRecyclerViewAdapter
import com.cjwgit.jejucactusreceipt.ui.dialog.NotificationDialog
import com.cjwgit.jejucactusreceipt.ui.viewmodel.MainFragmentUiState
import com.cjwgit.jejucactusreceipt.ui.viewmodel.MainFragmentVM
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentMainBinding? = null

    private val viewModel: MainFragmentVM by viewModels()


    private val cactusRecyclerViewAdapter = CactusRecyclerViewAdapter { clickItem ->
        viewModel.setCactusItem(clickItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.cactusRecyclerViewAdapter = cactusRecyclerViewAdapter
        binding.lifecycleOwner = viewLifecycleOwner
        init()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is MainFragmentUiState.ShowMessage -> {
                            showMessage(state.message)
                        }

                        is MainFragmentUiState.SetCactusList -> {
                            cactusRecyclerViewAdapter.initData(state.data.toMutableList())
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
        val recyclerView = binding.cactusRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
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