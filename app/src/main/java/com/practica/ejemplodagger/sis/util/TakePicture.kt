package com.practica.ejemplodagger.sis.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.util.*

class TakePicture {

    var PhotoPath=""

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("QueryPermissionsNeeded")
     fun dispatchTakePictureIntent(context:Context, activity:Activity):String {
        val REQUEST_IMAGE_CAPTURE = 1
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            activity.let {
                takePictureIntent.resolveActivity(it.packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile(context)
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri? = context.let { it1 ->
                            FileProvider.getUriForFile(
                                it1,
                                "com.practica.ejemplodagger.android.fileprovider",
                                it
                            )
                        }
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

                    }
                }
            }
        }
        return this.PhotoPath
    }


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
     fun createImageFile(context:Context): File {

        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val picture = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            PhotoPath = absolutePath
        }
        return picture
    }





}