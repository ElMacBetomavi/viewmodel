package com.practica.ejemplodagger.data.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.sis.ui.view.alerdialog.ImageAlertDialog
import java.io.File

class DetailsCategoryUseCase {

    val context = MainApplication.appContext
    /**ver detalles muestra un alert dialog con la imagen de la categoria
     * si esque existe, caso contrario muestra un mensaje*/
    fun verDetalles(image: String, fragmentManager: FragmentManager, context:Context){
        val file = File(image)
        var bitmap: Bitmap? = null

        if (file.exists()){
            bitmap  = BitmapFactory.decodeFile(image)
                val alert = ImageAlertDialog(bitmap)
                alert.show(fragmentManager, ImageAlertDialog.TAG)
        }else{
            val uri = image.toUri()
            val uriFile = File(uri.path!!)
            if (uriFile.exists()) {
                val alert = ImageAlertDialog(bitmap)
                alert.show(fragmentManager, ImageAlertDialog.TAG)
            }else{
                Toast.makeText(context,"no existe imagen asignada", Toast.LENGTH_LONG).show()
            }
        }
    }


}