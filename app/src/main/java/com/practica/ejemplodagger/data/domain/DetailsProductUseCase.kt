package com.practica.ejemplodagger.data.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.sis.ui.view.RegisterCategoriyFragment
import com.practica.ejemplodagger.sis.ui.view.RegisterProveedorFragment
import com.practica.ejemplodagger.sis.ui.view.alerdialog.ImageAlertDialog
import java.io.File

class DetailsProductUseCase {

    /**ver detalles muestra un alert dialog con la imagen de la categoria
     * si esque existe, caso contrario muestra un mensaje*/
    fun detalles(cotegoryName:String, parentFragmentManager:FragmentManager){
        val bundle = Bundle()
        bundle.putString("categoryName", cotegoryName)
        val fragment = RegisterCategoriyFragment()
        fragment.arguments = bundle
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container,fragment)
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

}