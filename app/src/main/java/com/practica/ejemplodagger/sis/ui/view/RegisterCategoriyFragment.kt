package com.practica.ejemplodagger.sis.ui.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.databinding.FragmentRegisterCategoriyBinding
import com.practica.ejemplodagger.sis.ui.view.alerdialog.ImageExistAlertDialog
import com.practica.ejemplodagger.sis.util.CategoriaErrorMessage
import com.practica.ejemplodagger.sis.viewmodel.RegisterCategoryViewModel
import java.io.File
import java.io.IOException
import java.util.*

private const val ARG_PARAM1 = "id"
private const val ARG_PARAM2 = "product_id"

class RegisterCategoriyFragment : Fragment() {

    private var _binding: FragmentRegisterCategoriyBinding? = null
    private val binding get() = _binding!!
    private val registerCategoryViewModel: RegisterCategoryViewModel by viewModels()
    private var id: Int? = 0
    private var categoria: String? = null
    var PhotoPath=""
    var editFlag = false
    private lateinit var initcategoria:CategoriaEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_PARAM1,0)
            categoria = it.getString(ARG_PARAM2,"")
        }
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterCategoriyBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initcategoria = CategoriaEntity(0,"","")
        if (id!=0){
            registerCategoryViewModel.setInitCategoryValues(id!!)
        }
        registerCategoryViewModel.errorMessageCategoria.observe(viewLifecycleOwner, Observer { errormessage->
            setErrorMessage(errormessage)
        })

        registerCategoryViewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,it, Toast.LENGTH_LONG).show()
        })

        registerCategoryViewModel.changeFragment.observe(viewLifecycleOwner, Observer {
            changeFragment()
        })

        registerCategoryViewModel.initcategoria.observe(viewLifecycleOwner, Observer {
            setEditValue(it)
        })

        binding.saveCategoriaBtn.setOnClickListener{
            val categoria: CategoriaEntity = getCategoria()
            categoria.id = initcategoria.id
            categoria.image = PhotoPath
            registerCategoryViewModel.validateCategoria(categoria, editFlag)
        }

        binding.imageField.setOnClickListener{
            selectImage()
        }

    }

    private fun setErrorMessage(errorMessage: CategoriaErrorMessage){
        binding.categoriaRegisterField.error = errorMessage.name
        binding.descripcionCategoriaField.error = errorMessage.description
    }

    private fun getCategoria():CategoriaEntity{
        return CategoriaEntity(0,
            binding.categoriaRegisterField.text.toString(),
            binding.descripcionCategoriaField.text.toString(),
            PhotoPath
        )
    }

    private fun changeFragment(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, CategoriaFragment(), "categoria")
        fragmentTransition.commit()
    }

    /**si se selecciono editar,carga los valores previos a editar en el formulario*/
    private fun setEditValue(initCategory:CategoriaEntity){
        editFlag=true
        initcategoria =initCategory
        binding.categoriaRegisterField.setText(initCategory.name)
        binding.descripcionCategoriaField.setText(initCategory.description)
        binding.categoriaRegisterField.isFocusable = false
        val file = File(initCategory.image!!)
        if(file.exists()){
            val bitmap = BitmapFactory.decodeFile(initCategory.image)
            PhotoPath = initCategory.image!!
            binding.imageField.setImageBitmap(bitmap)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun selectImage(){
        val file = File(PhotoPath!!)
        if(file.exists()){
            val alert = ImageExistAlertDialog(){ dispatchTakePictureIntent() }
            alert.show(parentFragmentManager, ImageExistAlertDialog.TAG)
        }else dispatchTakePictureIntent()
    }

    val REQUEST_IMAGE_CAPTURE = 1
    val PICK_IMAGE = 2

    @RequiresApi(Build.VERSION_CODES.N)
    private fun dispatchTakePictureIntent() {
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap: Bitmap = BitmapFactory.decodeFile(PhotoPath)
            val matrix = Matrix()
            matrix.postRotate(90F)
            var widthImg = 0.0
            var hightImg = 0.0
            if (bitmap.width > bitmap.height){
                widthImg= bitmap.width*0.6
                hightImg = bitmap.height*1.0
            }else{
                widthImg = bitmap.width*1.0
                hightImg = bitmap.height*0.6
                matrix.postRotate(-90F)
            }
            val rotateBitmap = Bitmap.createBitmap(bitmap,0,0,widthImg.toInt(),hightImg.toInt(), matrix,true)
            val imageScaled = Bitmap.createScaledBitmap(rotateBitmap, 550, 400, false)
            binding.imageField.setImageBitmap(imageScaled)
        }

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
            val imageUri : Uri? = data?.data
            println("uri $imageUri")
            PhotoPath = imageUri?.toString()!!
            binding.imageField.setImageURI(imageUri)
        }
    }

}