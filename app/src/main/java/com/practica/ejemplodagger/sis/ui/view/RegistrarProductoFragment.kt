package com.practica.ejemplodagger.sis.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.databinding.FragmentRegistrarProductoBinding
import com.practica.ejemplodagger.sis.viewmodel.RegisterProductViewModel
import com.practica.ventasmoviles.sys.viewModel.productos.ErrorMessage


private const val ARG_PARAM1 = "id"
private const val ARG_PARAM2 = "product_id"
class RegistrarProductoFragment : Fragment() {

    private var _binding: FragmentRegistrarProductoBinding? = null
    private val binding get() = _binding!!
    private val registerProductViewModel: RegisterProductViewModel by viewModels()
    private var id: Int? = 0
    private var categoria: String? = null
    var PhotoPath=""
    var editFlag = false
    private lateinit var initProduct: ProductosEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_PARAM1,0)
            categoria = it.getString(ARG_PARAM2,"")
        }
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.GONE
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)!!.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrarProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProduct = ProductosEntity(0)

        if (id!=0){
            registerProductViewModel.setInitCategoryValues(id!!)
        }

        registerProductViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage->
            setErrorMessage(errorMessage)
        })

        registerProductViewModel.changeFragment.observe(viewLifecycleOwner, Observer {
            changeFragment()
        })

        registerProductViewModel.initProduct.observe(viewLifecycleOwner, Observer {
            setEditValue(it)
        })

        registerProductViewModel.categories.observe(viewLifecycleOwner, Observer { categoriesList ->
            initOptionsRegisterField(categoriesList)
        })

        registerProductViewModel.setMultiopcionsField()

        binding.saveBtn.setOnClickListener{
            val producto:ProductosEntity = getDataProducto()
            producto.id = initProduct.id
            producto.imagen = PhotoPath
            registerProductViewModel.validateCategoria(producto, editFlag)
        }


    }

    /**estable las opciones de los campos de opcion multiple del formulario*/
    private fun initOptionsRegisterField(categoriesList: MutableList<String>) {
        val itemsUnidades = listOf("Pieza", "Litro", "Paquete", "Metros")
        val adapterCategory = ArrayAdapter(requireContext(), R.layout.list_item_options, categoriesList)
        (binding.categoriaField.editText as? AutoCompleteTextView)?.setAdapter(adapterCategory)
        val adapterUnidadMedida = ArrayAdapter(requireContext(), R.layout.list_item_options, itemsUnidades)
        (binding.unidadMedidaField.editText as? AutoCompleteTextView)?.setAdapter(adapterUnidadMedida)
    }

    /**configura los correspondientes mensajes de error en el formulario de las validaciones*/
    fun setErrorMessage(errorMessage: ErrorMessage){
        binding.nameField.error = errorMessage.name
        binding.costoField.error = errorMessage.costo
        binding.categoriaField.error = errorMessage.categoria
        binding.precioMayoField.error = errorMessage.precioMayoreo
        binding.precioMenuField.error = errorMessage.precioMenudeo
        binding.marcaField.error = errorMessage.marca
        binding.colorField.error = errorMessage.color
        binding.unidadMedidaField.error = errorMessage.unidadDeMedida
        binding.cantidadMinField.error = errorMessage.cantidad
    }

    private fun changeFragment(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, ProductFragment(), "producto")
        fragmentTransition.commit()
    }

    /**devuelve la informacion registrada en el formulario para posteriormente ser validada*/
    fun getDataProducto():ProductosEntity{
        return ProductosEntity(0,
            PhotoPath,
            binding.nameField.text.toString(),
            binding.costoField.text.toString().toFloatOrNull(),
            binding.precioMenuField.text.toString().toFloatOrNull(),
            binding.precioMayoField.text.toString().toFloatOrNull(),
            binding.categoriaValue.text.toString(),
            binding.marcaField.text.toString(),
            binding.colorField.text.toString(),
            binding.unidadMedidaValue.text.toString(),
            binding.cantidadMinField.text.toString().toIntOrNull(),
        )
    }

    private fun setEditValue(initProducto: ProductosEntity){
        editFlag=true
        initProduct =initProducto
        binding.categoriaValue.setText(initProduct.categoria)
        binding.unidadMedidaValue.setText(initProduct.unidadMedida)
        binding.saveBtn.text = "editar"
        binding.nameField.setText(initProduct.nombre)
        binding.costoField.setText(initProduct.costo.toString())
        binding.precioMenuField.setText(initProduct.precioMenudeo.toString())
        binding.precioMayoField.setText(initProduct.precioMayoreo.toString())
        binding.marcaField.setText(initProduct.marca)
        binding.colorField.setText(initProduct.color)
        binding.cantidadMinField.setText(initProduct.cantidadMin.toString())
    }

}