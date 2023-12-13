package com.moetaz.mazaady.presentation.ui.dialogs.bottomSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moetaz.mazaady.R
import com.moetaz.mazaady.databinding.DialogBottomSearchListBinding
import com.moetaz.mazaady.presentation.ui.adapters.BottomSearchListAdapter
import com.moetaz.mazaady.presentation.utils.getParcelableArrayList
import com.moetaz.mazaady.presentation.utils.setUp
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class BottomSearchListDialog : BottomSheetDialogFragment() {

    companion object {
        private const val ARGS_TITLE = "ARGS_TITLE"
        private const val ARGS_HAS_OTHER_VALUE = "ARGS_HAS_OTHER_VALUE"
        private const val ARGS_LIST = "ARGS_LIST"

        fun newInstance(
            hasOtherValue: Boolean = false,
            list: ArrayList<SearchItem>,
            title: String? = null,
        ) =
            BottomSearchListDialog().apply {
                arguments = Bundle().apply {
                    if (title != null)
                        putString(ARGS_TITLE, title)

                    putBoolean(ARGS_HAS_OTHER_VALUE, hasOtherValue)
                    putParcelableArrayList(ARGS_LIST, list)
                }

            }
    }

    private lateinit var binding: DialogBottomSearchListBinding
    private lateinit var adapter: BottomSearchListAdapter
    var onItemSelected: (SearchItem) -> Unit = {}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = DialogBottomSearchListBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    private fun setUp() {
        adapter = BottomSearchListAdapter(arguments?.getBoolean(ARGS_HAS_OTHER_VALUE) ?: false)
        binding.apply {
            rvList.setUp(adapter, isDivider = true)
            adapter.onItemClick = ::onItemClicked
        }

        onClicks()

        binding.tvTitle.text = arguments?.getString(ARGS_TITLE, getString(R.string.select))
        adapter.submitList(getParcelableArrayList(ARGS_LIST, SearchItem::class.java))
        adapter.originalList =
            getParcelableArrayList(ARGS_LIST, SearchItem::class.java) ?: emptyList()
    }

    /** in case of the id of the SearchItem = -1 this means that user selected Other value and want to write its own value. */
    private fun onItemClicked(item: SearchItem) {
        onItemSelected(item)
        dismiss()
    }

    private fun onClicks() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.edSearch.addTextChangedListener {
            adapter.filter.filter(it.toString())
        }
    }


}