package com.practica.ejemplodagger.sis.ui.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.practica.ejemplodagger.data.entities.UserEntity

class DeleteAlertDialog(
    private val user:UserEntity,
    private val updateList: (user:UserEntity) -> Unit
) : DialogFragment()  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Desea eliminar el usuario  ${user.name}")
            .setView(view)
            .setPositiveButton("si") { _, _ ->
                updateList(user)
                Toast.makeText(context, "usuario eliminado", Toast.LENGTH_LONG).show()
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