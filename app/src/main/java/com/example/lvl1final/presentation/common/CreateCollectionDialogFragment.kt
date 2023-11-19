package com.example.lvl1final.presentation.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.lvl1final.R
import com.example.lvl1final.presentation.MainViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CreateCollectionDialogFragment : DialogFragment() {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_create_collection_dialog, null)

        builder.setView(view)

        val textInputLayout: TextInputLayout =
            view.findViewById(R.id.textInputLayout_enter_collection_name)
        val textInputEditTextCollectionName: TextInputEditText =
            view.findViewById(R.id.textInputEditText_enter_collection_name)
        val btnCancel: ImageView = view.findViewById(R.id.imageView_cancel)
        val btnDone: Button = view.findViewById(R.id.btn_done)

        textInputEditTextCollectionName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                textInputLayout.error = null
            }

            override fun afterTextChanged(p0: Editable?) {}
        })


        btnDone.setOnClickListener {
            val inputText = textInputEditTextCollectionName.text.toString().trim()
            val movieCollectionList = viewModel.collectionList.value
            var isMatchFound = false
            for (movieCollection in movieCollectionList) {
                if (movieCollection.collectionName.equals(
                        inputText,
                        ignoreCase = true
                    )
                ) isMatchFound = true
            }
            if (inputText.isEmpty() || inputText.isBlank()) {
                textInputLayout.error = getString(R.string.the_name_cannot_be_empty)
            } else if (isMatchFound) {
                textInputLayout.error = getString(R.string.collection_with_this_name_already_exists)
            } else {
                textInputLayout.error = null
                viewModel.createNewCollection(inputText)
                dismiss()
            }
        }

        btnCancel.setOnClickListener {
            dismiss()
        }

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.oval_dialog_background)

        return dialog
    }
}
