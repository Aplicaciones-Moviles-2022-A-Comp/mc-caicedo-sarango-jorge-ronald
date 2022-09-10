package com.example.facebook_messengerrv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.facebook_messengerrv.adapter.AmigoAdapter
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonHistorias = findViewById<ImageView>(R.id.btn_historias)
        botonHistorias.setOnClickListener {
            irActividad(Historias::class.java)
        }

        val imgPerfilUsr = findViewById<CircleImageView>(R.id.iv_perfilAmigo)
        Glide.with(imgPerfilUsr).load(AmigoProvider.user.foto).into(imgPerfilUsr)
        initRecyclerView()




    }

    fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_listaAmigos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AmigoAdapter(AmigoProvider.listaAmigos)
    }

    fun irActividad(clase:Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}