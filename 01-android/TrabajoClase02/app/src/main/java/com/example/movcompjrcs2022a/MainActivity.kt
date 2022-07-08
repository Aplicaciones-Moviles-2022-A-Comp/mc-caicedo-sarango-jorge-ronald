package com.example.movcompjrcs2022a

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val data = result.data
                Log.i("intent-epn", "${data?.getStringExtra("nombreModificado")}")
            }
        }
    }
    val contenidoIntentImplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            if (result.data != null){
                if (result.data!!.data !=null){
                    val uri: Uri = result.data!!.data!!
                    val cursor = contentResolver.query(
                        uri,
                        null,
                        null,
                        null,
                        null
                    )
                    cursor?.moveToFirst()
                    val indiceTelefono = cursor?.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    val telefono = cursor?.getString(
                        indiceTelefono!!
                    )
                    cursor?.close()
                    Log.i("intent-epn", "Telefono ${telefono}")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonAcumular = findViewById<Button>(R.id.btn_shortcut)
        botonAcumular.setOnClickListener {
            irActividad(ActividadAcumular::class.java)
        }
        val botonIrListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonIrListView.setOnClickListener {
            irActividad(BListView::class.java)
        }

        val botonIntent = findViewById<Button>(R.id.btn_intent)
        botonIntent.setOnClickListener {
            abrirActividadConParametros(CIntentExplicitoParametros::class.java)
        }

        val botonBDD = findViewById<Button>(R.id.btn_UsuariosBDD)
        botonBDD.setOnClickListener {
            irActividad(EVistaEntrenador::class.java)
        }

        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            contenidoIntentExplicito.launch(intentConRespuesta)
        }

    }

    fun irActividad( //Intent explícito
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }


    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        //Enviar parametros(solamente variables primitivas)
        intentExplicito.putExtra("nombre", "Jorge")
        intentExplicito.putExtra("apellido", "Caicedo")
        intentExplicito.putExtra("edad",32)
        intentExplicito.putExtra("entrenadorPrincipal",BEntrenador("JORGE", "Calvas"))

        contenidoIntentExplicito.launch(intentExplicito)
        //startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO) //401

    }

}