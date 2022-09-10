package com.example.facebook_messengerrv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.facebook_messengerrv.Amigo
import com.example.facebook_messengerrv.R

class AmigoAdapter(private val amigosList:List<Amigo>) : RecyclerView.Adapter<AmigoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmigoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AmigoViewHolder(layoutInflater.inflate(R.layout.item_amigo,parent,false))
    }

    override fun onBindViewHolder(holder: AmigoViewHolder, position: Int) {
        val item = amigosList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = amigosList.size
}