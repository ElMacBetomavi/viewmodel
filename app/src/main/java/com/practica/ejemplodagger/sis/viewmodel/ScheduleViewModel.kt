package com.practica.ejemplodagger.sis.viewmodel

import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.data.domain.EditScheduleUseCase
import com.practica.ejemplodagger.data.entities.ScheduleEntity
import com.practica.ejemplodagger.data.repository.ScheduleRepository
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteAlertDialog
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DeleteScheduleDialog
import kotlinx.coroutines.launch

class ScheduleViewModel:ViewModel() {

    val repository = ScheduleRepository()
    var scheduleList = MutableLiveData<MutableList<ScheduleEntity>>()

    fun getAllSchedule(){
        viewModelScope.launch {
            val currentScheduleList = repository.getAll()
            scheduleList.postValue(currentScheduleList)
        }
    }

    fun deleteSchedule(schedule: ScheduleEntity){
        viewModelScope.launch {
            repository.delete(schedule)
            getAllSchedule()
        }
    }

    /**atiende la seleccion del context menu de cada item del rv*/
    fun itemSelect(item: MenuItem, schedule: ScheduleEntity, fragmentManager: FragmentManager){
        when (item.title) {
            "Eliminar" -> {
                val alert = DeleteScheduleDialog(schedule){ deleteCategoria -> deleteSchedule(deleteCategoria) }
                alert.show(fragmentManager, DeleteAlertDialog.TAG)
            }
            "Editar" -> {
                val editarSchedule = EditScheduleUseCase()
                editarSchedule.editar(schedule.id, fragmentManager)
            }
            else -> println("otro")
        }
    }
}