package com.practica.ejemplodagger.data.repository

import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriaRepository {

    private val db = MainApplication.database.categoriaDao()

    suspend fun getAllCategorias(): MutableList<CategoriaEntity> =
        withContext(Dispatchers.IO) {
            db.getAllCategoria()
        }

    suspend fun add(categoria: CategoriaEntity)=
        withContext(Dispatchers.IO) {
            db.addCategoria(categoria)
        }
    suspend fun delete(categoria: CategoriaEntity)=
        withContext(Dispatchers.IO) {
            db.deleteCategoria(categoria)
        }

    suspend fun update(categoria: CategoriaEntity)=
        withContext(Dispatchers.IO) {
            db.updateCategoria(categoria)
        }
    suspend fun search(query:String): MutableList<CategoriaEntity> =
        withContext(Dispatchers.IO) {
            db.searchCategoria(query)!!
        }
    suspend fun getCategoryByName(query:String): CategoriaEntity =
        withContext(Dispatchers.IO) {
            db.getByName(query)!!
        }


}