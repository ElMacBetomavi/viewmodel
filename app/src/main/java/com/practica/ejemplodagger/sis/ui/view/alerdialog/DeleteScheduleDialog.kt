package com.practica.ejemplodagger.sis.ui.view.alerdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.practica.ejemplodagger.data.entities.ScheduleEntity

class DeleteScheduleDialog(
    private val schedule : ScheduleEntity,
    private val updateList: (schedule: ScheduleEntity) -> Unit
) : DialogFragment()  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Esta seguro de eliminar la cita ?")
            .setView(view)
            .setPositiveButton("si") { _, _ ->
                updateList(schedule)
                Toast.makeText(context, "cita eliminada", Toast.LENGTH_LONG).show()
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