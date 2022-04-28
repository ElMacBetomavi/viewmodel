package com.practica.ejemplodagger.sis.ui.view

import android.annotation.SuppressLint
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
import com.practica.ejemplodagger.data.entities.ClientesEntity
import com.practica.ejemplodagger.databinding.FragmentClientesBinding
import com.practica.ejemplodagger.sis.ui.adapter.ClientesAdapter
import com.practica.ejemplodagger.sis.viewmodel.ClientesViewModel


class ClientesFragment : Fragment() {

    private var _binding: FragmentClientesBinding? = null
    private val binding get() = _binding!!
    private val clientesViewModel:ClientesViewModel by viewModels()
    private lateinit var addBtn: FloatingActionButton
    private lateinit var search: SearchView
    private lateinit var adapter: ClientesAdapter
    private var clientesList = mutableListOf<ClientesEntity>()

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBtn = activity?.findViewById(R.id.add_categoria)!!
        search = activity?.findViewById(R.id.search)!!
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)?.title = "Clientes"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        clientesViewModel.clientesList.observe(viewLifecycleOwner, Observer { currentClientList ->
            setProveedoresList(currentClientList)
        })

        clientesViewModel.getAllClientes()

        addBtn.setOnClickListener{
            changeAddFragment()
        }

        //atiende el search view encargado de filtrar la lista de categorias
        search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clientesViewModel.searchCategories(query!!)
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (query=="")clientesViewModel.getAllClientes()
                    return true
                }
            }
        )

    }

    fun initRecyclerView(){
        if(clientesList.isNotEmpty()) binding.messageClientes.visibility =View.GONE
        adapter = ClientesAdapter()
        adapter.setListProducts(clientesList)
        binding.rvClientes.layoutManager = LinearLayoutManager(parentFragment?.context)
        binding.rvClientes.adapter = adapter
    }

    fun setProveedoresList(clientes:MutableList<ClientesEntity>){
        clientesList.clear()
        clientesList = clientes
        if(clientesList.isNotEmpty()){
            binding.messageClientes.visibility = View.GONE
        }else binding.messageClientes.visibility = View.VISIBLE
        adapter.setListProducts(clientesList)
    }

    private fun changeAddFragment(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,
            R.anim.fade_in, R.anim.fade_out)
        fragmentTransition.replace(R.id.fragment_container, RegisterClienteFragment(), "registrar-cliente")
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

    /**opciones de la tarjeta del producto, ver detalles, eliminar, editar*/
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getPosition()
        val product = clientesList[position]
        clientesViewModel.itemSelect(item,product,parentFragmentManager)
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