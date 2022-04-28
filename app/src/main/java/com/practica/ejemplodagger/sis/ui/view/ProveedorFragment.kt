package com.practica.ejemplodagger.sis.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.data.entities.ProveedoresEntity
import com.practica.ejemplodagger.databinding.FragmentProveedorBinding
import com.practica.ejemplodagger.sis.ui.adapter.ProveedorAdapter
import com.practica.ejemplodagger.sis.viewmodel.ProveedorViewModel


class ProveedorFragment : Fragment() {

    private var _binding: FragmentProveedorBinding? = null
    private val binding get() = _binding!!
    private val proveedorViewModel:ProveedorViewModel by viewModels()
    private lateinit var addBtn: FloatingActionButton
    private lateinit var search: SearchView
    private var proveedorList = mutableListOf<ProveedoresEntity>()
    private lateinit var adapter: ProveedorAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBtn = activity?.findViewById(R.id.add_categoria)!!
        search = activity?.findViewById(R.id.search)!!
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)?.title = "Proveedores"
        activity?.findViewById<SearchView>(R.id.search)!!.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProveedorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        //cambia de a fragmento de agregar categoria
        proveedorViewModel.proveedoresList.observe(viewLifecycleOwner, Observer { currentProductList ->
            setProveedoresList(currentProductList)
        })

        proveedorViewModel.getAllProveedores()

        addBtn.setOnClickListener{
            changeAddFragment()
        }

    }

    fun initRecyclerView(){
        if(proveedorList.isNotEmpty()) binding.messageProveedores.visibility =View.GONE
        adapter = ProveedorAdapter()
        adapter.setListProducts(proveedorList)
        binding.rvProveedores.layoutManager = LinearLayoutManager(parentFragment?.context)
        binding.rvProveedores.adapter = adapter
    }

    private fun changeAddFragment(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,
            R.anim.fade_in, R.anim.fade_out)
        fragmentTransition.replace(R.id.fragment_container, RegisterProveedorFragment(), "registrar-proveedor")
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

    fun setProveedoresList(proveedores:MutableList<ProveedoresEntity>){
        proveedorList.clear()
        proveedorList = proveedores
        if(proveedorList.isNotEmpty()){
            binding.messageProveedores.visibility = View.GONE
        }else binding.messageProveedores.visibility = View.VISIBLE
        adapter.setListProducts(proveedorList)
    }

    /**opciones de la tarjeta del producto, ver detalles, eliminar, editar*/
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getPosition()
        val product = proveedorList[position]
        proveedorViewModel.itemSelect(item,product,parentFragmentManager)
        return true
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.VISIBLE
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)!!.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.findViewById<SearchView>(R.id.search)!!.visibility = View.VISIBLE
    }
}