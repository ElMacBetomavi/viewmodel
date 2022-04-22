package com.practica.ejemplodagger.sis.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.databinding.FragmentRegisterDatePickerBinding
import com.practica.ejemplodagger.sis.ui.view.alerdialog.DatePickerFragment
import com.practica.ejemplodagger.sis.ui.view.alerdialog.TimePickerFragment


class RegisterDatePickerFragment : Fragment() {

    private var _binding: FragmentRegisterDatePickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDate.setOnClickListener{ showDatePickerDialog()}
        binding.etHour.setOnClickListener{showTimePickerDialog()}
    }

    fun showDatePickerDialog(){
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker")
    }

    @SuppressLint("SetTextI18n")
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.etDate.setText("Has seleccionado el $day del $month del año $year")
    }

    fun showTimePickerDialog(){
        val timePicker =TimePickerFragment{hour, minute -> onTimeSelected(hour,minute)}
        timePicker.show(parentFragmentManager, "timePicker")
    }

    @SuppressLint("SetTextI18n")
    fun onTimeSelected(hour:Int, minute:Int){
        binding.etHour.setText("Has seleccionado la hora "+String.format("%02d:%02d",hour,minute)+"hrs")
    }

}