package com.practica.ejemplodagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.practica.ejemplodagger.data.entities.UserEntity
import com.practica.ejemplodagger.databinding.ActivityMainBinding
import com.practica.ejemplodagger.sis.ui.adapter.UserAdapter
import com.practica.ejemplodagger.sis.ui.view.PurchaseConfirmationDialogFragment
import com.practica.ejemplodagger.sis.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter:UserAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var users:MutableList<UserEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerview()
        mainViewModel.getAllUsers()

        mainViewModel.userList.observe(this, Observer { currentUserList->
            users.clear()
            users= currentUserList as MutableList<UserEntity>
            adapter.setUserLists(users)
        })

        binding.addBtn.setOnClickListener{addNewUser()}
    }

    fun initRecyclerview(){
        users= emptyList<UserEntity>().toMutableList()
        adapter = UserAdapter()
        adapter.setUserLists(users)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    /**show the add new user window*/
    private fun addNewUser(){
        val alert = PurchaseConfirmationDialogFragment{  user-> mainViewModel.addUser(user) }
        alert.show(supportFragmentManager, PurchaseConfirmationDialogFragment.TAG)
    }

    /**opciones de la tarjeta del producto, ver detalles, eliminar, editar*/
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getPosition()
        val user = users[position]
        mainViewModel.itemSelect(item,user,supportFragmentManager)
        return true
    }

}