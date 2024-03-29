package com.example.examen_bim1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instancia de la Base de datos
        CBaseDeDatos.Tablas = CSQLiteHelper(this)


        val botonBandas = findViewById<Button>(R.id.btn_bandas)
        botonBandas.setOnClickListener {
            irActividad(BBandsView::class.java)
        }

    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}