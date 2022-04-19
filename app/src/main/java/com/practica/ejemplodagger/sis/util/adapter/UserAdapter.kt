package com.practica.ejemplodagger.sis.util.adapter

import android.annotation.SuppressLint
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.data.entities.UserEntity
import java.util.zip.Inflater

class UserAdapter :
    RecyclerView.Adapter<UserAdapter.ViewHolder>(), View.OnCreateContextMenuListener {

    private var userList = emptyList<UserEntity>()
    private var position:Int = 0

    fun getPosition():Int{
        return position
    }

    private fun setPosition(position: Int){
        this.position=position
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUserLists(products: List<UserEntity>){
        this.userList
        this.userList = products
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name_label)
        private val lastname: TextView = view.findViewById(R.id.lastname_label)


        fun setUserData(userEntity: UserEntity){
            name.text = userEntity.name
            lastname.text = userEntity.lasName
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_list_item, viewGroup, false)

        view.setOnCreateContextMenuListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user= userList[position]
        holder.setUserData(user)
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            setPosition(position)
            false
        })
    }

    override fun getItemCount() = userList.size


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(null)
        menu.add(0, view!!.id, 0, "Ver detalles")//groupId, itemId, order, title
        menu.add(0, view.id, 0, "Eliminar")
        menu.add(0, view.id, 0, "Editar")
    }
}


