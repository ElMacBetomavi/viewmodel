package com.practica.ejemplodagger.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoria_entity")
data class CategoriaEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name:String?,
    var description:String?,
    var image:String?="")
