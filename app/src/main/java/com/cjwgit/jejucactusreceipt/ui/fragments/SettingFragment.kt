package com.cjwgit.jejucactusreceipt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cjwgit.jejucactusreceipt.databinding.FragmentSettingBinding
import com.cjwgit.jejucactusreceipt.ui.adapter.ViewPagerAdapter
import com.cjwgit.jejucactusreceipt.ui.viewmodel.SettingFragmentVM
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingFragmentVM by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        init()

//        activity?.title = "제주농원 설정 화면"
        return binding.root
    }

    private fun init() {
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        adapter.addFragment(EditCactusFragment())
        adapter.addFragment(EditAuctionFragment())

        binding.viewPager.adapter = adapter
        val titles = listOf("영수증 항목 편집", "경매장 항목 편집")
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}