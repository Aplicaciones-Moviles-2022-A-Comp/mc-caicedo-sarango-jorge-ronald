package com.example.facebook_messengerrv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.facebook_messengerrv.Amigo
import com.example.facebook_messengerrv.AmigoProvider
import com.example.facebook_messengerrv.R

class HistoriaAdapter(private val amigosList:List<Amigo>):RecyclerView.Adapter<HistoriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HistoriaViewHolder(layoutInflater.inflate(R.layout.item_historia,parent,false))
    }

    override fun onBindViewHolder(holder: HistoriaViewHolder, position: Int) {
        val item = amigosList[position]
        val itemInicial = AmigoProvider.user
        if(position==0){
            holder.renderFstItem(itemInicial)
        }else{
            holder.render(item)
        }
    }

    override fun getItemCount(): Int = amigosList.size
}