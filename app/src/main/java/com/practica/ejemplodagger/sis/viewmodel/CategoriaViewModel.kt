package com.practica.ejemplodagger.sis.viewmodel

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.domain.EditCategoryUseCase
import com.practica.ejemplodagger.data.domain.DetailsCategoryUseCase
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.data.repository.CategoriaRepository
import com.practica.ejemplodagger.data.repository.ProductRepository
import com.practica.ejemplodagger.sis.ui.view.alerdialog.CategoryIsUseDialog
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteAlertDialog
import kotlinx.coroutines.launch

class CategoriaViewModel:ViewModel() {

    val repository = CategoriaRepository()
    val productRepository = ProductRepository()
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
            if (!isUseCategoria(category.name!!)){
                repository.delete(category)
                Toast.makeText(context, "categoria eliminada", Toast.LENGTH_LONG).show()
                getAllCategorias()
            }else{

            }
        }
    }

    fun searchCategories(query:String){
        viewModelScope.launch {
            val curentCategoriaList = repository.search(query)
            cateogriaList.postValue(curentCategoriaList)
        }
    }

    suspend fun isUseCategoria(categoria:String): Boolean {
        val productos = productRepository.getAllProducts()
        var isUseFlat= false
        productos.map { producto->
            if(producto.categoria == categoria) isUseFlat = true
        }
        return isUseFlat
    }

    /**atiende la seleccion del context menu de cada item del rv*/
    fun itemSelect(item: MenuItem, category:CategoriaEntity, fragmentManager: FragmentManager){
        when (item.title) {
            "Ver imagen" -> {
                val detailsCategory = DetailsCategoryUseCase()
                detailsCategory.verDetalles(category.image!!, fragmentManager, context!!)
            }
            "Eliminar" -> {
                deletecomplete(category,fragmentManager)
            }
            "Editar" -> {
                val editarCategory = EditCategoryUseCase()
                editarCategory.editar(category.id, fragmentManager)
            }
            else -> println("otro")
        }
    }


    fun deletecomplete(category:CategoriaEntity, fragmentManager: FragmentManager){
        viewModelScope.launch {
            if(!isUseCategoria(category.name!!)){
                val alert = DeleteAlertDialog(category){ deleteCategoria -> deleteCategory(deleteCategoria) }
                alert.show(fragmentManager, DeleteAlertDialog.TAG)
            }else{
                val alert = CategoryIsUseDialog()
                alert.show(fragmentManager, DeleteAlertDialog.TAG)
            }
        }

    }
}