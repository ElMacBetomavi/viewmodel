package com.practica.ejemplodagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.practica.ejemplodagger.databinding.ActivityMainBinding
import com.practica.ejemplodagger.sis.ui.view.CategoriaFragment
import com.practica.ejemplodagger.sis.util.ChangeFragment
import com.practica.ejemplodagger.sis.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment(CategoriaFragment())

        mainViewModel.fragment.observe(this, Observer { currentFragment->
            changeFragment(currentFragment)
        })

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            itemSelected(menuItem)
        }

    }

    fun changeFragment(fragment:Fragment){
        val changeFragment = ChangeFragment()
        changeFragment.change(0,"", fragment ,supportFragmentManager)
    }

    fun itemSelected(menuItem:MenuItem):Boolean{
        menuItem.isChecked = true
        binding.drawerLayout.close()
        mainViewModel.setOnClickMenu(menuItem)
        return true
    }

}