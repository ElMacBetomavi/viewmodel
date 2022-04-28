package com.practica.ejemplodagger.sis.ui.view.alerdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.practica.ejemplodagger.data.entities.ClientesEntity

class DeleteClienteDialog(
    private val cliente: ClientesEntity,
    private val updateList: (cliente: ClientesEntity) -> Unit
) : DialogFragment()  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Esta seguro de eliminar el cliente ${cliente.nombre}?")
            .setView(view)
            .setPositiveButton("si") { _, _ ->
                updateList(cliente)
                Toast.makeText(context, "cliente eliminado ", Toast.LENGTH_LONG).show()
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