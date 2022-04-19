package com.practica.ejemplodagger

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.practica.ejemplodagger.data.db.UsersDatabase
import com.practica.ejemplodagger.sis.di.component.DaggerUtilComponent
import com.practica.ejemplodagger.sis.di.component.UtilComponent
import com.practica.ejemplodagger.sis.di.module.ContextModule


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        utilComponent = DaggerUtilComponent.builder().contextModule(
            ContextModule(
                applicationContext
            )
        ).build()

        MainApplication.database =  Room.databaseBuilder(
            this, UsersDatabase::class.java,
            "productos-db")
            //.allowMainThreadQueries()
            .build()
    }

    companion object {
        lateinit var database: UsersDatabase

        private var utilComponent: UtilComponent? = null
        val appContext: Context?
            get() = utilComponent!!.appContext
    }
}