package com.practica.ejemplodagger.sis.viewmodel

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.data.entities.ScheduleEntity
import com.practica.ejemplodagger.data.repository.ScheduleRepository
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DatePickerFragment
import com.practica.ejemplodagger.sis.ui.view.alerdialog.TimePickerFragment
import com.practica.ejemplodagger.sis.util.SetInitCategoryValues
import com.practica.ejemplodagger.sis.util.SetInitScheduleValues
import kotlinx.coroutines.launch

class RegisterDatePickerViewModel: ViewModel() {

    @SuppressLint("StaticFieldLeak")
    val context =  MainApplication.appContext
    val messageDate = MutableLiveData<String>()
    val messageHour = MutableLiveData<String>()
    val scheduleList = MutableLiveData<MutableList<ScheduleEntity>>()
    val valDate = MutableLiveData<String>()
    val valTime = MutableLiveData<String>()
    private val repository =  ScheduleRepository()
    val changeFragment = MutableLiveData<Boolean>()
    val initSchedule = MutableLiveData<ScheduleEntity>()

    //seleccion de fecha
    fun showDatePickerDialog(fragmentManager: FragmentManager){
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(fragmentManager, "datePicker")
    }

    @SuppressLint("SetTextI18n")
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        valDate.postValue("$year-${String.format("%02d",month)}-${String.format("%02d",day)}")
        messageDate.postValue("Has seleccionado el $day del $month del año $year")
    }

    //seleccion de hora
    fun showTimePickerDialog(fragmentManager: FragmentManager){
        val timePicker = TimePickerFragment{ hour, minute -> onTimeSelected(hour,minute)}
        timePicker.show(fragmentManager, "timePicker")
    }

    @SuppressLint("SetTextI18n")
    fun onTimeSelected(hour:Int, minute:Int){
        valTime.postValue(String.format("%02d:%02d",hour,minute))
        messageHour.postValue("Has seleccionado la hora "+String.format("%02d:%02d",hour,minute)+"hrs")
    }

    //agrega/modificar  cita
    fun addSchedule(schedule:ScheduleEntity, editFlag:Boolean){
        viewModelScope.launch {
            if (!editFlag){
                repository.add(schedule)
                Toast.makeText(context,"cita agregada", Toast.LENGTH_LONG).show()
            }else {
                repository.update(schedule)
                Toast.makeText(context,"cita modificada", Toast.LENGTH_LONG).show()
            }
            val currentScheduleList = repository.getAll()
            scheduleList.postValue(currentScheduleList)
            changeFragment.postValue(true)

        }
    }

    fun setInitCategoryValues(id:Int){
        viewModelScope.launch {
            val currentInitSchedule = SetInitScheduleValues().getInitSchedule(id!!)
            initSchedule.postValue(currentInitSchedule)
        }
    }




}