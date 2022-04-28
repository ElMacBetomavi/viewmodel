package com.practica.ejemplodagger.sis.ui.adapter

import android.annotation.SuppressLint
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ClientesEntity

class ClientesAdapter() :
    RecyclerView.Adapter<ClientesAdapter.ViewHolder>(), View.OnCreateContextMenuListener {

    private var clientesList = emptyList<ClientesEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListProducts(products: List<ClientesEntity>) {
        this.clientesList = products
        notifyDataSetChanged()
    }

    private var position: Int = 0

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val description: TextView

        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.categoria_item)
            description = view.findViewById(R.id.descripcion_item)
        }

        @SuppressLint("SetTextI18n")
        fun setInformation(cliente: ClientesEntity) {

            name.text = "Nombre: " + cliente.nombre
            description.text = "Direccion: ${cliente.calle} ${cliente.numero} ${cliente.colonia}"
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_categoria, viewGroup, false)
        view.setOnCreateContextMenuListener(this)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val cliente = clientesList[position]
        viewHolder.setInformation(cliente)

        viewHolder.itemView.setOnLongClickListener(View.OnLongClickListener {
            setPosition(position)
            false
        })
    }

    override fun getItemCount() = clientesList.size

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(null);
        menu.add(0, view!!.getId(), 0, "Ver detalles");//groupId, itemId, order, title
        menu.add(0, view.getId(), 0, "Eliminar")
        menu.add(0, view.getId(), 0, "Editar");
        menu.add(0, view.getId(), 0, "Llamar");
    }
}

