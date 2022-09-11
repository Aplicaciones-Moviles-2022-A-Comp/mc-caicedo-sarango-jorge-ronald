package com.example.examen_bim1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BEditBand : AppCompatActivity() {
    val db = Firebase.firestore
    val bandas = db.collection("bands")
    var bandaSelected:BBanda? = null

    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val data = result.data
                Log.i("intent-epn", "${data?.getStringExtra("Se abre interfaz Canciones")}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bedit_band)

        val idItemSeleccion = intent.getLongExtra("idSeleccion",0)
        val bandaParam = intent.getParcelableExtra<BBanda>("bandaSelected")


        bandaSelected = bandaParam

        val titulo = findViewById<TextView>(R.id.tv_editarBanda)
        val nombreB = findViewById<EditText>(R.id.input_edit_nombrB)
        val fechaCB = findViewById<EditText>(R.id.input_edit_fechaCB)
        val lugarCB = findViewById<EditText>(R.id.input_edit_lugarOB)
        val isActiveB = findViewById<Switch>(R.id.sw_edit_isActiveB)
        val numeroIntB = findViewById<EditText>(R.id.input_edit_numeroIB)

        titulo.text = "Editar Banda"
        nombreB.setText(bandaSelected?.nombre)
        fechaCB.setText(bandaSelected?.fechaCreacion)
        lugarCB.setText(bandaSelected?.lugarOrigen)
        isActiveB.isChecked=bandaSelected!!.isActive
        numeroIntB.setText(bandaSelected?.numIntegrantes.toString())

        val botonAceptar = findViewById<Button>(R.id.btn_aceptar_editB)
        val botonCancelar = findViewById<Button>(R.id.btn_cancel_editB)

        botonAceptar.setOnClickListener {
            actualizarBanda(
                idItemSeleccion,
                nombreB.text.toString(),
                fechaCB.text.toString(),
                lugarCB.text.toString(),
                isActiveB.isChecked,
                numeroIntB.text.toString().toLong()
            )
            abrirActividadConParametros(BBandsView::class.java)
        }

        botonCancelar.setOnClickListener {
            abrirActividadConParametros(BBandsView::class.java)
        }
    }


    fun actualizarBanda(
        idBanda: Long,
        nombre: String,
        fechaCreacion: String,
        lugarOrigen: String,
        isActive: Boolean,
        numIntegrantes: Long
    ){

        val bandaUpdated= hashMapOf(
            "nombre" to nombre,
            "fechaCreacion" to fechaCreacion,
            "lugarOrigen" to lugarOrigen,
            "isActive" to isActive,
            "numIntegrantes" to numIntegrantes
        )
        bandas.document(idBanda.toString()).set(bandaUpdated).addOnSuccessListener {
            Toast.makeText(this,"${idBanda}",Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Falló la inserción",Toast.LENGTH_LONG).show()
        }
    }
    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        val idBanda= bandaSelected?.idBanda
        val bandaSeleccion = bandaSelected
        intentExplicito.putExtra("idSeleccion",idBanda)
        intentExplicito.putExtra("bandaSelected",bandaSeleccion)

        contenidoIntentExplicito.launch(intentExplicito)
    }

}