package com.practica.ejemplodagger.data.repository

import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val db = MainApplication.database.userDao()

    suspend fun getAllUsers(): List<UserEntity> =
        withContext(Dispatchers.IO) {
            db.getAll()
        }

    suspend fun add(user: UserEntity)=
        withContext(Dispatchers.IO) {
            db.add(user)
        }
    suspend fun delete(user: UserEntity)=
        withContext(Dispatchers.IO) {
            db.delete(user)
        }

}

