package com.practica.ejemplodagger.sis.util

import android.annotation.SuppressLint
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.data.repository.CategoriaRepository
import com.practica.ejemplodagger.databinding.FragmentCategoriaBinding

class SetInitCategoryValues {

    /**si se selecciono editar,carga los valores previos a editar en el formulario*/
    @SuppressLint("SetTextI18n")
    suspend fun getInitCategory(id:Int):CategoriaEntity{
        var initcategoria = CategoriaEntity(0,"","")
        val repository = CategoriaRepository()
        val categorias = repository.getAllCategorias()

        categorias.map { category ->
            if(category.id == id) initcategoria = category
        }
        println("categoria metodo "+ initcategoria)
        return  initcategoria
    }


}