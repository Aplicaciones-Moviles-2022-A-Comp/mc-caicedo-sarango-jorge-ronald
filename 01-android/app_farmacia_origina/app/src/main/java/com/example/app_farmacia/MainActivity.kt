package com.example.app_farmacia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_farmacia.productoadapter.ProductoAdapter
import com.example.app_farmacia.productoadapter.ProductoProvider
import com.example.app_farmacia.promoadapter.PromoAdapter
import com.example.app_farmacia.promoadapter.PromoProvider
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        val textViewContacto = findViewById<TextView>(R.id.tv_submn_contacto)
        textViewContacto.setOnClickListener {
            irActividad(ContactoView::class.java)
        }
        initRecyclerView()
        //Login
        val btnIniciarSesion=findViewById<ImageView>(R.id.iv_iniciarSesion)
        btnIniciarSesion.setOnClickListener {
            irActividad(AuthActivity::class.java)
        }


    }
    fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_promos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PromoAdapter(PromoProvider.promoList)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
    //Métodos sobreescritos del menú lateral
    fun init(){
        val menuNav = findViewById<NavigationView>(R.id.nav_inicio)
        menuNav.setNavigationItemSelectedListener{ item->
            when(item.itemId){
                R.id.nav_item_one -> {
                    //irActividad(MainActivity::class.java)
                    true
                }
                R.id.nav_item_two -> {
                    irActividad(ProductosView::class.java)
                    true
                }
                R.id.nav_item_three -> {
                    irActividad(ContactoView::class.java)
                    true
                }
                R.id.nav_item_four -> {
                    //irActividad(Contactanos::class.java)
                    true
                }
                R.id.nav_item_five ->{
                    irActividad(CarritoView::class.java)
                    true
                }
                else -> false
            }
        }
    }
}