package com.practica.ejemplodagger.sis.ui.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.UserEntity


class PurchaseConfirmationDialogFragment(
    private val updateList: (user:UserEntity) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        val view = layoutInflater.inflate(R.layout.cutom_alert_dialog,null)
        val textName = view.findViewById<TextInputEditText>(R.id.name_field)
        val textLastname = view.findViewById<TextInputEditText>(R.id.lastname_field)

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("agregar usuario")
            .setView(view)
            .setPositiveButton("si") { _, _ ->
                val newUser = UserEntity(0,textName.text.toString(), textLastname.text.toString())
                //db.add(newUser)
                updateList(newUser)
                Toast.makeText(context, "agrego: ${textName.text}",Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("cancelar"){ _, _ ->
                Toast.makeText(context, "selecciono no",Toast.LENGTH_LONG).show()
            }
            .create()
        return dialog
    }

    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }
}