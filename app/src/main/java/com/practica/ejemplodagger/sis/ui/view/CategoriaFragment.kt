package com.practica.ejemplodagger.sis.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.data.entities.UserEntity
import com.practica.ejemplodagger.databinding.FragmentCategoriaBinding
import com.practica.ejemplodagger.sis.ui.adapter.CategoriaListAdapter
import com.practica.ejemplodagger.sis.ui.adapter.UserAdapter


class CategoriaFragment : Fragment() {

    private var _binding: FragmentCategoriaBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoriaListAdapter
    private lateinit var categoriaList:MutableList<CategoriaEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

    }

    fun initRecyclerView(){
        categoriaList= emptyList<CategoriaEntity>().toMutableList()
        adapter = CategoriaListAdapter()
        adapter.setListProducts(categoriaList)
        binding.rvCategorias.layoutManager = LinearLayoutManager(context)
        binding.rvCategorias.adapter = adapter
    }



}