package com.practica.ejemplodagger.data.repository

import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.entities.ProductosEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {

    private val db = MainApplication.database.productoDao()

    suspend fun getAllProducts(): MutableList<ProductosEntity> =
        withContext(Dispatchers.IO) {
            db.getAllProductos()
        }

    suspend fun add(product: ProductosEntity)=
        withContext(Dispatchers.IO) {
            db.addProduct(product)
        }
    suspend fun delete(product: ProductosEntity)=
        withContext(Dispatchers.IO) {
            db.deleteProducto(product)
        }

    suspend fun update(product: ProductosEntity)=
        withContext(Dispatchers.IO) {
            db.updateCategoria(product)
        }
    suspend fun search(query:String): MutableList<ProductosEntity> =
        withContext(Dispatchers.IO) {
            db.search(query)!!
        }

}