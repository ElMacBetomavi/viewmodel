package com.practica.ejemplodagger.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proveedores_entity")
data class ProveedoresEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var nombre:String?="",
    var calle:String?="",
    var numero:String?="",
    var codigoPostal:Int?=0,
    var colonia:String?="",
    var municipio:String?="",
    var estado:String?="",
    var contacto:String?="",
    var lada:Int?=0,
    var telefono:Int?=0,
    var tipoTel:String?="")