package com.example.app_farmacia.promoadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_farmacia.Promocion
import com.example.app_farmacia.R
import com.example.app_farmacia.productoadapter.ProductoViewHolder

class PromoAdapter(private val promosList:ArrayList<Promocion>): RecyclerView.Adapter<PromoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PromoViewHolder(layoutInflater.inflate(R.layout.item_promocion,parent, false))
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val item = promosList[position]
        holder.render(item)
    }

    override fun getItemCount():Int = promosList.size
}