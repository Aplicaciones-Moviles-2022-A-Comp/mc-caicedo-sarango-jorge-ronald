package com.example.app_farmacia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.app_farmacia.productoadapter.ProductoProvider

class SingleProductView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_product_view)
        val idItemSelected = intent.getIntExtra("idItemSeleccionado",0)
        val arregloProductos = ProductoProvider.productoList
        val productoSelected=arregloProductos[idItemSelected]

        val tvNombreSht = findViewById<TextView>(R.id.tv_nombre_sht)
        val tvNombreExt = findViewById<TextView>(R.id.tv_nombre_ext)
        val tvPrecio = findViewById<TextView>(R.id.tv_precio_prod)
        val tvDescripcion = findViewById<TextView>(R.id.tv_descripcion_prod)

        tvNombreSht.text = productoSelected.nombreShort
        tvNombreExt.text = productoSelected.nombreExt
        tvPrecio.text = productoSelected.precioUnit.toString()
        tvDescripcion.text = productoSelected.descriptcion
    }
}