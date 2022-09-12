package com.example.app_farmacia.productoadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_farmacia.Producto
import com.example.app_farmacia.R

class ProductoAdapter(private val productosList:ArrayList<Producto>):RecyclerView.Adapter<ProductoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(R.layout.item_producto,parent, false))
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val item = productosList[position]
        holder.render(item)
    }

    override fun getItemCount():Int = productosList.size

}