package com.mobiquity.mobsterapp.dev

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class GeneralDialog : DialogFragment() {

    private var dialogListener: DialogListener? = null

    interface DialogListener {
        fun onPositiveButtonClicked()
        fun onNegativeButtonClicked()
    }

    companion object {
        const val DIALOG_TITLE = "DIALOG_TITLE"
        const val DIALOG_MESSAGE = "DIALOG_MESSAGE"
        const val POSITIVE_BUTTON_TITLE = "POSITIVE_BUTTON_TITLE"
        const val NEGATIVE_BUTTON_TITLE = "NEGATIVE_BUTTON_TITLE"

        fun newInstance(title: String, message: String?, positiveButtonTitle:String, negativeButtonTitle: String): GeneralDialog {
            val args = Bundle()
            args.putString(DIALOG_TITLE, title)
            args.putString(DIALOG_MESSAGE, message)
            args.putString(POSITIVE_BUTTON_TITLE, positiveButtonTitle)
            args.putString(NEGATIVE_BUTTON_TITLE, negativeButtonTitle)
            val dialog = GeneralDialog()
            dialog.setArguments(args)
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        val  title = bundle?.getString(DIALOG_TITLE)
        val message = bundle?.getString(DIALOG_MESSAGE)
        val positiveButtonTitle = bundle?.getString(POSITIVE_BUTTON_TITLE)
        val negativeButtonTitle = bundle?.getString(NEGATIVE_BUTTON_TITLE)
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButtonTitle, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialogListener?.onPositiveButtonClicked()
                dialog.dismiss()
            }
        })
        builder.setNegativeButton(negativeButtonTitle, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialogListener?.onNegativeButtonClicked()
                dialog.dismiss()
            }
        })
        return builder.create()
    }

    fun setDialogListener(listener: DialogListener) {
        dialogListener = listener
    }
}