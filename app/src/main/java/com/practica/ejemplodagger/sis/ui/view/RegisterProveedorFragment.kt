package com.practica.ejemplodagger.sis.ui.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ProveedoresEntity
import com.practica.ejemplodagger.databinding.FragmentRegisterProveedorBinding
import com.practica.ejemplodagger.sis.util.ProveedorErrorMessage
import com.practica.ejemplodagger.sis.viewmodel.RegisterProveedorViewModel

private const val ARG_PARAM1 = "id"
private const val ARG_PARAM2 = "product_id"
private const val ARG_PARAM3 = "detail"
class RegisterProveedorFragment : Fragment() {

    private var _binding: FragmentRegisterProveedorBinding? = null
    private val binding get() = _binding!!
    private val registerProveedorViewModel: RegisterProveedorViewModel by viewModels()
    private var id: Int? = 0
    private var categoria: String? = null
    private var detailflat:String? = null
    private lateinit var initProveedor: ProveedoresEntity
    var editFlag = false
    var newProveedor = ProveedoresEntity(0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_PARAM1,0)
            categoria = it.getString(ARG_PARAM2,"")
            detailflat = it.getString(ARG_PARAM3,"")
        }
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.GONE
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)!!.visibility = View.GONE

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterProveedorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProveedor = ProveedoresEntity(0)
        initOptionsRegisterField()

        if (id!=0){
            registerProveedorViewModel.setInitCategoryValues(id!!)
        }

        if (detailflat=="detalles") setDetails()

        registerProveedorViewModel.initProveedor.observe(viewLifecycleOwner, Observer {
            setEditValue(it)
        })

        registerProveedorViewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,it, Toast.LENGTH_LONG).show()
        })

        registerProveedorViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage->
            setErrorMessage(errorMessage)
        })

        registerProveedorViewModel.changeFragment.observe(viewLifecycleOwner, Observer {
            changeFragment()
        })

        binding.saveBtn.setOnClickListener{
            getDataProveedor()
            newProveedor.id = initProveedor.id
           registerProveedorViewModel.validateProveedor(newProveedor, editFlag)
        }
    }

    /**estable las opciones de los campos de opcion multiple del formulario*/
    private fun initOptionsRegisterField() {
        val itemsUnidades = listOf("Fijo", "Celular")
        val adapterUnidadMedida = ArrayAdapter(requireContext(), R.layout.list_item_options, itemsUnidades)
        (binding.tipoTelField.editText as? AutoCompleteTextView)?.setAdapter(adapterUnidadMedida)
    }

    /**devuelve la informacion registrada en el formulario para posteriormente ser validada*/
    fun getDataProveedor(){
        newProveedor.nombre = binding.nameField.text.toString()
        newProveedor.calle= binding.calleField.text.toString()
        newProveedor.numero = binding.numeroField.text.toString()
        newProveedor.codigoPostal =binding.codigoPostalField.text.toString().toIntOrNull()
        newProveedor.colonia= binding.coloniaField.text.toString()
        newProveedor.municipio = binding.municipioField.text.toString()
        newProveedor.estado=binding.estadoField.text.toString()
        newProveedor.contacto=binding.contactoField.text.toString()
        newProveedor.lada= binding.ladaField.text.toString().toIntOrNull()
        newProveedor.telefono=binding.telefonoField.text.toString().toIntOrNull()
        newProveedor.tipoTel=binding.tipoTelValue.text.toString()
    }

    /**configura los correspondientes mensajes de error en el formulario de las validaciones*/
    fun setErrorMessage(errorMessage: ProveedorErrorMessage){
        binding.nameField.error = errorMessage.nombre
        binding.calleField.error = errorMessage.calle
        binding.numeroField.error = errorMessage.numero
        binding.interiorField.error = errorMessage.intenrior
        binding.codigoPostalField.error = errorMessage.codigoPostal
        binding.coloniaField.error = errorMessage.colonia
        binding.municipioField.error = errorMessage.municipio
        binding.estadoField.error = errorMessage.estado
        binding.contactoField.error = errorMessage.contacto
        binding.ladaField.error = errorMessage.lada
        binding.telefonoField.error = errorMessage.telefono
        binding.tipoTelField.error = errorMessage.tipoTel
    }

    private fun changeFragment(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, ProveedorFragment(), "proveedor")
        fragmentTransition.commit()
    }

    /** configura los valores iniciales cuando se selecciona la opcion de editar*/
    private fun setEditValue(initproveedor: ProveedoresEntity){
        editFlag=true
        initProveedor =initproveedor
        binding.nameField.setText(initProveedor.nombre)
        binding.calleField.setText(initProveedor.calle)
        binding.saveBtn.text = "editar"
        binding.numeroField.setText(initProveedor.numero)
        binding.codigoPostalField.setText(initProveedor.codigoPostal.toString())
        binding.coloniaField.setText(initProveedor.colonia)
        binding.municipioField.setText(initProveedor.municipio)
        binding.estadoField.setText(initProveedor.estado)
        binding.contactoField.setText(initProveedor.contacto)
        binding.ladaField.setText(initProveedor.lada.toString())
        binding.telefonoField.setText(initProveedor.telefono.toString())
        binding.tipoTelValue.setText(initProveedor.tipoTel)
        binding.labelProveedor.text = "Editar Cliente"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDetails(){
        binding.nameField.isFocusable =false
        binding.calleField.isFocusable =false
        binding.saveBtn.visibility = View.GONE
        binding.numeroField.isFocusable =false
        binding.codigoPostalField.isFocusable =false
        binding.coloniaField.isFocusable =false
        binding.municipioField.isFocusable =false
        binding.estadoField.isFocusable =false
        binding.contactoField.isFocusable =false
        binding.ladaField.isFocusable =false
        binding.telefonoField.isFocusable =false
        binding.tipoTelValue.isFocusable =false
        binding.interiorField.isFocusable=false
        binding.tipoTelField.isFocusable=false
        binding.labelProveedor.text = "Detalles"
    }


}