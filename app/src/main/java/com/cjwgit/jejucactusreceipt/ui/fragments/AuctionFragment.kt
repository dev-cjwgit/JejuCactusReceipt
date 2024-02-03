package com.cjwgit.jejucactusreceipt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cjwgit.jejucactusreceipt.databinding.FragmentAuctionBinding
import com.cjwgit.jejucactusreceipt.ui.viewmodel.AuctionFragmentVM

class AuctionFragment : Fragment() {

    private var _binding: FragmentAuctionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val auctionFragmentVM =
            ViewModelProvider(this).get(AuctionFragmentVM::class.java)

        _binding = FragmentAuctionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        auctionFragmentVM.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}