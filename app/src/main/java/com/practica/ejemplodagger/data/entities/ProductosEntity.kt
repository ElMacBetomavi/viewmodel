package com.practica.ejemplodagger.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos_entity")
data class ProductosEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var nombre:String?="",
    var costo:Float?=0f,
    var precioMenudeo:Float?=0f,
    var precioMayoreo:Float?=0f,
    var categoria:String?="",
    var marca:String?="",
    var color:String?="",
    var unidadMedida:String?="",
    var cantidadMin:Int?=0,
    var imagen:String?="")