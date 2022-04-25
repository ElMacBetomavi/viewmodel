package com.practica.ejemplodagger.sis.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.data.repository.CategoriaRepository
import com.practica.ejemplodagger.data.repository.ProductRepository
import com.practica.ejemplodagger.sis.util.ProductRegisterValidation
import com.practica.ejemplodagger.sis.util.SetInitProductValues
import com.practica.ventasmoviles.sys.viewModel.productos.ErrorMessage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class RegisterProductViewModel:ViewModel() {

    val repository =  ProductRepository()
    val initProduct = MutableLiveData<ProductosEntity>()
    val categoryRepository = CategoriaRepository()
    var products = MutableLiveData<List<ProductosEntity>>()
    var errorMessage = MutableLiveData<ErrorMessage>()
    val registerValidation = ProductRegisterValidation()
    val changeFragment = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    var categories = MutableLiveData<MutableList<String>>()

    fun validateCategoria(product: ProductosEntity, editFlag:Boolean){
        val currentErrorMessage = registerValidation.validateProduct(product)
        errorMessage.postValue(currentErrorMessage)
        viewModelScope.launch {
            //valida los campos del formulario
            if(currentErrorMessage.status){
                //valida si es accion de agregar o editar
                if (!editFlag){
                    var productExist = false
                    val productos = repository.getAllProducts()
                    productos.map {
                        async {
                            if (it.nombre==product.nombre) {
                                productExist = true
                            }
                        }
                    }.awaitAll()
                    //en caso de agregar nueva categoria, valida si ya fue registrada
                    if (!productExist) {
                        addNewProduct(product)
                        message.postValue("Categoria agregada")
                        changeFragment.postValue(true)
                    }else{
                        message.postValue("la categoria ya fue previamente registrada y no puede duplicarse")
                    }
                }else{
                    edit(product)
                    message.postValue("cambios guardados")
                    changeFragment.postValue(true)
                }
            }
        }
    }

    private fun addNewProduct(categoria: ProductosEntity){
        viewModelScope.launch {
            repository.add(categoria)
            val currentProduct = repository.getAllProducts()
            products.postValue(currentProduct)
        }
    }

    private fun edit(product: ProductosEntity){
        viewModelScope.launch {
            repository.update(product)
            val currentproduct = repository.getAllProducts()
            products.postValue(currentproduct)
        }
    }

    fun setMultiopcionsField(){
        viewModelScope.launch {
            val listCategorias = mutableListOf<String>()
            val categorias = categoryRepository.getAllCategorias()
            categorias.map {
                listCategorias.add(it.name!!)
            }
            categories.postValue(listCategorias)
        }
    }

    fun setInitCategoryValues(id:Int){
        viewModelScope.launch {
            val currentInitProduct = SetInitProductValues().getInitProduct(id)
            initProduct.postValue(currentInitProduct)
        }
    }

}