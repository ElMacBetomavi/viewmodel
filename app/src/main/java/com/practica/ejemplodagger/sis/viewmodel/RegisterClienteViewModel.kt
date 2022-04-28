package com.practica.ejemplodagger.sis.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.data.entities.ClientesEntity
import com.practica.ejemplodagger.data.repository.ClienteRepository
import com.practica.ejemplodagger.sis.util.ClientErrorMessage
import com.practica.ejemplodagger.sis.util.SetInitClientValues
import com.practica.ejemplodagger.sis.util.ValidarCliente
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.awaitAll

class RegisterClienteViewModel: ViewModel() {

    val validarCliente= ValidarCliente()
    val errorMessage = MutableLiveData<ClientErrorMessage>()
    val repository =  ClienteRepository()
    var clientes = MutableLiveData<List<ClientesEntity>>()
    var message = MutableLiveData<String>()
    val changeFragment = MutableLiveData<Boolean>()
    val initCliente = MutableLiveData<ClientesEntity>()


    fun validateCliente(cliente: ClientesEntity, editFlag:Boolean){
        val currentErrorMessage = validarCliente.validateCliente(cliente)
        errorMessage.postValue(currentErrorMessage)
        viewModelScope.launch {
            //valida los campos del formulario

            if (currentErrorMessage.status) {
                //valida si es accion de agregar o editar
                if (!editFlag) {
                    var isClientExist = false
                    val productos = repository.getAllClientes()
                    productos.map {
                        async {
                            if (it.nombre==cliente.nombre) {
                                isClientExist = true
                            }
                        }
                    }.awaitAll()
                    //en caso de agregar nueva categoria, valida si ya fue registrada
                    if (!isClientExist) {
                        addNewCliente(cliente)
                        message.postValue("Cliente agregado")
                        changeFragment.postValue(true)
                    }else{
                        message.postValue("El Cliente ya fue previamente registrado y no puede duplicarse")
                    }
                } else {
                    edit(cliente)
                    message.postValue("cambios guardados")
                    changeFragment.postValue(true)
                }
            }
        }
    }


    fun addNewCliente(cliente: ClientesEntity){
        viewModelScope.launch {
            repository.add(cliente)
            val currentProvedores = repository.getAllClientes()
            clientes.postValue(currentProvedores)
        }
    }

    private fun edit(cliente: ClientesEntity){
        viewModelScope.launch {
            repository.update(cliente)
            val currentproduct = repository.getAllClientes()
            clientes.postValue(currentproduct)
        }
    }

    fun setInitCategoryValues(id:Int){
        viewModelScope.launch {
            val currentInitProduct = SetInitClientValues().getInitCliente(id)
            initCliente.postValue(currentInitProduct)
        }
    }

}