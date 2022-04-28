package com.practica.ejemplodagger.sis.ui.adapter

import android.annotation.SuppressLint
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.ProveedoresEntity

class ProveedorAdapter :
    RecyclerView.Adapter<ProveedorAdapter.ViewHolder>(), View.OnCreateContextMenuListener {

    private var productsList= emptyList<ProveedoresEntity>()
    @SuppressLint("NotifyDataSetChanged")

    fun setListProducts(proveedores: List<ProveedoresEntity>){
        this.productsList = proveedores
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.itemView.setOnLongClickListener(null)
        super.onViewRecycled(holder)
    }

    private var position:Int = 0

    fun getPosition():Int{
        return position
    }

    fun setPosition(position: Int){
        this.position=position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView
        val categoria: TextView
        val marca: TextView
        val labelNombre:TextView
        val labelDom:TextView
        val labelTel:TextView

        init {
            // Define click listener for the ViewHolder's View.
            nombre = view.findViewById(R.id.nombre_label)
            categoria = view.findViewById(R.id.categoria_label)
            marca = view.findViewById(R.id.marca_label)
            labelNombre = view.findViewById(R.id.label_one)
            labelDom = view.findViewById(R.id.label_two)
            labelTel = view.findViewById(R.id.label_three)
        }
        fun setlabels(){
            labelNombre.text="Nombre: "
            labelDom.text = "Domicilio: "
            labelTel.text = "Telefono: "
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_producto, viewGroup, false)

        view.setOnCreateContextMenuListener(this)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nombre.text = productsList[position].nombre
        viewHolder.categoria.text = productsList[position].colonia
        viewHolder.marca.text = productsList[position].telefono.toString()
        viewHolder.setlabels()
        viewHolder.itemView.setOnLongClickListener(View.OnLongClickListener {
            setPosition(position)
            false
        })
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = productsList.size

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(null);
        menu.add(0, view!!.id, 0, "Ver detalles");//groupId, itemId, order, title
        menu.add(0, view.id, 0, "Eliminar")
        menu.add(0, view.id, 0, "Editar");
    }

}