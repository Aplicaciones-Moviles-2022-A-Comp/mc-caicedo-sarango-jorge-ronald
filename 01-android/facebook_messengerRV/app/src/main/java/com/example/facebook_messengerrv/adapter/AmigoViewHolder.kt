package com.example.facebook_messengerrv.adapter

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.facebook_messengerrv.Amigo
import com.example.facebook_messengerrv.R
import de.hdodenhof.circleimageview.CircleImageView

class AmigoViewHolder(view:View) :RecyclerView.ViewHolder(view) {

    val nombre = view.findViewById<TextView>(R.id.tv_nombre)
    val ultimoMsj = view.findViewById<TextView>(R.id.tv_ultmsj)
    val fecha = view.findViewById<TextView>(R.id.tv_fecha)
    val imgAmigo = view.findViewById<CircleImageView>(R.id.iv_perfilAmigo)
    val imgVisto = view.findViewById<CircleImageView>(R.id.iv_visto)
    val online = view.findViewById<CircleImageView>(R.id.iv_img_perfil)

    fun render(amigoModel:Amigo){
        nombre.text=amigoModel.nombre
        ultimoMsj.text=amigoModel.ultimoMsj
        fecha.text=amigoModel.fechaUM
        Glide.with(imgAmigo.context).load(amigoModel.foto).into(imgAmigo)
        if (amigoModel.visto){
            imgVisto.isVisible == amigoModel.visto
            Glide.with(imgVisto.context).load(amigoModel.foto).into(imgVisto)
            Glide.with(online.context).load("https://www.meme-arsenal.com/memes/9b27ef9f8dd9088ce3c2c06ee95558fb.jpg").into(online)
        }else{
            !imgVisto.isVisible
            !online.isVisible
        }
    }
}