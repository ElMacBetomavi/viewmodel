package com.practica.ejemplodagger.sis.viewmodel

import android.annotation.SuppressLint
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.repository.UserRepository
import com.practica.ejemplodagger.data.entities.UserEntity
import com.practica.ejemplodagger.sis.ui.view.CategoriaFragment
import com.practica.ejemplodagger.sis.ui.view.ProductFragment
import com.practica.ejemplodagger.sis.ui.view.RegisterDatePickerFragment
import com.practica.ejemplodagger.sis.ui.view.ScheduleFragment
import kotlinx.coroutines.launch
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
                R.id.compras ->
                    fragment.postValue(ScheduleFragment())
                R.id.ventas ->
                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                R.id.mis_clientes ->
                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                R.id.inventario ->
                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
            }

    }





}