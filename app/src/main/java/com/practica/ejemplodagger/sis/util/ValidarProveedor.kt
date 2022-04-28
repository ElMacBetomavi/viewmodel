package com.practica.ejemplodagger.sis.util

import com.practica.ejemplodagger.data.entities.ProveedoresEntity
import com.practica.ventasmoviles.sys.viewModel.productos.ErrorMessage

class ValidarProveedor {

    fun validateProveedor(proveedor: ProveedoresEntity): ProveedorErrorMessage {

        val currentErrorMessage = ProveedorErrorMessage()
        //val flats = mutableListOf(true,true,true,true,true,true,true,true,true)
        val flats = BooleanArray(11){true}

        if(!validateName(proveedor.nombre!!)){
            currentErrorMessage.nombre = "el nombre debe de tener minimo 5 caracteres maximo 70"
            flats[0]=false
        }else {
            currentErrorMessage.nombre=null
        }

        if(validateCalle(proveedor.calle!!)){
            currentErrorMessage.calle = null
        }else {
            currentErrorMessage.calle = "calle debe tener almenos 1 caracter maximo 100"
            flats[1]=false
        }

        if(!validateNumero(proveedor.numero!!)){
            currentErrorMessage.numero = "numero debe de tener al menos 1 caracter maximo 20"
            flats[2]=false
        }else {
            currentErrorMessage.numero = null
        }

        if(proveedor.codigoPostal == null) proveedor.codigoPostal = 0

        if(!validateNumero(proveedor.codigoPostal!!)){
            currentErrorMessage.codigoPostal = "codigo postal debe tener 5 numeros"
            flats[3]=false
        }else {
            currentErrorMessage.codigoPostal = null
        }

        if(!validateCharacters(proveedor.colonia!!)){
            currentErrorMessage.colonia = "colonia debe tener 5 caracteres"
            flats[4]=false
        }else {
            currentErrorMessage.colonia = null
        }

        if(!validateCharacters(proveedor.municipio!!)){
            currentErrorMessage.municipio = "municipio debe tener 5 caracteres"
            flats[5]=false
        }else {
            currentErrorMessage.municipio = null
        }

        if(!validateCharacters(proveedor.estado!!)){
            currentErrorMessage.estado = "estado debe tener 5 caracteres"
            flats[6]=false
        }else {
            currentErrorMessage.estado = null
        }

        if(!validateContacto(proveedor.contacto!!)){
            currentErrorMessage.contacto = "contacto debe tener 5 al menos caracteres"
            flats[7]=false
        }else {
            currentErrorMessage.contacto = null
        }

        if(proveedor.lada == null) proveedor.lada = 0

        if(!validateLada(proveedor.lada!!)){
            currentErrorMessage.lada = "lada debe de tener minimo 2 numeros maximo 3"
            flats[8]=false
        }else {
            currentErrorMessage.lada = null
        }

        if(proveedor.telefono == null) proveedor.telefono = 0

        if(!validatetelefono(proveedor.telefono!!)){
            currentErrorMessage.telefono = "numero debe de tener al menos 7 caracteres maximo 8"
            flats[9]=false
        }else {
            currentErrorMessage.telefono = null
        }

        if(proveedor.tipoTel==""){
            currentErrorMessage.tipoTel = "Seleccione una opción"
            flats[10]=false
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




    private fun validateSelectedOption(option:String):Boolean{
        return option.isEmpty()
    }
}