package com.practica.ejemplodagger.sis.viewmodel

import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.domain.DetailsClientUseCase
import com.practica.ejemplodagger.data.domain.DetailsProveedorUseCase
import com.practica.ejemplodagger.data.domain.EditClientUseCase
import com.practica.ejemplodagger.data.domain.EditProveedorUseCase
import com.practica.ejemplodagger.data.entities.ClientesEntity
import com.practica.ejemplodagger.data.repository.ClienteRepository
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteAlertDialog
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteClienteDialog
import kotlinx.coroutines.launch

class ClientesViewModel: ViewModel() {

    val repository = ClienteRepository()
    var clientesList = MutableLiveData<MutableList<ClientesEntity>>()
    @SuppressLint("StaticFieldLeak")
    val context = MainApplication.appContext

    fun getAllClientes(){
        viewModelScope.launch {
            val curentProveedoresList = repository.getAllClientes()
            clientesList.postValue(curentProveedoresList)
        }
    }

    fun deleteCliente(cliente: ClientesEntity){
        viewModelScope.launch {
            repository.delete(cliente)
            getAllClientes()
        }
    }

    fun searchCategories(query:String){
        viewModelScope.launch {
            val curentClientList = repository.search(query)
            clientesList.postValue(curentClientList)
        }
    }

    /**atiende la seleccion del context menu de cada item del rv*/
    fun itemSelect(item: MenuItem, cliente: ClientesEntity, fragmentManager: FragmentManager){
        when (item.title) {
            "Ver detalles" -> {
                val detailClienteUseCase = DetailsClientUseCase()
                detailClienteUseCase.detail(cliente.id,fragmentManager)
            }
            "Eliminar" -> {
                val alert = DeleteClienteDialog(cliente){ deletecliente -> deleteCliente(deletecliente) }
                alert.show(fragmentManager, DeleteAlertDialog.TAG)
            }
            "Editar" -> {
                val editProveedorUseCase = EditClientUseCase()
                editProveedorUseCase.editar(cliente.id, fragmentManager)
            }
            "Llamar" -> {

            }
            else -> println("otro")
        }
    }

}