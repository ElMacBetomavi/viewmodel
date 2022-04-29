package com.practica.ejemplodagger.data.domain

import com.practica.ejemplodagger.data.repository.CategoriaRepository
import com.practica.ejemplodagger.data.repository.ProductRepository

class FilterSelectUseCase {

    val categoryRepository = CategoriaRepository()
    val productRepository = ProductRepository()


    suspend fun categoriaSelected(): MutableList<String> {
        val categorias = mutableListOf<String>()
        val allCategories = categoryRepository.getAllCategorias()

        allCategories.map { categoria ->
            categorias.add(categoria.name)
        }
        return  categorias
    }

    suspend fun unidadesSelected():MutableList<String>{
        val unidades = mutableListOf<String>()
        val allUnits = productRepository.getAllProducts()

        allUnits.map { product ->
            unidades.add(product.unidadMedida)
        }
        return  unidades
    }
}