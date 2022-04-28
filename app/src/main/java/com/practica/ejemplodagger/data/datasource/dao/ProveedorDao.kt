package com.practica.ejemplodagger.data.datasource.dao

import androidx.room.*
import com.practica.ejemplodagger.data.entities.ProveedoresEntity

@Dao
interface ProveedorDao {
    @Query("SELECT * FROM proveedores_entity ORDER BY nombre COLLATE NOCASE ASC")
    fun getAll(): MutableList<ProveedoresEntity>

    @Query("SELECT * FROM proveedores_entity WHERE id IN (:proveedortsIds)")
    fun loadAllByIds(proveedortsIds: IntArray): List<ProveedoresEntity>

    @Insert
    fun addProveedor(proveedor: ProveedoresEntity):Long

    @Delete
    fun deleteProveedor(proveedor: ProveedoresEntity)

    @Update
    suspend fun updateProveedor(proveedor: ProveedoresEntity): Int

    @Query("SELECT * FROM proveedores_entity WHERE estado like :value OR nombre like :value ORDER BY nombre COLLATE NOCASE ASC")
    suspend fun search(value: String?):  MutableList<ProveedoresEntity>?

}