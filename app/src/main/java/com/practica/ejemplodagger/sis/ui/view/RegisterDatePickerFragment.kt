package com.practica.ejemplodagger.sis.ui.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.CategoriaEntity
import com.practica.ejemplodagger.data.entities.ScheduleEntity
import com.practica.ejemplodagger.databinding.FragmentRegisterDatePickerBinding
import com.practica.ejemplodagger.sis.util.notificaciones.Notifications
import com.practica.ejemplodagger.sis.viewmodel.RegisterDatePickerViewModel

private const val ARG_PARAM1 = "id"

class RegisterDatePickerFragment : Fragment() {

    private var _binding: FragmentRegisterDatePickerBinding? = null
    private val binding get() = _binding!!
    private val registerDatePickerViewModel: RegisterDatePickerViewModel by viewModels()
    var dateFormat = ""
    var timeFormat = ""
    private var id: Int? = 0
    var editFlag = false
    private lateinit var initSchedule: ScheduleEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_PARAM1,0)
        }
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.GONE
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)!!.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterDatePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSchedule = ScheduleEntity(0,"")
        if (id!=0){
            registerDatePickerViewModel.setInitCategoryValues(id!!)
        }

        registerDatePickerViewModel.messageDate.observe(viewLifecycleOwner, Observer {
            binding.etDate.setText(it)
        })

        registerDatePickerViewModel.messageHour.observe(viewLifecycleOwner, Observer {
            binding.etHour.setText(it)
        })

        registerDatePickerViewModel.valDate.observe(viewLifecycleOwner, Observer {
            dateFormat = it
        })

        registerDatePickerViewModel.valTime.observe(viewLifecycleOwner, Observer {
            timeFormat= it
        })

        registerDatePickerViewModel.changeFragment.observe(viewLifecycleOwner, Observer {
            changeFragment()
        })

        registerDatePickerViewModel.initSchedule.observe(viewLifecycleOwner, Observer {
            setEditValue(it)
        })

        binding.etDate.setOnClickListener{
            registerDatePickerViewModel.showDatePickerDialog(parentFragmentManager)
        }

        binding.etHour.setOnClickListener{
            registerDatePickerViewModel.showTimePickerDialog(parentFragmentManager)
        }

        binding.saveDateBtn.setOnClickListener{
            addSchedule()
        }



    }

    fun addSchedule(){
        if (dateFormat =="" || timeFormat==""){
            Toast.makeText(context,"seleccione hora y fecha", Toast.LENGTH_LONG).show()
        }else{
            val myDate= "${dateFormat}T$timeFormat:00"
            val schedule = ScheduleEntity(initSchedule.id, myDate)
            registerDatePickerViewModel.addSchedule(schedule, editFlag)
        }
    }

    private fun changeFragment(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, ScheduleFragment(), "schedule")
        fragmentTransition.commit()
    }

    /**si se selecciono editar,carga los valores previos a editar en el formulario*/
    private fun setEditValue(initschedule:ScheduleEntity){
        editFlag=true
        initSchedule =initschedule
        binding.etDate.setText(initschedule.date)
        binding.etHour.setText(initschedule.date)
        binding.etDate.isFocusable = false
        binding.saveDateBtn.text = "editar"
        binding.label.text = "Editar"
    }



}