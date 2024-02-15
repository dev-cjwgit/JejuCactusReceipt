package com.cjwgit.jejucactusreceipt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.cjwgit.jejucactusreceipt.databinding.FragmentEditCactusBinding
import com.cjwgit.jejucactusreceipt.ui.adapter.EditCactusRecyclerViewAdapter
import com.cjwgit.jejucactusreceipt.ui.adapter.common.ItemTouchHelperCallback
import com.cjwgit.jejucactusreceipt.ui.adapter.common.ItemTouchHelperListener
import com.cjwgit.jejucactusreceipt.ui.dialog.NotificationDialog
import com.cjwgit.jejucactusreceipt.ui.viewmodel.EditCactusFragmentUiState
import com.cjwgit.jejucactusreceipt.ui.viewmodel.EditCactusFragmentVM
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class EditCactusFragment : Fragment() {
    private var _binding: FragmentEditCactusBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditCactusFragmentVM by inject()

    private val cactusRecyclerViewAdapter by lazy {
        EditCactusRecyclerViewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCactusBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.cactusRecyclerViewAdapter = cactusRecyclerViewAdapter
        binding.lifecycleOwner = viewLifecycleOwner

        init()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.uiState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is EditCactusFragmentUiState.ShowMessage -> {
                        showMessage(state.message)
                    }

                    is EditCactusFragmentUiState.SetCactusList -> {
                        cactusRecyclerViewAdapter.submitList(state.data)
                    }

                    else -> {

                    }
                }


            }
        }
    }

    private fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val cactusRecyclerView = binding.cactusRecyclerView
        cactusRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cactusRecyclerView.animation = null
        cactusRecyclerView.setHasFixedSize(true)

        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(object : ItemTouchHelperListener {
            override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
                // TODO CJW WORK Model 연결 필요
                viewModel.swipe(fromPosition, toPosition)
//                cactusRecyclerViewAdapter.notifyItemMoved(fromPosition, toPosition)
                return true
            }

            override fun onItemSwipe(position: Int) {
                // TODO CJW WORK Model 연결 필요
                println("swipe $position")
                viewModel.removeItem(position)
            }
        }))
        itemTouchHelper.attachToRecyclerView(cactusRecyclerView)
    }

    override fun onResume() {
        super.onResume()
        activity?.title = "제주농원 영수증 편집 화면"

        // ViewPager 특성 상 Resume 시 초기화
        viewModel.init()
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
        _binding = null
    }
}