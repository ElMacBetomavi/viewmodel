package com.practica.ejemplodagger.sis.ui.view.alerdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.practica.ejemplodagger.data.entities.CategoriaEntity


class DeleteAlertDialog(
    private val category:CategoriaEntity,
    private val updateList: (category: CategoriaEntity) -> Unit
) : DialogFragment()  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Esta seguro de eliminar la categoria  ${category.name}?")
            .setView(view)
            .setPositiveButton("si") { _, _ ->
                updateList(category)
            }
            .setNegativeButton("no"){ view, _ ->
                view.dismiss()
            }
            .create()
        return dialog
    }
    companion object {
        const val TAG = "DeleteDialog"
    }
}