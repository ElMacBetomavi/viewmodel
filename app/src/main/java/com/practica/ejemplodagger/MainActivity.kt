package com.practica.ejemplodagger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
        changeFragment()
    }

    fun changeFragment(){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container,CategoriaFragment(), "categoria")
        fragmentTransition.commit()
    }
}