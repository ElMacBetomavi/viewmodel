package com.practica.ejemplodagger.sis.ui.view.alerdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.practica.ejemplodagger.data.entities.ProductosEntity

class DeleteProductDialog(
    private val product: ProductosEntity,
    private val updateList: (product: ProductosEntity) -> Unit
) : DialogFragment()  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Esta seguro de eliminar el producto  ${product.nombre}?")
            .setView(view)
            .setPositiveButton("si") { _, _ ->
                updateList(product)
                Toast.makeText(context, "producto eliminado ", Toast.LENGTH_LONG).show()
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