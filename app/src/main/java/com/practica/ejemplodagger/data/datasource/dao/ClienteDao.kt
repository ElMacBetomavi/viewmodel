package com.practica.ejemplodagger.data.datasource.dao

import androidx.room.*
import com.practica.ejemplodagger.data.entities.ClientesEntity
import com.practica.ejemplodagger.data.entities.ProveedoresEntity

@Dao
interface ClienteDao {
    @Query("SELECT * FROM clientes_entity ORDER BY nombre COLLATE NOCASE ASC")
    fun getAll(): MutableList<ClientesEntity>

    @Query("SELECT * FROM clientes_entity WHERE id IN (:clientesIds)")
    fun loadAllByIds(clientesIds: IntArray): List<ClientesEntity>

    @Insert
    fun addCliente(cleinte: ClientesEntity):Long

    @Delete
    fun deleteCliente(cliente: ClientesEntity)

    @Update
    suspend fun updateCliente(cliente: ClientesEntity): Int

    @Query("SELECT * FROM clientes_entity WHERE colonia like :value OR nombre like :value ORDER BY nombre COLLATE NOCASE ASC")
    suspend fun search(value: String?):  MutableList<ClientesEntity>?

}