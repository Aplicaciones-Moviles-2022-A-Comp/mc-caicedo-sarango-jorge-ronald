package com.example.app_farmacia

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_farmacia.productoadapter.ProductoAdapter
import com.example.app_farmacia.productoadapter.ProductoProvider
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductosView : AppCompatActivity(){
    val db = Firebase.firestore
    val productos = db.collection("productos")
    val productosList:ArrayList<Producto> = arrayListOf()
    var idItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)
        init()
        val textViewHome = findViewById<TextView>(R.id.tv_submn_promo)
        val textViewContacto = findViewById<TextView>(R.id.tv_submn_contacto)
        textViewHome.setOnClickListener {
            irActividad(MainActivity::class.java)
        }
        textViewContacto.setOnClickListener {
            irActividad(ContactoView::class.java)
        }
        crearDatosProductos()
        seleccionarTodasB()

        val recyclerView = findViewById<RecyclerView>(R.id.rv_productos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProductoAdapter(ProductoProvider.productoList)

        registerForContextMenu(recyclerView)

    }
    //Menu contextual
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater =menuInflater
        inflater.inflate(R.menu.menu1,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_ver_producto ->{
                val singlePintent = Intent(this,SingleProductView::class.java).apply {
                    putExtra("idItemSeleccionado",idItemSeleccionado)
                }
                startActivity(singlePintent)
                return true
            }
            else -> super.onContextItemSelected(item)
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
        val menuNav = findViewById<NavigationView>(R.id.nav_productos)
        menuNav.setNavigationItemSelectedListener{ item->
            when(item.itemId){
                R.id.nav_item_one -> {
                    irActividad(MainActivity::class.java)
                    true
                }
                R.id.nav_item_two -> {
                    //irActividad(ProductosView::class.java)
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
                    //var asd = findViewById<DrawerLayout>(R.id.dl_home)
                    //asd.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    //Métodos Firebase
    fun seleccionarTodasB(){
        limpiarArreglo()
        productos
            .get()
            .addOnSuccessListener {
                for (producto in it) {
                    anadirAArregloProductos(productosList,producto)
                }
            }
    }

    fun anadirAArregloProductos(
        arregloNuevo:ArrayList<Producto>,
        producto: QueryDocumentSnapshot
    ){

        val nuevoProducto = Producto(
            producto.data["nombreShort"] as String,
            producto.data["nombreExt"] as String,
            producto.data["imagen"] as String,
            producto.data["precioUnit"] as Double,
            producto.data["descripcion"] as String
        )
        arregloNuevo.add(
            nuevoProducto
        )
    }
    fun limpiarArreglo(){
        productosList.clear()
    }

    fun crearDatosProductos(){
        val p = ProductoProvider.productoList

        p.forEachIndexed { index, producto ->
            val productoN= hashMapOf(
                "nombreShort" to producto.nombreShort,
                "nombreExt" to producto.nombreExt,
                "imagen" to producto.imagen,
                "precioUnit" to producto.precioUnit,
                "descripcion" to producto.descriptcion
            )
            productos.document(index.toString()).set(productoN)
        }
    }

}