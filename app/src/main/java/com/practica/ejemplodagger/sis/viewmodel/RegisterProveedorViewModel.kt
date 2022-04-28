package com.practica.ejemplodagger.sis.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.data.entities.ProveedoresEntity
import com.practica.ejemplodagger.data.repository.ProveedoresRepository
import com.practica.ejemplodagger.sis.util.ProveedorErrorMessage
import com.practica.ejemplodagger.sis.util.SetInitProductValues
import com.practica.ejemplodagger.sis.util.SetInitProveedorValues
import com.practica.ejemplodagger.sis.util.ValidarProveedor
import com.practica.ventasmoviles.sys.viewModel.productos.ErrorMessage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class RegisterProveedorViewModel: ViewModel() {

    val validarProveedor= ValidarProveedor()
    val errorMessage = MutableLiveData<ProveedorErrorMessage>()
    val repository =  ProveedoresRepository()
    var proveedores = MutableLiveData<List<ProveedoresEntity>>()
    var message = MutableLiveData<String>()
    val changeFragment = MutableLiveData<Boolean>()
    val initProveedor = MutableLiveData<ProveedoresEntity>()

    fun validateProveedor(proveedor: ProveedoresEntity, editFlag:Boolean){
        val currentErrorMessage = validarProveedor.validateProveedor(proveedor)
        errorMessage.postValue(currentErrorMessage)
        viewModelScope.launch {
            //valida los campos del formulario
            if (currentErrorMessage.status) {
                //valida si es accion de agregar o editar
                if (!editFlag) {
                    var proveedorExist = false
                    val productos = repository.getAllProveedoress()
                    productos.map {
                        async {
                            if (it.nombre==proveedor.nombre) {
                                proveedorExist = true
                            }
                        }
                    }.awaitAll()
                    //en caso de agregar nueva categoria, valida si ya fue registrada
                    if (!proveedorExist) {
                        addNewProveedor(proveedor)
                        message.postValue("Producto agregado")
                        changeFragment.postValue(true)
                    }else{
                        message.postValue("El producto ya fue previamente registrado y no puede duplicarse")
                    }
                } else {
                    edit(proveedor)
                    message.postValue("cambios guardados")
                    changeFragment.postValue(true)
                }
            }
        }
    }

     fun addNewProveedor(proveedor: ProveedoresEntity){
        viewModelScope.launch {
            repository.add(proveedor)
            val currentProvedores = repository.getAllProveedoress()
            proveedores.postValue(currentProvedores)
        }
    }

    private fun edit(product: ProveedoresEntity){
        viewModelScope.launch {
            repository.update(product)
            val currentproduct = repository.getAllProveedoress()
            proveedores.postValue(currentproduct)
        }
    }

    fun setInitCategoryValues(id:Int){
        viewModelScope.launch {
            val currentInitProduct = SetInitProveedorValues().getInitProveedor(id)
            initProveedor.postValue(currentInitProduct)
        }
    }

}