package com.practica.ejemplodagger.sis.ui.view.alerdialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.UserEntity


class ImageAlertDialog(
    private val bitmap: Bitmap? = null,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        val myView = layoutInflater.inflate(R.layout.image_view_alert_dialog,null)
        val image = myView.findViewById<ImageView>(R.id.image_view)
        image.setImageBitmap(bitmap)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(myView)
            .setPositiveButton("ok") { view, _ ->
                view.dismiss()
            }
            .create()

        return dialog
    }

    companion object {
        const val TAG = "ImageAlertDialog"
    }
}