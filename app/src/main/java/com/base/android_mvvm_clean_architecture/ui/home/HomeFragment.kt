package com.base.android_mvvm_clean_architecture.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.android_mvvm_clean_architecture.R
import dagger.hilt.android.AndroidEntryPoint
import com.base.android_mvvm_clean_architecture.common.base.BaseFragment
import com.base.android_mvvm_clean_architecture.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment() : BaseFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class) {
    private val listAdapter by lazy { HomeAdapter() }

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        bind.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }
        viewModelSelf.getLocationInfo()
    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModelSelf.listLocationEvent.observe(this) {
            listAdapter.submitList(it)
        }
    }
}