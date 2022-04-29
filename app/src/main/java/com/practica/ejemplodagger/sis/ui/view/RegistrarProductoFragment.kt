package com.practica.ejemplodagger.sis.ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ProductosEntity
import com.practica.ejemplodagger.databinding.FragmentRegistrarProductoBinding
import com.practica.ejemplodagger.sis.ui.view.alerdialog.ImageExistAlertDialog
import com.practica.ejemplodagger.sis.ui.view.alerdialog.SelectSourcePicDialog
import com.practica.ejemplodagger.sis.viewmodel.RegisterProductViewModel
import com.practica.ventasmoviles.sys.viewModel.productos.ErrorMessage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*

private const val ARG_PARAM1 = "id"
private const val ARG_PARAM2 = "product_id"
@Suppress("DEPRECATION")
class RegistrarProductoFragment : Fragment() {

    private var _binding: FragmentRegistrarProductoBinding? = null
    private val binding get() = _binding!!
    private val registerProductViewModel: RegisterProductViewModel by viewModels()
    private var id: Int? = 0
    private var categoria: String? = null
    var PhotoPath=""
    var editFlag = false
    private lateinit var initProduct: ProductosEntity
    var newProduct = ProductosEntity(0)

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

    @RequiresApi(Build.VERSION_CODES.N)
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

        registerProductViewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,it,Toast.LENGTH_LONG).show()
        })

        registerProductViewModel.setMultiopcionsField()

        binding.saveBtn.setOnClickListener{
            getDataProducto()
            newProduct.id = initProduct.id
            registerProductViewModel.validateCategoria(newProduct, editFlag)
        }

        binding.imageField.setOnClickListener{
            selectImage()
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
    fun getDataProducto(){
        newProduct.nombre = binding.nameField.text.toString()
        newProduct.costo= binding.costoField.text.toString().toFloatOrNull()
        newProduct.precioMenudeo = binding.precioMenuField.text.toString().toFloatOrNull()
        newProduct.precioMayoreo=binding.precioMayoField.text.toString().toFloatOrNull()
        newProduct.categoria =binding.categoriaValue.text.toString()
        newProduct.marca= binding.marcaField.text.toString()
        newProduct.color = binding.colorField.text.toString()
        newProduct.unidadMedida=binding.unidadMedidaValue.text.toString()
        newProduct.cantidadMin=binding.cantidadMinField.text.toString().toIntOrNull()
    }

    /** configura los valores iniciales cuando se selecciona la opcion de editar*/
    private fun setEditValue(initProducto: ProductosEntity){
        editFlag=true
        initProduct =initProducto
        PhotoPath = initProduct.imagen!!
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

        val file = File(initProduct.imagen!!)
        if(file.exists()){
            newProduct.imagen = PhotoPath
            val bitmap: Bitmap = BitmapFactory.decodeFile(initProduct.imagen)
            val imageScaled = Bitmap.createScaledBitmap(bitmap, 550, 400, false)
            binding.imageField.setImageBitmap(imageScaled)
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1
    val PICK_IMAGE = 2

    @RequiresApi(Build.VERSION_CODES.N)
    fun selectImage(){
        val file = File(newProduct.imagen!!)
        if(file.exists()){
            val alert = ImageExistAlertDialog{ -> showSelectPicsourchalert() }
            alert.show(parentFragmentManager, ImageExistAlertDialog.TAG)
        }else {
            val uri = PhotoPath.toUri()
            val sourceFile = DocumentFile.fromSingleUri(requireContext(), uri)
            if (sourceFile!!.exists()) {
                val alert = ImageExistAlertDialog{ -> showSelectPicsourchalert() }
                alert.show(parentFragmentManager, ImageExistAlertDialog.TAG)
            }else{
                showSelectPicsourchalert()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun showSelectPicsourchalert(){
        val alert = SelectSourcePicDialog({-> selectPictureGalery()},
            {-> dispatchTakePictureIntent()})
        alert.show(parentFragmentManager, "imagen")
    }

    fun selectPictureGalery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    // codigo para tomar foto con camara
    @RequiresApi(Build.VERSION_CODES.N)
    private fun dispatchTakePictureIntent() {
        println("tomar foto 100+")
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            activity?.let {
                takePictureIntent.resolveActivity(it.packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri? = context?.let { it1 ->
                            FileProvider.getUriForFile(
                                it1,
                                "com.practica.ejemplodagger.android.fileprovider",
                                it
                            )
                        }
                        println("uri path $photoURI")
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    /**crea la ruta de la imagen tomada en la intent de la camara*/
    @RequiresApi(Build.VERSION_CODES.N)
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val picture = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            if (absolutePath != null){
                PhotoPath = absolutePath
            }
        }
        return picture
    }

    /**atiende las acciones de los intent de seleccion de imagen de galeria
     * o tomar fotografia con la camara*/
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            newProduct.imagen = PhotoPath
            val bitmap: Bitmap = BitmapFactory.decodeFile(PhotoPath)
            val imageScaled = Bitmap.createScaledBitmap(bitmap, 550, 400, false)
            binding.imageField.setImageBitmap(imageScaled)
        }

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            val imageUri : Uri? = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, imageUri)
            val file = createImageFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
            val bArray = bos.toByteArray()
            file.writeBytes(bArray)
            binding.imageField.setImageBitmap(bitmap)
            newProduct.imagen = PhotoPath
        }
    }

}