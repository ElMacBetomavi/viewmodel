package com.practica.ejemplodagger.sis.util

import android.annotation.SuppressLint
import com.practica.ejemplodagger.data.entities.ScheduleEntity
import com.practica.ejemplodagger.data.repository.ScheduleRepository

class SetInitScheduleValues {
    /**si se selecciono editar,carga los valores previos a editar en el formulario*/
    @SuppressLint("SetTextI18n")
    suspend fun getInitSchedule(id:Int): ScheduleEntity {
        var initSchedule = ScheduleEntity(0,"")
        val repository = ScheduleRepository()
        val categorias = repository.getAll()

        categorias.map { schedule ->
            if(schedule.id == id) initSchedule = schedule
        }
        return  initSchedule
    }
}