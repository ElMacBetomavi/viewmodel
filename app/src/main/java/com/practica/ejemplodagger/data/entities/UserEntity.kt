package com.practica.ejemplodagger.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    val name:String?,
    val lasName:String?,
    var image:String?="")