package com.practica.ejemplodagger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practica.ejemplodagger.data.datasource.dao.CategoriaDao
import com.practica.ejemplodagger.data.datasource.dao.ProductoDao
import com.practica.ejemplodagger.data.datasource.dao.ScheduleDao
import com.practica.ejemplodagger.data.datasource.dao.UserDao
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.data.entities.ScheduleEntity
import com.practica.ejemplodagger.data.entities.UserEntity


@Database(entities = [CategoriaEntity::class, ScheduleEntity::class, ProductosEntity::class, UserEntity::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun productoDao(): ProductoDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun scheduleDao(): ScheduleDao

}