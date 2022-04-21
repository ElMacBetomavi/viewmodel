package com.practica.ejemplodagger.sis.viewmodel

import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.domain.EditCategoryUseCase
import com.practica.ejemplodagger.data.domain.DetailsCategoryUseCase
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.data.repository.CategoriaRepository
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteAlertDialog
import kotlinx.coroutines.launch

class CategoriaViewModel:ViewModel() {

    val repository = CategoriaRepository()
    var cateogriaList = MutableLiveData<MutableList<CategoriaEntity>>()
    @SuppressLint("StaticFieldLeak")
    val context = MainApplication.appContext

    fun getAllCategorias(){
        viewModelScope.launch {
            val curentCategoriaList = repository.getAllCategorias()
            cateogriaList.postValue(curentCategoriaList)
        }
    }

    fun deleteCategory(category: CategoriaEntity){
        viewModelScope.launch {
            repository.delete(category)
            getAllCategorias()
        }
    }

    fun searchCategories(query:String){
        viewModelScope.launch {
            val curentCategoriaList = repository.search(query)
            cateogriaList.postValue(curentCategoriaList)
        }
    }

    /**atiende la seleccion del context menu de cada item del rv*/
    fun itemSelect(item: MenuItem, category:CategoriaEntity, fragmentManager: FragmentManager){
        when (item.title) {
            "Ver imagen" -> {
                val detailsCategory = DetailsCategoryUseCase()
                detailsCategory.verDetalles(category.image!!, fragmentManager, context!!)
            }
            "Eliminar" -> {
                val alert = DeleteAlertDialog(category){ deleteCategoria -> deleteCategory(deleteCategoria) }
                alert.show(fragmentManager, DeleteAlertDialog.TAG)
            }
            "Editar" -> {
                val editarCategory = EditCategoryUseCase()
                editarCategory.editar(category.id, fragmentManager)
            }
            else -> println("otro")
        }
    }


}