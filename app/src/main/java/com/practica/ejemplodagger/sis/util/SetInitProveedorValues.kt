package com.practica.ejemplodagger.sis.util

import android.annotation.SuppressLint
import com.practica.ejemplodagger.data.entities.ProveedoresEntity
import com.practica.ejemplodagger.data.repository.ProveedoresRepository

class SetInitProveedorValues {

    /**si se selecciono editar,carga los valores previos a editar en el formulario*/
    @SuppressLint("SetTextI18n")
    suspend fun getInitProveedor(id:Int): ProveedoresEntity {
        var initProveedor = ProveedoresEntity(0)
        val repository = ProveedoresRepository()
        val proveedores = repository.getAllProveedoress()

        proveedores.map { proveedor ->
            if(proveedor.id == id) initProveedor = proveedor
        }
        return  initProveedor
    }

}
