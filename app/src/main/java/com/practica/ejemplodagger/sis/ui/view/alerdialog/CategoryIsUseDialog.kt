package com.practica.ejemplodagger.sis.ui.view.alerdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CategoryIsUseDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("la categoria no puede eliminarse ya que hay productos asociados a ella")
            .setView(view)
            .setPositiveButton("ok") { _, _ ->
                dismiss()
            }
            .create()
        return dialog
    }
}