package com.practica.ejemplodagger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practica.ejemplodagger.data.datasource.dao.CategoriaDao
import com.practica.ejemplodagger.data.datasource.dao.ClienteDao
import com.practica.ejemplodagger.data.datasource.dao.ProductoDao
import com.practica.ejemplodagger.data.datasource.dao.ProveedorDao
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.data.entities.ClientesEntity
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.data.entities.ProveedoresEntity

@Database(entities = [CategoriaEntity::class, ProductosEntity::class,
    ProveedoresEntity::class, ClientesEntity::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun proveedorDao(): ProveedorDao
    abstract fun clienteDao(): ClienteDao
}