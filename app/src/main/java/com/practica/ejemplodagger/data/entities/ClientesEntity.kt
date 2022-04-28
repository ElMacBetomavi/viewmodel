package com.practica.ejemplodagger.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes_entity")
data class ClientesEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var nombre:String?="",
    var apellidoM:String?="",
    var apellidoP:String?="",
    var tipoCompra:String?="",
    var calle:String?="",
    var numero:String?="",
    var interior:String?="",
    var codigoPostal:Int?=0,
    var colonia:String?="",
    var municipio:String?="",
    var estado:String?="",
    var lada:Int?=0,
    var telefono:Int?=0,
    var tipoTel:String?="",
    var alias:String?="")