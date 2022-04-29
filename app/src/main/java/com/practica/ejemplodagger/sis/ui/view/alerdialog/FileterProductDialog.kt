package com.practica.ejemplodagger.sis.ui.view.alerdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ClientesEntity

class FileterProductDialog(
    private val filtrar: (filtro:String) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val myView = layoutInflater.inflate(R.layout.filter_options_dialog, null)
        val categoriaRv = myView.findViewById<RadioButton>(R.id.categoria_filter)
        val unidadesRv = myView.findViewById<RadioButton>(R.id.unidades_filter)

        return AlertDialog.Builder(requireContext())
            .setView(myView)
            .setTitle("Seleccione un filtro")
            .setPositiveButton("Aceptar"){ view, _ ->
                if (categoriaRv.isChecked) {
                    filtrar("categoria")
                } else{
                    if (unidadesRv.isChecked) {
                        filtrar("unidades")
                    }else{
                        filtrar("productos")
                    }
                }

                view.dismiss()
            }
            .create()
    }

    companion object {
        const val TAG = "FilterProductDialog"
    }
}