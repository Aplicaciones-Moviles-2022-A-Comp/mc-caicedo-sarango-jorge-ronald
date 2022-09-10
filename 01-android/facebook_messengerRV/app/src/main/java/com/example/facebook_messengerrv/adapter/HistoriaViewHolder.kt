package com.example.facebook_messengerrv.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.facebook_messengerrv.Amigo
import com.example.facebook_messengerrv.R
import de.hdodenhof.circleimageview.CircleImageView

class HistoriaViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val historia = view.findViewById<ImageView>(R.id.iv_historia)
    val imgPerfil = view.findViewById<CircleImageView>(R.id.iv_img_perfil)
    val nombreUsr = view.findViewById<TextView>(R.id.tv_nombreUsr)


    fun render(amigoModel:Amigo){
        Glide.with(historia.context).load(amigoModel.historia).into(historia)
        Glide.with(imgPerfil.context).load(amigoModel.foto).into(imgPerfil)
        nombreUsr.text=amigoModel.nombre
        if (amigoModel.visto){
            imgPerfil.borderColor=Color.parseColor("#0172f7")
        }else{
            imgPerfil.borderColor=Color.parseColor("#C6CCD2DA")
        }
        imgPerfil.borderWidth=3
    }
    fun renderFstItem(userModel:Amigo){
        val msjPrimerItem = "Agregar a Historia"
        val addHistoria = imgPerfil.resources.getDrawable(R.drawable.ic_add_svgrepo_com)
        Glide.with(historia.context).load(userModel.historia).into(historia)
        Glide.with(imgPerfil.context).load(addHistoria).into(imgPerfil)
        nombreUsr.text=msjPrimerItem
        imgPerfil.borderWidth=0
    }

}