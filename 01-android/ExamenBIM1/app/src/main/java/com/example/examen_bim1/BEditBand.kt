package com.example.examen_bim1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class BEditBand : AppCompatActivity() {
    var arregloB:ArrayList<BBanda> = BBaseDeDatosMemoria.arregloBBanda

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bedit_band)

        val bandaSeleccionada = intent.getParcelableExtra<BBanda>("banda")
        val idItemSeleccion = intent.getIntExtra("idSeleccion",0)

        val titulo = findViewById<TextView>(R.id.tv_editarBanda)
        val nombreB = findViewById<EditText>(R.id.input_edit_nombrB)
        val fechaCB = findViewById<EditText>(R.id.input_edit_fechaCB)
        val lugarCB = findViewById<EditText>(R.id.input_edit_lugarOB)
        val isActiveB = findViewById<Switch>(R.id.sw_edit_isActiveB)
        val numeroIntB = findViewById<EditText>(R.id.input_edit_numeroIB)

        titulo.text = "Editar Banda"
        nombreB.setText(bandaSeleccionada!!.nombre)
        fechaCB.setText(bandaSeleccionada.fechaCreacion)
        lugarCB.setText(bandaSeleccionada.lugarOrigen)
        isActiveB.isChecked=bandaSeleccionada.isActive
        numeroIntB.setText(bandaSeleccionada.numIntegrantes!!.toString())

        val botonAceptar = findViewById<Button>(R.id.btn_aceptar_editB)
        val botonCancelar = findViewById<Button>(R.id.btn_cancel_editB)

        botonAceptar.setOnClickListener {
            arregloB.set(idItemSeleccion,BBanda(
                bandaSeleccionada.idBanda.toString().toInt(),
                nombreB.text.toString(),
                fechaCB.text.toString(),
                lugarCB.text.toString(),
                isActiveB.isChecked,
                numeroIntB.text.toString().toInt())
            )
            irActividad(BBandsView::class.java)
        }

        botonCancelar.setOnClickListener {
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