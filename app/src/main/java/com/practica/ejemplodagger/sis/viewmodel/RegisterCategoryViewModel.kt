package com.practica.ejemplodagger.sis.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.data.repository.CategoriaRepository
import com.practica.ejemplodagger.sis.util.CategoriaErrorMessage
import com.practica.ejemplodagger.sis.util.SetInitCategoryValues
import com.practica.ejemplodagger.sis.util.ValidateCategoryRegister
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class RegisterCategoryViewModel:ViewModel() {

    var categoriasList = MutableLiveData<List<CategoriaEntity>>()
    var errorMessageCategoria = MutableLiveData<CategoriaErrorMessage>()
    val registerValidation = ValidateCategoryRegister()
    var message = MutableLiveData<String>()
    val changeFragment = MutableLiveData<Boolean>()
    val repository =  CategoriaRepository()
    val initcategoria = MutableLiveData<CategoriaEntity>()

    fun validateCategoria(categoria: CategoriaEntity, editFlag:Boolean){
        val currentErrorMessage = registerValidation.validateProduct(categoria)
        errorMessageCategoria.postValue(currentErrorMessage)
        viewModelScope.launch {
            //valida los campos del formulario
            if(currentErrorMessage.status){
                //valida si es accion de agregar o editar
                if (!editFlag){
                    var categoryExist = false
                        val categorias = repository.getAllCategorias()
                        categorias.map {
                            async {
                                if (it.name==categoria.name) {
                                    categoryExist = true
                                }
                            }
                        }.awaitAll()
                    //en caso de agregar nueva categoria, valida si ya fue registrada
                    if (!categoryExist) {
                        addNewProduct(categoria)
                        message.postValue("Categoria agregada")
                        changeFragment.postValue(true)
                    }else{
                        println("imagen ${categoria.image}")
                        message.postValue("la categoria ya fue previamente registrada y no puede duplicarse")
                    }
                }else{
                    edit(categoria)
                    message.postValue("cambios guardados")
                    changeFragment.postValue(true)
                }
            }
        }
    }

    private fun addNewProduct(categoria: CategoriaEntity){
        viewModelScope.launch {
            repository.add(categoria)
            val currentCategoria = repository.getAllCategorias()
            categoriasList.postValue(currentCategoria)
        }
    }

    private fun edit(categoria: CategoriaEntity){
        viewModelScope.launch {
            repository.update(categoria)
            val currentCategoria = repository.getAllCategorias()
            categoriasList.postValue(currentCategoria)
        }
    }

    fun setInitCategoryValues(id:Int){
        viewModelScope.launch {
            val currentInitcategoria = SetInitCategoryValues().getInitCategory(id!!)
            initcategoria.postValue(currentInitcategoria)
        }
    }

    fun setValuesWhenDetailsProductClicked(nombre:String){
        viewModelScope.launch {
            val currentInitcategoria = repository.getCategoryByName(nombre)
            initcategoria.postValue(currentInitcategoria)
        }
    }


}