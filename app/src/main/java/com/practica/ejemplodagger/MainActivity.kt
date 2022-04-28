package com.practica.ejemplodagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.practica.ejemplodagger.databinding.ActivityMainBinding
import com.practica.ejemplodagger.sis.ui.view.CategoriaFragment
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

        binding.topAppBar.setNavigationOnClickListener { setOnClickMenu() }
    }

    fun changeFragment(fragment:Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.setCustomAnimations(R.anim.enter_from_right,R.anim.out_from_right)
        fragmentTransition.replace(R.id.fragment_container,fragment)
        fragmentTransition.commit()
    }

    private fun setOnClickMenu(){
        val popupMenu = PopupMenu(this,binding.topAppBar)
        popupMenu.menuInflater.inflate(R.menu.main_menu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            mainViewModel.setOnClickMenu(item)
            true
        }
        popupMenu.show()
    }




}