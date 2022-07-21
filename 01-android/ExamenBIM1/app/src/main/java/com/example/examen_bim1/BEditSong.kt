package com.example.examen_bim1

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class BEditSong : AppCompatActivity() {

    var arregloC:ArrayList<BCancion> = BBaseDeDatosMemoria.arregloCancion
    var idBandita = 0
    var arregloB:ArrayList<BBanda> = BBaseDeDatosMemoria.arregloBBanda

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

        val cancionSeleccionada = intent.getParcelableExtra<BCancion>("cancion")
        val idCancionGlobal = intent.getIntExtra("idGlobalSong",0)
        val idBandaGlobal = intent.getIntExtra("idGlobalBand",0)
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
            arregloC.forEachIndexed {
                index,bCancion -> if(bCancion.idCancion == idCancionGlobal){arregloC.set(index,
                BCancion(
                    cancionSeleccionada.idCancion.toString().toInt(),
                    nombreC.text.toString(),
                    duracionC.text.toString(),
                    fechaC.text.toString(),
                    swSingleC.isChecked,
                    numeroPC.text.toString().toInt(),
                    gananciasC.text.toString().toDouble(),
                    cancionSeleccionada.idBanda.toString().toInt())
            )}
            }
            abrirActividadConParametros(BSongsView::class.java)
        }
        botonCancelar.setOnClickListener {
            abrirActividadConParametros(BSongsView::class.java)
        }
    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        //Enviar parametros(solamente variables primitivas)
        intentExplicito.putExtra("idGlobalBand",idBandita)
        intentExplicito.putExtra("nombreBand",arregloB[idBandita].nombre)
        //intentExplicito.putExtra("idGlobalSong",idCancionGlobal)

        contentIntentExpli.launch(intentExplicito)
    }
}