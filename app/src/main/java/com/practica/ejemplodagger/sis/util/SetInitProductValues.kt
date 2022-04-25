package com.practica.ejemplodagger.sis.util

import android.annotation.SuppressLint
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.data.repository.ProductRepository

class SetInitProductValues {

    /**si se selecciono editar,carga los valores previos a editar en el formulario*/
    @SuppressLint("SetTextI18n")
    suspend fun getInitProduct(id:Int): ProductosEntity {
        var initProduct = ProductosEntity(0)
        val repository = ProductRepository()
        val productos = repository.getAllProducts()

        productos.map { producto ->
            if(producto.id == id) initProduct = producto
        }
        return  initProduct
    }

}