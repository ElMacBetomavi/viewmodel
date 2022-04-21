package com.practica.ejemplodagger.sis.util

import com.practica.ejemplodagger.data.entities.CategoriaEntity

class ValidateCategoryRegister {

    fun validateProduct(categoria: CategoriaEntity): CategoriaErrorMessage {
        val currentErrorMessage = CategoriaErrorMessage()
        val flats = mutableListOf(true,true)
        if(!validateName(categoria.name!!)){
            currentErrorMessage.name = "el nombre debe de tener minimo 5 caracteres maximo 70"
            flats[0]=false
        }else {
            currentErrorMessage.name=null
        }
        if(!validateDescription(categoria.description!!)){
            currentErrorMessage.description = "Descripcion debe de tener minimo 10 caracteres maximo 150"
            flats[1]=false
        }else {
            currentErrorMessage.description=null
        }

        currentErrorMessage.status = flats[0] && flats[1]

        println( flats[1])
        println( flats[0])
        return currentErrorMessage
    }

    private fun validateName(name:String): Boolean {
        return name.length in 5..70
    }

    private fun validateDescription(description:String): Boolean {
        return description.length in 10..150
    }
}