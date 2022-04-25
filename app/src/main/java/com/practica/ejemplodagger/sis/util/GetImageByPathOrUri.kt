package com.practica.ejemplodagger.sis.util

import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.practica.ejemplodagger.MainApplication
import java.io.File

class GetImageByPathOrUri() {

    val context = MainApplication.appContext

    fun getImage(image:String):String{
        val file = File(image)
        return if(file.exists()) {
            "path"
        }else{
            val uri = image.toUri()
            val sourceFile = DocumentFile.fromSingleUri(context!!, uri)
            if (sourceFile!!.exists()) "uri" else ""
        }
    }

}