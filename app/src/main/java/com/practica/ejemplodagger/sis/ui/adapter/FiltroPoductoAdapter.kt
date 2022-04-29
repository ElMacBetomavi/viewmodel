package com.practica.ejemplodagger.sis.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practica.ejemplodagger.R

class FiltroPoductoAdapter() :
    RecyclerView.Adapter<FiltroPoductoAdapter.ViewHolder>() {

    private var stringList= emptyList<String>()
    @SuppressLint("NotifyDataSetChanged")

    fun setListStrings(text: List<String>){
        this.stringList = text
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.itemView.setOnLongClickListener(null)
        super.onViewRecycled(holder)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView

        init {
            nombre = view.findViewById(R.id.item_general)
        }

        fun setStrings(text:String){
            nombre.text=text
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_filter, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val text = stringList[position]
        viewHolder.setStrings(text)
    }

    override fun getItemCount() = stringList.size



}