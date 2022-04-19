package com.practica.ejemplodagger.sis.di.component

import android.content.Context
import com.practica.ejemplodagger.sis.di.module.UtilModule
import dagger.Component

import javax.inject.Singleton


@Singleton
@Component(modules = [UtilModule::class])
interface UtilComponent {
    val appContext: Context?
}