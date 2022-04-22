package com.practica.ejemplodagger.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoria_entity")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    val date:String?,
    val hour:String?)