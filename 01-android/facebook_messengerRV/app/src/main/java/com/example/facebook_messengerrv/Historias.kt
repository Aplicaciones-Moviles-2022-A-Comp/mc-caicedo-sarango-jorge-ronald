package com.example.facebook_messengerrv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.facebook_messengerrv.adapter.HistoriaAdapter
import de.hdodenhof.circleimageview.CircleImageView

class Historias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historias)

        val botonChats = findViewById<ImageView>(R.id.btn_chats)
        botonChats.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        val imgPerfilUsr = findViewById<CircleImageView>(R.id.iv_perfilAmigo)
        Glide.with(imgPerfilUsr).load(AmigoProvider.user.foto).into(imgPerfilUsr)
        initRecyclerView()

    }

    fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_histories)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.adapter=HistoriaAdapter(AmigoProvider.listaAmigos)
    }
    fun irActividad(clase:Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}