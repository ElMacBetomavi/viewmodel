package com.practica.ejemplodagger.sis.util

import com.practica.ejemplodagger.data.entities.ClientesEntity

class ValidarCliente {

    fun validateCliente(cliente: ClientesEntity): ClientErrorMessage {

        val currentErrorMessage = ClientErrorMessage()
        val flats = BooleanArray(15){true}

        if(!validateName(cliente.nombre!!)){
            currentErrorMessage.nombre = "el nombre debe de tener minimo 5 caracteres maximo 70"
            flats[0]=false
        }else {
            currentErrorMessage.nombre=null
        }

        if(validateCalle(cliente.calle!!)){
            currentErrorMessage.calle = null
        }else {
            currentErrorMessage.calle = "calle debe tener almenos 1 caracter maximo 100"
            flats[1]=false
        }

        if(!validateNumero(cliente.numero!!)){
            currentErrorMessage.numero = "numero debe de tener al menos 1 caracter maximo 20"
            flats[2]=false
        }else {
            currentErrorMessage.numero = null
        }

        if(cliente.codigoPostal == null) cliente.codigoPostal = 0

        if(!validateNumero(cliente.codigoPostal!!)){
            currentErrorMessage.codigoPostal = "codigo postal debe tener 5 numeros"
            flats[3]=false
        }else {
            currentErrorMessage.codigoPostal = null
        }

        if(!validateCharacters(cliente.colonia!!)){
            currentErrorMessage.colonia = "colonia debe tener 5 caracteres"
            flats[4]=false
        }else {
            currentErrorMessage.colonia = null
        }

        if(!validateCharacters(cliente.municipio!!)){
            currentErrorMessage.municipio = "municipio debe tener 5 caracteres"
            flats[5]=false
        }else {
            currentErrorMessage.municipio = null
        }

        if(!validateCharacters(cliente.estado!!)){
            currentErrorMessage.estado = "estado debe tener 5 caracteres"
            flats[6]=false
        }else {
            currentErrorMessage.estado = null
        }

        if(cliente.lada == null) cliente.lada = 0

        if(!validateLada(cliente.lada!!)){
            currentErrorMessage.lada = "lada debe de tener minimo 2 numeros maximo 3"
            flats[7]=false
        }else {
            currentErrorMessage.lada = null
        }

        if(cliente.telefono == null) cliente.telefono = 0

        if(!validatetelefono(cliente.telefono!!)){
            currentErrorMessage.telefono = "numero debe de tener al menos 7 caracteres maximo 8"
            flats[8]=false
        }else {
            currentErrorMessage.telefono = null
        }

        if(cliente.tipoTel==""){
            currentErrorMessage.tipoTel = "Seleccione una opción"
            flats[9]=false
        }else {
            currentErrorMessage.tipoTel = null
        }

        currentErrorMessage.status = flats[0] && flats[1] && flats[2] && flats[3] &&
                flats[4] && flats[5] && flats[6] && flats[7] && flats[8]&& flats[9]&& flats[10]

        return currentErrorMessage
    }

    private fun validateName(name:String): Boolean {
        return name.length in 5..70
    }

    private fun validateCalle(name:String): Boolean {
        return name.length in 1..100
    }

    private fun validateNumero(name:String): Boolean {
        return name.length in 1..20
    }

    private fun validateNumero(number:Int): Boolean {
        val stringNumber = number.toString()
        return stringNumber.length == 5
    }

    private fun validateCharacters(name:String): Boolean {
        return name.length == 5
    }

    private fun validateContacto(contacto:String): Boolean {
        return contacto.length >= 5
    }

    private fun validateLada(lada:Int): Boolean {
        val stringNumber = lada.toString()
        return stringNumber.length in 2..3
    }

    private fun validatetelefono(telefono:Int): Boolean {
        val strinTelefonomberN = telefono.toString()
        return strinTelefonomberN.length in 7..8
    }
}