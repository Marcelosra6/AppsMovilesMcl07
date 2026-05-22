package com.mchi.ej4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mchi.ej4.data.Articulo
import com.mchi.ej4.databinding.ItemArticuloBinding
class ArticuloAdapter : ListAdapter<Articulo, ArticuloAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(val binding: ItemArticuloBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticuloBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articulo = getItem(position)
        holder.binding.Codigo.text = articulo.codigo.toString()
        holder.binding.Descripcion.text = articulo.descripcion
        holder.binding.Precio.text = articulo.precio.toString()
    }

    //verificacion que sean diferentes xsiaca
    class DiffCallback : DiffUtil.ItemCallback<Articulo>() {
        override fun areItemsTheSame(a: Articulo, b: Articulo) = a.codigo == b.codigo
        override fun areContentsTheSame(a: Articulo, b: Articulo) = a == b
    }
}