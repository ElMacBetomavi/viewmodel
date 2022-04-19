package com.practica.ejemplodagger.data.datasource.dao

import androidx.room.*
import com.practica.ejemplodagger.data.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_entity ORDER BY name COLLATE NOCASE ASC")
    fun getAll(): MutableList<UserEntity>

    @Query("SELECT * FROM user_entity WHERE id IN (:userIds)")
    fun getById(userIds: IntArray): MutableList<UserEntity>

    @Insert
    fun add(user: UserEntity):Long

    @Delete
    fun delete(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity): Int

    @Query("SELECT * FROM user_entity WHERE name like :value OR lasName like :value")
    suspend fun search(value: String?):  MutableList<UserEntity>?

}