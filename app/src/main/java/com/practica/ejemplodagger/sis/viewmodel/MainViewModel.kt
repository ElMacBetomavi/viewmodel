package com.practica.ejemplodagger.sis.viewmodel

import android.annotation.SuppressLint
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.sis.ui.view.*
import javax.inject.Inject

class MainViewModel @Inject constructor() :ViewModel() {

    @SuppressLint("StaticFieldLeak")
    val context = MainApplication.appContext

    val fragment = MutableLiveData<Fragment>()

    fun setOnClickMenu(item: MenuItem){
            when (item.itemId) {
                R.id.categoria ->
                    fragment.postValue(CategoriaFragment())
                R.id.productos ->
                    fragment.postValue(ProductFragment())
                R.id.proveedores ->
                    fragment.postValue(ProveedorFragment())
                R.id.compras ->
                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                R.id.ventas ->
                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                R.id.mis_clientes ->
                    fragment.postValue(ClientesFragment())
                R.id.inventario ->
                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
            }

    }





}