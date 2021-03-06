package com.practica.ejemplodagger.sis.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.databinding.FragmentCategoriaBinding
import com.practica.ejemplodagger.sis.ui.adapter.CategoriaListAdapter
import com.practica.ejemplodagger.sis.viewmodel.CategoriaViewModel


class CategoriaFragment : Fragment() {

    private var _binding: FragmentCategoriaBinding? = null
    private val binding get() = _binding!!
    private val categoriaViewModel: CategoriaViewModel by viewModels()
    private lateinit var adapter: CategoriaListAdapter
    private lateinit var categoriaList:MutableList<CategoriaEntity>
    private lateinit var addBtn:FloatingActionButton
    private lateinit var search:SearchView

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         addBtn = activity?.findViewById(R.id.add_categoria)!!
         search = activity?.findViewById(R.id.search)!!
         activity?.findViewById<MaterialToolbar>(R.id.topAppBar)?.title = "Categorias"
         activity?.findViewById<ImageButton>(R.id.filter)!!.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        //carga los valores de la lista de categoria
        categoriaViewModel.getAllCategorias()

        //cambia los valores de alista cando se actualiza
        categoriaViewModel.cateogriaList.observe(viewLifecycleOwner, Observer { currentCategoriaList ->
            setCategoryList(currentCategoriaList)
        })

        //cambia de a fragmento de agregar categoria
        addBtn.setOnClickListener{ addCategory() }

        //atiende el search view encargado de filtrar la lista de categorias
        search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    categoriaViewModel.searchCategories(query!!)
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (query=="")categoriaViewModel.getAllCategorias()
                    return true
                }
            }
        )

    }

    fun initRecyclerView(){
        categoriaList= emptyList<CategoriaEntity>().toMutableList()
        adapter = CategoriaListAdapter()
        adapter.setListProducts(categoriaList)
        binding.rvCategorias.layoutManager = LinearLayoutManager(context)
        binding.rvCategorias.adapter = adapter
    }

    fun setCategoryList(category:MutableList<CategoriaEntity>){
        categoriaList.clear()
        categoriaList = category
        if(categoriaList.isNotEmpty()){
            binding.messageCategoria.visibility = View.GONE
        }else binding.messageCategoria.visibility = View.VISIBLE
        adapter.setListProducts(categoriaList)
    }

    /**show the add new user window*/
    private fun addCategory(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,
                                               R.anim.fade_in, R.anim.fade_out)
        fragmentTransition.replace(R.id.fragment_container, RegisterCategoriyFragment(), "register_categoria")
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

    /**opciones de la tarjeta del producto, ver detalles, eliminar, editar*/
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getPosition()
        val categoria = categoriaList[position]
        categoriaViewModel.itemSelect(item,categoria,parentFragmentManager)
        return true
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.VISIBLE
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)!!.visibility = View.VISIBLE
    }

}