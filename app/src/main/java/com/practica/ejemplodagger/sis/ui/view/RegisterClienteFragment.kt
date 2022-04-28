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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ClientesEntity
import com.practica.ejemplodagger.databinding.FragmentRegisterClienteBinding
import com.practica.ejemplodagger.sis.util.ClientErrorMessage
import com.practica.ejemplodagger.sis.viewmodel.RegisterClienteViewModel

private const val ARG_PARAM1 = "id"
private const val ARG_PARAM2 = "product_id"
private const val ARG_PARAM3 = "detail"

class RegisterClienteFragment : Fragment() {

    private var _binding: FragmentRegisterClienteBinding? = null
    private val binding get() = _binding!!
    private val registerClienteViewModel:RegisterClienteViewModel by viewModels()
    private var id: Int? = 0
    private var categoria: String? = null
    private var detailflat:String? = null
    private lateinit var initCliente: ClientesEntity
    var editFlag = false
    var newCliente = ClientesEntity(0)

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
    ): View {
        _binding = FragmentRegisterClienteBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCliente = ClientesEntity(0)

        initOptionsRegisterField()

        if (id!=0){
            registerClienteViewModel.setInitCategoryValues(id!!)
        }

        registerClienteViewModel.initCliente.observe(viewLifecycleOwner, Observer {
            setEditValue(it)
            if (detailflat=="detalles") setDetails()
        })

        registerClienteViewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,it, Toast.LENGTH_LONG).show()
        })

        registerClienteViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage->
            setErrorMessage(errorMessage)
        })

        registerClienteViewModel.changeFragment.observe(viewLifecycleOwner, Observer {
            changeFragment()
        })

        binding.saveBtn.setOnClickListener{
            getDataProveedor()
            newCliente.id = initCliente.id
            registerClienteViewModel.validateCliente(newCliente, editFlag)
        }

    }

    /**estable las opciones de los campos de opcion multiple del formulario*/
    private fun initOptionsRegisterField() {
        val itemsUnidades = listOf("Fijo", "Celular")
        val tipoCliente = listOf("Mayoreo", "Menudeo")
        val adapterUnidadMedida = ArrayAdapter(requireContext(), R.layout.list_item_options, itemsUnidades)
        (binding.tipoTelField.editText as? AutoCompleteTextView)?.setAdapter(adapterUnidadMedida)
        val adapterTipoCliente = ArrayAdapter(requireContext(), R.layout.list_item_options, tipoCliente)
        (binding.tipoCompraField.editText as? AutoCompleteTextView)?.setAdapter(adapterTipoCliente)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setDetails(){
        binding.nameField.isFocusable =false
        binding.apellidoPField.isFocusable =false
        binding.apellidoMField.isFocusable =false
        binding.calleField.isFocusable =false
        binding.saveBtn.visibility = View.GONE
        binding.numeroField.isFocusable =false
        binding.codigoPostalField.isFocusable =false
        binding.coloniaField.isFocusable =false
        binding.municipioField.isFocusable =false
        binding.estadoField.isFocusable =false
        binding.ladaField.isFocusable =false
        binding.telefonoField.isFocusable =false
        binding.tipoTelValue.isFocusable =false
        binding.interiorField.isFocusable=false
        binding.tipoTelField.isFocusable=false
        binding.labelP.text = "Detalles"
    }

    private fun setEditValue(initproveedor: ClientesEntity){
        editFlag=true
        initCliente =initproveedor
        binding.nameField.setText(initCliente.nombre)
        binding.apellidoPField.setText(initCliente.apellidoP)
        binding.apellidoMField.setText(initCliente.apellidoM)
        binding.tipoCompraValue.setText(initCliente.tipoCompra)
        binding.calleField.setText(initCliente.calle)
        binding.saveBtn.text = "editar"
        binding.numeroField.setText(initCliente.numero)
        binding.codigoPostalField.setText(initCliente.codigoPostal.toString())
        binding.coloniaField.setText(initCliente.colonia)
        binding.municipioField.setText(initCliente.municipio)
        binding.estadoField.setText(initCliente.estado)
        binding.ladaField.setText(initCliente.lada.toString())
        binding.telefonoField.setText(initCliente.telefono.toString())
        binding.tipoTelValue.setText(initCliente.tipoTel)
        binding.aliasField.setText(initCliente.alias)
        binding.labelP.text = "Editar Cliente"
    }

    /**configura los correspondientes mensajes de error en el formulario de las validaciones*/
    fun setErrorMessage(errorMessage: ClientErrorMessage){
        binding.nameField.error = errorMessage.nombre
        binding.calleField.error = errorMessage.calle
        binding.numeroField.error = errorMessage.numero
        binding.codigoPostalField.error = errorMessage.codigoPostal
        binding.coloniaField.error = errorMessage.colonia
        binding.municipioField.error = errorMessage.municipio
        binding.estadoField.error = errorMessage.estado
        binding.ladaField.error = errorMessage.lada
        binding.telefonoField.error = errorMessage.telefono
        binding.tipoTelField.error = errorMessage.tipoTel
    }

    private fun changeFragment(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, ClientesFragment(), "clientes")
        fragmentTransition.commit()
    }

    /**devuelve la informacion registrada en el formulario para posteriormente ser validada*/
    fun getDataProveedor(){
        newCliente.nombre = binding.nameField.text.toString()
        newCliente.apellidoP = binding.apellidoPField.text.toString()
        newCliente.apellidoM = binding.apellidoMField.text.toString()
        newCliente.tipoCompra = binding.tipoCompraValue.text.toString()
        newCliente.calle= binding.calleField.text.toString()
        newCliente.numero = binding.numeroField.text.toString()
        newCliente.interior = binding.interiorField.text.toString()
        newCliente.codigoPostal =binding.codigoPostalField.text.toString().toIntOrNull()
        newCliente.colonia= binding.coloniaField.text.toString()
        newCliente.municipio = binding.municipioField.text.toString()
        newCliente.estado=binding.estadoField.text.toString()
        newCliente.lada= binding.ladaField.text.toString().toIntOrNull()
        newCliente.telefono=binding.telefonoField.text.toString().toIntOrNull()
        newCliente.tipoTel=binding.tipoTelValue.text.toString()
        newCliente.alias=binding.aliasField.text.toString()
    }

}