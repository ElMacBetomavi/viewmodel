package com.practica.ejemplodagger.sis.util

import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ventasmoviles.sys.viewModel.productos.ErrorMessage

class ProductRegisterValidation {

    fun validateProduct(producto: ProductosEntity): ErrorMessage {
        var currentErrorMessage = ErrorMessage()
        val flats = mutableListOf(true,true,true,true,true,true,true,true,true)
        if(!validateName(producto.nombre!!)){
            currentErrorMessage.name = "el nombre debe de tener minimo 5 caracteres maximo 70"
            flats[0]=false
        }else {
            currentErrorMessage.name=null
        }

        if(validateCosto(producto.costo)){
            currentErrorMessage.costo = null
        }else {
            currentErrorMessage.costo = "obligatorio"
            flats[1]=false
        }

        if(validateSelectedOption(producto.categoria!!)){
            currentErrorMessage.categoria = "Seleccione un opción"
            flats[2]=false
        }else {
            currentErrorMessage.categoria = null
        }

        if(!validateCosto(producto.precioMenudeo)){
            currentErrorMessage.precioMenudeo = "obligatorio"
            flats[3]=false
        }else {
            currentErrorMessage.precioMenudeo = null
        }

        if(!validateCosto(producto.precioMayoreo)){
            currentErrorMessage.precioMayoreo = "obligatorio"
            flats[4]=false
        }else {
            currentErrorMessage.precioMayoreo = null
        }

        if(!validateMarca(producto.marca!!)){
            currentErrorMessage.marca = "marca debe de tener minimo 3 caracteres maximo 40"
            flats[5]=false
        }else {
            currentErrorMessage.marca = null
        }

        if(!validateColor(producto.color!!)){
            currentErrorMessage.color = "color debe de tener minimo 5 caracteres maximo 40"
            flats[6]=false
        }else {
            currentErrorMessage.color = null
        }

        if(validateSelectedOption(producto.unidadMedida!!)){
            currentErrorMessage.unidadDeMedida = "Seleccione un opción"
            flats[7]=false
        }else {
            currentErrorMessage.unidadDeMedida = null
        }
        if(producto.cantidadMin==null){
            currentErrorMessage.cantidad = "Oblicagorio"
            flats[0]=false
        }else {
            currentErrorMessage.cantidad = null
        }
        currentErrorMessage.status = flats[0] && flats[1] && flats[2] && flats[3] &&
                                     flats[4] && flats[5] && flats[6] && flats[7] && flats[8]

        return currentErrorMessage
    }

    private fun validateName(name:String): Boolean {
        return name.length in 5..70
    }

    private fun validateCosto(costo: Float?): Boolean {
        val newcosto:Float
        newcosto = if(costo != null) costo else 0f
        return newcosto >= 0.1f
    }

    private fun validateMarca(marca:String):Boolean{
        return marca.length in 3..40
    }
    private fun validateColor(marca:String):Boolean{
        return marca.length in 5..40
    }

    private fun validateSelectedOption(option:String):Boolean{
        return option.isEmpty()
    }
}