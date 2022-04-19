package com.practica.ejemplodagger.sis.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.data.Repository.UserReposiory
import com.practica.ejemplodagger.data.entities.UserEntity
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    val repository = UserReposiory()
    var userList = MutableLiveData<List<UserEntity>>()

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


}