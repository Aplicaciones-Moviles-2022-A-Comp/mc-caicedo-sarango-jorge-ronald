package com.example.examen_bim1

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BEditSong : AppCompatActivity() {

    var idBandita:Long = 0
    val db = Firebase.firestore
    val bandas = db.collection("bands")
    var bandaSeleccionada:BBanda? = null

    val contentIntentExpli = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val data = result.data
                Log.i("intent-epn", "${data?.getStringExtra("Se abre interfaz Canciones")}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bedit_song)


        val idCancionGlobal = intent.getLongExtra("idSong",0)
        val idBandaGlobal = intent.getLongExtra("idSeleccion",0)
        val cancionSeleccionada = intent.getParcelableExtra<BCancion>("cancionSelected")
        val banda = intent.getParcelableExtra<BBanda>("bandaSelected")
        bandaSeleccionada = banda

        idBandita = idBandaGlobal


        val tituloC = findViewById<TextView>(R.id.tv_edit_song)
        val nombreC = findViewById<EditText>(R.id.input_editNombreS)
        val duracionC = findViewById<EditText>(R.id.input_editDuracionS)
        val fechaC = findViewById<EditText>(R.id.input_editFechaS)
        val swSingleC = findViewById<Switch>(R.id.sw_edit_isSingle)
        val numeroPC = findViewById<EditText>(R.id.input_editNumPistaS)
        val gananciasC = findViewById<EditText>(R.id.input_editGananciasS)

        tituloC.text = "Editar Canción"
        nombreC.setText(cancionSeleccionada!!.nombreC)
        duracionC.setText(cancionSeleccionada.duracion)
        fechaC.setText(cancionSeleccionada.fechaLanzamieto)
        swSingleC.isChecked=cancionSeleccionada.isSingle
        numeroPC.setText(cancionSeleccionada.numPista.toString())
        gananciasC.setText(cancionSeleccionada.gananciasSencillo.toString())

        val botonAceptar = findViewById<Button>(R.id.btn_aceptar_editS)
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_editS)

        botonAceptar.setOnClickListener {
            actualizarCancion(
                idCancionGlobal,
                nombreC.text.toString(),
                duracionC.text.toString(),
                fechaC.text.toString(),
                swSingleC.isChecked,
                numeroPC.text.toString().toLong(),
                gananciasC.text.toString().toDouble(),
                idBandaGlobal
            )
            abrirActividadConParametros(BSongsView::class.java)
        }
        botonCancelar.setOnClickListener {
            abrirActividadConParametros(BSongsView::class.java)
        }
    }
    fun actualizarCancion(
        idCancion:Long,
        nombreC:String,
        duracion:String,
        fechaLanzamieto:String,
        isSingle:Boolean,
        numPista:Long,
        gananciasSencillo:Double,
        idBanda:Long
    ){

        val cancionUpdated= hashMapOf(
            "idCancion" to idCancion,
            "nombreC" to nombreC,
            "duracion" to duracion,
            "fechaLanzamieto" to fechaLanzamieto,
            "isSingle" to isSingle,
            "numPista" to numPista,
            "gananciasSencillo" to gananciasSencillo,
            "idBanda" to idBanda
        )
        bandas.document(idBanda.toString()).collection("songs").document(idCancion.toString()).set(cancionUpdated)
    }


    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idSeleccion",idBandita)
        intentExplicito.putExtra("bandaSelected",bandaSeleccionada)

        contentIntentExpli.launch(intentExplicito)
    }
}