package com.practica.ejemplodagger.data.repository

import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.entities.ProveedoresEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProveedoresRepository {
    private val db = MainApplication.database.proveedorDao()

    suspend fun getAllProveedoress(): MutableList<ProveedoresEntity> =
        withContext(Dispatchers.IO) {
            db.getAll()
        }

    suspend fun add(product: ProveedoresEntity)=
        withContext(Dispatchers.IO) {
            db.addProveedor(product)
        }
    suspend fun delete(product: ProveedoresEntity)=
        withContext(Dispatchers.IO) {
            db.deleteProveedor(product)
        }

    suspend fun update(product: ProveedoresEntity)=
        withContext(Dispatchers.IO) {
            db.updateProveedor(product)
        }
    suspend fun search(query:String): MutableList<ProveedoresEntity> =
        withContext(Dispatchers.IO) {
            db.search(query)!!
        }

}