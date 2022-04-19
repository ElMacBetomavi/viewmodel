package com.practica.ejemplodagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.practica.ejemplodagger.data.entities.UserEntity
import com.practica.ejemplodagger.databinding.ActivityMainBinding
import com.practica.ejemplodagger.sis.util.adapter.UserAdapter
import com.practica.ejemplodagger.sis.view.DeleteAlertDialog
import com.practica.ejemplodagger.sis.view.PurchaseConfirmationDialogFragment
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
        users= emptyList<UserEntity>().toMutableList()
        initRecyclerview()
        mainViewModel.getAllUsers()

        mainViewModel.userList.observe(this, Observer { currentUserList->
            users.clear()
            users= currentUserList as MutableList<UserEntity>
            adapter.setUserLists(users)
        })

        binding.showBtn.setOnClickListener{show()}
    }

    fun initRecyclerview(){
        adapter = UserAdapter()
        adapter.setUserLists(users)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    /**show the add new user window*/
    private fun show(){
        val alert = PurchaseConfirmationDialogFragment{  user-> mainViewModel.addUser(user) }
        alert.show(supportFragmentManager, PurchaseConfirmationDialogFragment.TAG)
    }

    /**opciones de la tarjeta del producto, ver detalles, eliminar, editar*/
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getPosition()
        val user = users[position]

        return when (item.title) {
            "Ver imagen" -> {
                true
            }
            "Eliminar" -> {
                val alert = DeleteAlertDialog(user){ user -> mainViewModel.deleteUser(user) }
                alert.show(supportFragmentManager, DeleteAlertDialog.TAG)
                true
            }
            "Editar" -> {
                true
            }
            else -> false
        }
    }

}