package com.practica.ejemplodagger.sis.viewmodel

import android.annotation.SuppressLint
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.repository.UserRepository
import com.practica.ejemplodagger.data.entities.UserEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() :ViewModel() {
    val repository = UserRepository()
    var userList = MutableLiveData<List<UserEntity>>()
    @SuppressLint("StaticFieldLeak")
    val context = MainApplication.appContext

    fun getAllUsers(){
        viewModelScope.launch {
            val currentUserList = repository.getAllUsers()
            userList.postValue(currentUserList)
        }
    }

    fun addUser(user:UserEntity){
        viewModelScope.launch {
            repository.add(user)
            getAllUsers()
        }
    }

    fun deleteUser(user:UserEntity){
        viewModelScope.launch {
            repository.delete(user)
            getAllUsers()
        }
    }

    fun viewMessage(){
        Toast.makeText(context,"funciona",Toast.LENGTH_LONG).show()
    }

    /**atiende la seleccion del context menu de cada item del rv*/
    fun itemSelect(item:MenuItem, user:UserEntity, fragmentManager: FragmentManager){
         when (item.title) {
            "Ver imagen" -> {
            }
            "Eliminar" -> {
                //val alert = DeleteAlertDialog(user){ deleteUser -> deleteUser(deleteUser) }
                //alert.show(fragmentManager, DeleteAlertDialog.TAG)
            }
            "Editar" -> {
                viewMessage()
            }
            else -> viewMessage()
        }
    }

}