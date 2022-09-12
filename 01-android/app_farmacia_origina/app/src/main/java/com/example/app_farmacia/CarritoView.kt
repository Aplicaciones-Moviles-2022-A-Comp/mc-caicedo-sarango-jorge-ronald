package com.example.app_farmacia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.navigation.NavigationView

class CarritoView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito_view)
        init()
        val textViewHome = findViewById<TextView>(R.id.tv_submn_promo)
        val textViewContacto = findViewById<TextView>(R.id.tv_submn_contacto)
        textViewHome.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
        textViewContacto.setOnClickListener {
            irActividad(ContactoView::class.java)
        }
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    //Métodos sobreescritos del menú lateral
    fun init(){
        val menuNav = findViewById<NavigationView>(R.id.nav_carrito)
        menuNav.setNavigationItemSelectedListener{ item->
            when(item.itemId){
                R.id.nav_item_one -> {
                    irActividad(MainActivity::class.java)
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
                    //irActividad(CarritoView::class.java)
                    //var asd = findViewById<DrawerLayout>(R.id.dl_home)
                    //asd.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }
}