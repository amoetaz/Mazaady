package com.moetaz.mazaady.presentation.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moetaz.mazaady.databinding.DialogSelectedValuesBinding
import com.moetaz.mazaady.domain.models.requests.MainRequest
import com.moetaz.mazaady.presentation.ui.adapters.SelectedPropertiesAdapter
import com.moetaz.mazaady.presentation.utils.getParcelable
import com.moetaz.mazaady.presentation.utils.setUp

class SelectedValuesDialog : BottomSheetDialogFragment() {

    companion object {
        private const val ARGS_REQUEST = "ARGS_REQUEST"

        fun newInstance(
            request: MainRequest,
        ) =
            SelectedValuesDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARGS_REQUEST, request)
                }
            }
    }

    private lateinit var binding: DialogSelectedValuesBinding
    private lateinit var adapter: SelectedPropertiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = DialogSelectedValuesBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    private fun setUp() {
        adapter = SelectedPropertiesAdapter()

        val request = getParcelable(ARGS_REQUEST, MainRequest::class.java)
        binding.apply {
            rv.setUp(adapter, isDivider = true)
            tvMainCat.text = request?.category?.name
            tvSubCat.text = request?.subCategory?.name
        }
        adapter.submitList(request?.properties?.values?.reversed())

        onClicks()
    }

    private fun onClicks() {
        binding.btnSubmit.setOnClickListener {
            dismiss()
        }

    }


}