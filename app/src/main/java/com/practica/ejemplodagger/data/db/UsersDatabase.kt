package com.practica.ejemplodagger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practica.ejemplodagger.data.datasource.dao.UserDao
import com.practica.ejemplodagger.data.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}