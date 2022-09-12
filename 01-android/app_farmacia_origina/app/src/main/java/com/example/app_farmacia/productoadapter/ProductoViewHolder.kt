package com.example.app_farmacia.productoadapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_farmacia.Producto
import com.example.app_farmacia.R

class ProductoViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val producto = view.findViewById<TextView>(R.id.tv_nombreExt_prod)


    fun render(productoModel: Producto){
        producto.text = productoModel.nombreExt
    }
}