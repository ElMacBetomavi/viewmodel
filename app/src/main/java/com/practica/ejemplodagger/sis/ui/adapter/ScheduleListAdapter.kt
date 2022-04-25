package com.practica.ejemplodagger.sis.ui.adapter

import android.annotation.SuppressLint
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ScheduleEntity

class ScheduleListAdapter() :
    RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>(), View.OnCreateContextMenuListener {

    private var scheduleList= emptyList<ScheduleEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListSchedule(schedule: List<ScheduleEntity>){
        this.scheduleList = schedule
        notifyDataSetChanged()
    }

    private var position:Int = 0

    fun getPosition():Int{
        return position
    }

    fun setPosition(position: Int){
        this.position=position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val schedule: TextView

        init {
            // Define click listener for the ViewHolder's View.
            schedule = view.findViewById(R.id.tv_schedule)

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_schedule, viewGroup, false)
        view.setOnCreateContextMenuListener(this)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val schedule = scheduleList[position]
        viewHolder.schedule.text="Horario: ${schedule.date}"

        viewHolder.itemView.setOnLongClickListener(View.OnLongClickListener {
            setPosition(position)
            false
        })
    }

    override fun getItemCount() = scheduleList.size

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(null);
        menu.add(0, view!!.id, 0, "Eliminar")
        menu.add(0, view.id, 0, "Editar");
    }

}