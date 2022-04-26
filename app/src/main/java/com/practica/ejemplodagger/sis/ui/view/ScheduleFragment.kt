package com.practica.ejemplodagger.sis.ui.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ScheduleEntity
import com.practica.ejemplodagger.databinding.FragmentScheduleBinding
import com.practica.ejemplodagger.sis.ui.adapter.ScheduleListAdapter
import com.practica.ejemplodagger.sis.util.notificaciones.Notifications
import com.practica.ejemplodagger.sis.viewmodel.ScheduleViewModel

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private lateinit var addBtn: FloatingActionButton
    private var scheduleList =  mutableListOf<ScheduleEntity>()
    private var adapter = ScheduleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBtn = activity?.findViewById(R.id.add_categoria)!!
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)?.title = "Citas"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        scheduleViewModel.getAllSchedule()

        scheduleViewModel.scheduleList.observe(viewLifecycleOwner, Observer {
            scheduleList.clear()
            scheduleList=it
            adapter.setListSchedule(scheduleList)
        })

        addBtn.setOnClickListener{ addCategory() }

        createNotificaction()
    }

    private fun initRecyclerView(){
        adapter.setListSchedule(scheduleList)
        binding.rvSchedule.layoutManager = LinearLayoutManager(context)
        binding.rvSchedule.adapter = adapter
    }

    private fun addCategory(){
        val transition = parentFragmentManager
        val fragmentTransition = transition.beginTransaction()
        fragmentTransition.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,
            R.anim.fade_in, R.anim.fade_out)
        fragmentTransition.replace(R.id.fragment_container, RegisterDatePickerFragment(), "register_schedule")
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

    /**opciones de la tarjeta del producto, ver detalles, eliminar, editar*/
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getPosition()
        val schedule = scheduleList[position]
        scheduleViewModel.itemSelect(item,schedule,parentFragmentManager)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun createNotificaction(){
        val notification = Notifications()
        notification.createNotificationChannel()
        notification.createExplicitIntentForActivity()
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.VISIBLE
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)!!.visibility = View.VISIBLE
    }

}