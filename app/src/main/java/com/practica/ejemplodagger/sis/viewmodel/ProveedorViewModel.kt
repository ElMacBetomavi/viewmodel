package com.practica.ejemplodagger.sis.viewmodel

import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.domain.DetailsProveedorUseCase
import com.practica.ejemplodagger.data.domain.EditProveedorUseCase
import com.practica.ejemplodagger.data.entities.ProveedoresEntity
import com.practica.ejemplodagger.data.repository.ProveedoresRepository
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteAlertDialog
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteProveedorDialog
import kotlinx.coroutines.launch

class ProveedorViewModel:ViewModel() {

    val repository = ProveedoresRepository()
    var proveedoresList = MutableLiveData<MutableList<ProveedoresEntity>>()
    @SuppressLint("StaticFieldLeak")
    val context = MainApplication.appContext

    fun getAllProveedores(){
        viewModelScope.launch {
            val curentProveedoresList = repository.getAllProveedoress()
            proveedoresList.postValue(curentProveedoresList)
        }
    }

    fun deleteProveedor(proveedor: ProveedoresEntity){
        viewModelScope.launch {
            repository.delete(proveedor)
            getAllProveedores()
        }
    }

    /**atiende la seleccion del context menu de cada item del rv*/
    fun itemSelect(item: MenuItem, proveedor: ProveedoresEntity, fragmentManager: FragmentManager){
        when (item.title) {
            "Ver detalles" -> {
                val detailProveedorUseCase = DetailsProveedorUseCase()
                detailProveedorUseCase.detail(proveedor.id,fragmentManager)
            }
            "Eliminar" -> {
                val alert = DeleteProveedorDialog(proveedor){ deleteproveedor -> deleteProveedor(deleteproveedor) }
                alert.show(fragmentManager, DeleteAlertDialog.TAG)
            }
            "Editar" -> {
                val editProveedorUseCase = EditProveedorUseCase()
                editProveedorUseCase.editar(proveedor.id, fragmentManager)
            }
            else -> println("otro")
        }
    }

}