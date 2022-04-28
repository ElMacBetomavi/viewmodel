package com.practica.ejemplodagger.data.repository

import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.entities.ClientesEntity
import com.practica.ejemplodagger.data.entities.ProveedoresEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClienteRepository {
    private val db = MainApplication.database.clienteDao()

    suspend fun getAllClientes(): MutableList<ClientesEntity> =
        withContext(Dispatchers.IO) {
            db.getAll()
        }

    suspend fun add(cliente: ClientesEntity)=
        withContext(Dispatchers.IO) {
            db.addCliente(cliente)
        }
    suspend fun delete(cliente: ClientesEntity)=
        withContext(Dispatchers.IO) {
            db.deleteCliente(cliente)
        }

    suspend fun update(cliente: ClientesEntity)=
        withContext(Dispatchers.IO) {
            db.updateCliente(cliente)
        }
    suspend fun search(query:String): MutableList<ClientesEntity> =
        withContext(Dispatchers.IO) {
            db.search(query)!!
        }

}