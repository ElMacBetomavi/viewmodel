package com.practica.ejemplodagger.sis.util

import android.annotation.SuppressLint
import com.practica.ejemplodagger.data.entities.ClientesEntity
import com.practica.ejemplodagger.data.repository.ClienteRepository

class SetInitClientValues {

    /**si se selecciono editar,carga los valores previos a editar en el formulario*/
    @SuppressLint("SetTextI18n")
    suspend fun getInitCliente(id:Int): ClientesEntity {
        var initCliente = ClientesEntity(0)
        val repository = ClienteRepository()
        val clientes = repository.getAllClientes()

        clientes.map { cliente ->
            if(cliente.id == id) initCliente = cliente
        }
        return  initCliente
    }
}