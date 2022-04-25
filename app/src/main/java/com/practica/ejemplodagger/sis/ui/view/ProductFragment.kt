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
import com.practica.ejemplodagger.databinding.FragmentProductBinding
import com.practica.ejemplodagger.sis.ui.adapter.ProductListAdapter
import com.practica.ejemplodagger.sis.viewmodel.ProductViewModel


class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var addBtn: FloatingActionButton
    private lateinit var search: SearchView
    private var productList = mutableListOf<ProductosEntity>()
    private lateinit var adapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBtn = activity?.findViewById(R.id.add_categoria)!!
        search = activity?.findViewById(R.id.search)!!
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)?.title = "Productos"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        //cambia de a fragmento de agregar categoria
        productViewModel.productList.observe(viewLifecycleOwner, Observer { currentProductList ->
            setCategoryList(currentProductList)
        })

        productViewModel.getAllProducts()
        addBtn.setOnClickListener{ addCategory() }

        search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                productViewModel.searchCategories(query!!)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query=="")productViewModel.getAllProducts()
                return true
            }
        }
        )
    }

    /**show the add new user window*/
    private fun addCategory(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,
            R.anim.fade_in, R.anim.fade_out)
        fragmentTransition.replace(R.id.fragment_container, RegistrarProductoFragment(), "registrar-producto")
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

    fun initRecyclerView(){
        if(productList.isNotEmpty()) binding.messageProducto.visibility =View.GONE
        adapter = ProductListAdapter()
        adapter.setListProducts(productList)
        binding.rvProducts.layoutManager = LinearLayoutManager(parentFragment?.context)
        binding.rvProducts.adapter = adapter
    }

    fun setCategoryList(products:MutableList<ProductosEntity>){
        productList.clear()
        productList = products
        if(productList.isNotEmpty()){
            binding.messageProducto.visibility = View.GONE
        }else binding.messageProducto.visibility = View.VISIBLE
        adapter.setListProducts(productList)
    }

    /**opciones de la tarjeta del producto, ver detalles, eliminar, editar*/
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getPosition()
        val product = productList[position]
        productViewModel.itemSelect(item,product,parentFragmentManager)
        return true
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.VISIBLE
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)!!.visibility = View.VISIBLE
    }

}