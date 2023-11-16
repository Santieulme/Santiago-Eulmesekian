package com.example.tpfinaldap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AutoAdapter(

    autolist: ArrayList<Auto>,
    private val onItemClick: (Int) -> Unit,
    private val onDeleteClick : (Int) -> Unit,
    private val onEditClick : (Int) -> Unit

): RecyclerView.Adapter<AutoAdapter.autoViewHolder>(){

    private var autolist: ArrayList<Auto>

    init {

        this.autolist =autolist

    }

    class autoViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val modelo= view.findViewById<TextView>(R.id.modelo)
        val marca= view.findViewById<TextView>(R.id.marca)
        val foto = view.findViewById<ImageView>(R.id.foto)
        val editar = view.findViewById<Button>(R.id.botonEditar)
        val eliminar = view.findViewById<Button>(R.id.botonEliminar)


        fun render(autoModel: Auto){

            modelo.text = autoModel.modelo
            marca.text = autoModel.marca

            Glide.with(foto.context).load(autoModel.foto).into(foto)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): autoViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return autoViewHolder(layoutInflater.inflate(R.layout.card_auto, parent, false))

    }

    override fun onBindViewHolder(holder: autoViewHolder, position: Int) {

        val item = autolist[position]

        holder.render(item)

        holder.foto.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick(holder.adapterPosition)
            }
        }

        holder.editar.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onEditClick(holder.adapterPosition)
            }
        }

        holder.eliminar.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onDeleteClick(holder.adapterPosition)
            }
        }

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick(holder.adapterPosition)
            }
        }



    }

    override fun getItemCount(): Int = autolist.size

}