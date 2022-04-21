package com.practica.ejemplodagger.sis.di.component

import android.content.Context
import com.practica.ejemplodagger.sis.di.module.UtilModule
import com.practica.ejemplodagger.sis.viewmodel.MainViewModel
import dagger.Component

import javax.inject.Singleton


@Singleton
@Component(modules = [UtilModule::class])
interface UtilComponent {

    val appContext: Context?
    fun inject(mainViewModel: MainViewModel)

}