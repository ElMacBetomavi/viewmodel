package com.practica.ejemplodagger.data.datasource.dao

import androidx.room.*
import com.practica.ejemplodagger.data.entities.ProductosEntity

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos_entity ORDER BY nombre COLLATE NOCASE ASC")
    fun getAllProductos(): MutableList<ProductosEntity>

    @Query("SELECT * FROM productos_entity WHERE id IN (:productsIds)")
    fun loadAllByIds(productsIds: IntArray): List<ProductosEntity>

    @Insert
    fun addProduct(product: ProductosEntity):Long

    @Delete
    fun deleteProducto(product: ProductosEntity)

    @Update
    suspend fun updateCategoria(producto: ProductosEntity): Int

    @Query("SELECT * FROM productos_entity WHERE categoria like :value OR nombre like :value ORDER BY nombre COLLATE NOCASE ASC")
    suspend fun search(value: String?):  MutableList<ProductosEntity>?

}