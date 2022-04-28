package com.practica.ejemplodagger.data.domain

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.sis.ui.view.RegistrarProductoFragment

class EditProductUseCase {

    fun editar(id:Int,parentFragmentManager: FragmentManager){
        val bundle = Bundle()
        bundle.putInt("id", id)
        val fragment = RegistrarProductoFragment()
        fragment.arguments = bundle
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container,fragment)
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }
}