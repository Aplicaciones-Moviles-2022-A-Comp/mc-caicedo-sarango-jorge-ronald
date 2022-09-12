package com.example.app_farmacia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class UsuarioView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_view)

        init()
        val textViewHome = findViewById<TextView>(R.id.tv_submn_promo)
        val textViewContacto = findViewById<TextView>(R.id.tv_submn_contacto)
        textViewHome.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
        textViewContacto.setOnClickListener {
            irActividad(ContactoView::class.java)
        }
        //SetUp
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setUp(email.toString(),provider.toString())

    }
    private fun setUp(email:String,provider:String){
        title = "Inicio"
        val txtEmail = findViewById<TextView>(R.id.tv_email_usr)
        val usrEmail = findViewById<TextView>(R.id.tv_nombreUsr)
        val txtProvider = findViewById<TextView>(R.id.tv_provider_usr)
        val btnCerrarSesion = findViewById<Button>(R.id.btn_cerrarS)
        txtEmail.text=email
        usrEmail.text=email
        txtProvider.text=provider
        btnCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
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
        val menuNav = findViewById<NavigationView>(R.id.nav_usuario)
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
                    //irActividad(Usuarioiew::class.java)
                    true
                }
                R.id.nav_item_five ->{
                    irActividad(CarritoView::class.java)
                    //var asd = findViewById<DrawerLayout>(R.id.dl_home)
                    //asd.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }
}