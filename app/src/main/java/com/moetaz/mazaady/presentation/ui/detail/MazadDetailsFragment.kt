package com.moetaz.mazaady.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.moetaz.mazaady.R
import com.moetaz.mazaady.databinding.FragmentMazadDetailsBinding
import com.moetaz.mazaady.presentation.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MazadDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentMazadDetailsBinding
    private lateinit var adapterBidder: BidderAdapter
    private lateinit var adapterProduct: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_mazad_details, container, false)

        adapterBidder = BidderAdapter()
        adapterProduct = ProductsAdapter()

        binding.apply {
            recyclerView.adapter = adapterBidder
            recyclerView2.adapter = adapterProduct
        }

        return binding.root
    }



}