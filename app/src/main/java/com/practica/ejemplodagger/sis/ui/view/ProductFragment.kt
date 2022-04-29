package com.practica.ejemplodagger.sis.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.databinding.FragmentProductBinding
import com.practica.ejemplodagger.sis.ui.adapter.FiltroPoductoAdapter
import com.practica.ejemplodagger.sis.ui.adapter.ProductListAdapter
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteAlertDialog
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteProductDialog
import com.practica.ejemplodagger.sis.ui.view.alerdialog.FileterProductDialog
import com.practica.ejemplodagger.sis.util.ChangeFragment
import com.practica.ejemplodagger.sis.viewmodel.ProductViewModel


class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var addBtn: FloatingActionButton
    private lateinit var search: SearchView
    private lateinit var filtro: ImageButton
    private var productList = mutableListOf<ProductosEntity>()
    private lateinit var adapter: ProductListAdapter

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBtn = activity?.findViewById(R.id.add_categoria)!!
        search = activity?.findViewById(R.id.search)!!
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)?.title = "Productos"
        filtro = activity?.findViewById(R.id.filter)!!
        filtro.visibility =  View.VISIBLE
        val colorValue = ContextCompat.getColor(requireContext(), R.color.transparent)
        filtro.setBackgroundColor(colorValue)
        val image = requireContext().getDrawable(R.drawable.ic_baseline_filter_alt_24)
        filtro.setImageDrawable(image)

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

        productViewModel.filterTxtList.observe(viewLifecycleOwner, Observer {
            setFilterView(it)
        })

        productViewModel.getAllProducts()

        addBtn.setOnClickListener{
            addCategory()
        }

        filtro.setOnClickListener{
            filters()
        }

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
        val changeFragment = ChangeFragment()
        changeFragment.change(0,"",RegistrarProductoFragment() ,parentFragmentManager,true)
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

    /**setea las opciones de filtro de producto del app menu */
    fun filters(){
        val alert = FileterProductDialog{filter -> optionFilterSelected(filter)}
        alert.show(parentFragmentManager, FileterProductDialog.TAG)
    }

    fun optionFilterSelected(filter:String){
        productViewModel.setFilter(filter)
    }

    fun setFilterView(textList:MutableList<String>){

        if(textList.isNotEmpty()) {
            binding.rvProducts.visibility = View.GONE
            binding.rvFilter.visibility = View.VISIBLE
            binding.messageProducto.visibility =View.GONE
            binding.messageProducto.text = "no hay registro"
            val filterAdapter = FiltroPoductoAdapter()
            filterAdapter.setListStrings(textList)
            binding.rvFilter.layoutManager = LinearLayoutManager(parentFragment?.context)
            binding.rvFilter.adapter = filterAdapter
        }else{
            binding.rvProducts.visibility = View.VISIBLE
            binding.rvFilter.visibility = View.GONE
            productViewModel.getAllProducts()
        }

    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.VISIBLE
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)!!.visibility = View.VISIBLE
    }

}