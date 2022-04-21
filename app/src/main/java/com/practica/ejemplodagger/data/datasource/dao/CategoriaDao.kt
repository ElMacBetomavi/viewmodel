package com.practica.ejemplodagger.data.datasource.dao

import androidx.room.*
import com.practica.ejemplodagger.data.entities.CategoriaEntity

@Dao
interface CategoriaDao {
    @Query("SELECT * FROM categoria_entity ORDER BY name COLLATE NOCASE ASC")
    fun getAllCategoria(): MutableList<CategoriaEntity>

    @Query("SELECT * FROM categoria_entity WHERE id IN (:categoriaIds)")
    fun loadAllByIds(categoriaIds: IntArray): MutableList<CategoriaEntity>

    @Insert
    fun addCategoria(categoria: CategoriaEntity):Long

    @Delete
    fun deleteCategoria(categoria: CategoriaEntity)

    @Update
    suspend fun updateCategoria(categoria: CategoriaEntity): Int

    @Query("SELECT * FROM categoria_entity WHERE name like :value OR description like :value")
    suspend fun searchCategoria(value: String?):  MutableList<CategoriaEntity>?

    @Query("SELECT * FROM categoria_entity WHERE name = :value")
    suspend fun getByName(value: String?):  CategoriaEntity?

}