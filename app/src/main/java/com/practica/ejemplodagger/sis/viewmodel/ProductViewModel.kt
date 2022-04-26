package com.practica.ejemplodagger.sis.viewmodel

import android.annotation.SuppressLint
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.domain.DetailsProductUseCase
import com.practica.ejemplodagger.data.domain.EditProductUseCase
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.data.repository.ProductRepository
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteAlertDialog
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteProductDialog
import kotlinx.coroutines.launch

class ProductViewModel:ViewModel() {

    val repository = ProductRepository()
    var productList = MutableLiveData<MutableList<ProductosEntity>>()
    @SuppressLint("StaticFieldLeak")
    val context = MainApplication.appContext

    fun getAllProducts(){
        viewModelScope.launch {
            val curentProductList = repository.getAllProducts()
            productList.postValue(curentProductList)
        }
    }

    fun deleteProduct(category: ProductosEntity){
        viewModelScope.launch {
            repository.delete(category)
            getAllProducts()
        }
    }

    fun searchCategories(query:String){
        viewModelScope.launch {
            val curentCategoriaList = repository.search(query)
            productList.postValue(curentCategoriaList)
        }
    }

    /**atiende la seleccion del context menu de cada item del rv*/
    fun itemSelect(item: MenuItem, product: ProductosEntity, fragmentManager: FragmentManager){
        when (item.title) {
            "Ver imagen" -> {

                val detailsProduct= DetailsProductUseCase()
                detailsProduct.verDetalles(product.imagen!!, fragmentManager, context!!)
            }
            "Eliminar" -> {
                val alert = DeleteProductDialog(product){ deleteCategoria -> deleteProduct(deleteCategoria) }
                alert.show(fragmentManager, DeleteAlertDialog.TAG)
            }
            "Editar" -> {
                val editProductUseCasey = EditProductUseCase()
                editProductUseCasey.editar(product.id, fragmentManager)
            }
            else -> println("otro")
        }
    }

}