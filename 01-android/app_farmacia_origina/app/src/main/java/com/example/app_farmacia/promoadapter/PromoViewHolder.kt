package com.example.app_farmacia.promoadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_farmacia.Promocion
import com.example.app_farmacia.R

class PromoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val titulo = view.findViewById<TextView>(R.id.tv_tituloP)
    val descripcion = view.findViewById<TextView>(R.id.tv_descripcionP)
    val imagenP = view.findViewById<ImageView>(R.id.iv_imgP)

    fun render(promoModel:Promocion){
        titulo.text = promoModel.titulo
        descripcion.text = promoModel.descripcion
        Glide.with(imagenP.context).load(promoModel.imagenP).into(imagenP)
    }
}