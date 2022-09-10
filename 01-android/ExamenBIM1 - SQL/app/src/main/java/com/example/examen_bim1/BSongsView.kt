package com.example.examen_bim1

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class BSongsView : AppCompatActivity() {
    var idItemSeleccion = 0
    var idCancionGlobal = 0
    var idBandaGlobal = 0

    var arregloC = arrayListOf<BCancion>()
    //var arregloCanciones = ArrayList<BCancion>()


    val contentIntentExpl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if (result.data != null){
                val data = result.data
                Log.i("intent-epn", "${data?.getStringExtra("Se abre interfaz Canciones")}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bsongs_view)

        //val idBanda = intent.getIntExtra("idGlobalBand",0)
        val idBanda = intent.getIntExtra("idSeleccion",0)
        val banda = CBaseDeDatos.Tablas!!.consultarBanda(idBanda)
        //val banda = intent.getStringExtra("nombreBand")
        idBandaGlobal = idBanda
        var arregloCanciones = CBaseDeDatos.Tablas!!.seleccionarTodasC(idBanda)
        arregloC=arregloCanciones


        val titulo = findViewById<TextView>(R.id.tv_nombre_banda)
        titulo.text = banda.nombre.toString()

        val listaCancion = findViewById<ListView>(R.id.lv_song_view)
        val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloC)

        listaCancion.adapter = adaptadorC
        adaptadorC.notifyDataSetChanged()

        val botonAnadirC = findViewById<Button>(R.id.btn_anadir_song)
        botonAnadirC.setOnClickListener {
            anadirCancion()
        }
        registerForContextMenu(listaCancion)

        val botonSalir = findViewById<Button>(R.id.btn_salir_vw_canciones)
        botonSalir.setOnClickListener {
            irActividad(BBandsView::class.java)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu2,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccion = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_cancion ->{

                val listaCancion = findViewById<ListView>(R.id.lv_song_view)
                val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloC)

                abrirActividadConParametros(BEditSong::class.java)

                listaCancion.adapter = adaptadorC
                adaptadorC.notifyDataSetChanged()
                return true
            }
            R.id.mi_eliminar_cancion ->{
                abrirDialogoEliminar()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun actualizarTest(){
        val listaCancion = findViewById<ListView>(R.id.lv_song_view)
        arregloC = CBaseDeDatos.Tablas!!.seleccionarTodasC(idBandaGlobal)
        val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloC)

        listaCancion.adapter = adaptadorC
        adaptadorC.notifyDataSetChanged()
    }

    fun abrirDialogoEliminar(){
        //val listaCancion = findViewById<ListView>(R.id.lv_song_view)
        //val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloC)
        val idCancion = arregloC[idItemSeleccion].idCancion

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar la banda seleccionada?")
        builder.setPositiveButton(
            "Aceptar", DialogInterface.OnClickListener { dialog, which ->
                CBaseDeDatos.Tablas!!.eliminarCancion(idCancion)
                actualizarTest()
            }
        )
        builder.setNegativeButton("Cancelar",null)
        val dialogoConfirmacion = builder.create()
        dialogoConfirmacion.show()
    }

    fun anadirCancion() {
        val listaCancion = findViewById<ListView>(R.id.lv_song_view)
        val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloC)

        val builder2 = AlertDialog.Builder(this)
        val inflater2 = layoutInflater


        val dialogLayout2 = inflater2.inflate(R.layout.campos_form_cancion, null)
        //val idCancion = dialogLayout2.findViewById<EditText>(R.id.input_idCancion)
        val nombreC = dialogLayout2.findViewById<EditText>(R.id.input_nombreC)
        val duracionC = dialogLayout2.findViewById<EditText>(R.id.input_duracionC)
        val fechaC = dialogLayout2.findViewById<EditText>(R.id.input_fechaC)
        val isSingleC = dialogLayout2.findViewById<Switch>(R.id.sw_isSingle)
        val numPistaC = dialogLayout2.findViewById<EditText>(R.id.input_numeroPC)
        val gananciaC = dialogLayout2.findViewById<EditText>(R.id.input_gananciaC)


        with(builder2) {
            setTitle("Añadir Canción")
            setPositiveButton("Aceptar") { dialog, which ->
                //Por positivo
                CBaseDeDatos.Tablas!!.insertarCancion(
                    nombreC.text.toString(),
                    duracionC.text.toString(),
                    fechaC.text.toString(),
                    isSingleC.isChecked,
                    numPistaC.text.toString().toInt(),
                    gananciaC.text.toString().toDouble(),
                    idBandaGlobal
                )
                actualizarTest()
            }
            setNegativeButton("Cancelar") { dialog, which ->

            }
            setView(dialogLayout2)
            show()
        }
    }

    override fun onResume() {
        super.onResume()
        actualizarTest()
    }

    fun abrirActividadConParametros(
        clase: Class<*>
        //cancion: BCancion?
    ){
        val intentExplicito = Intent(this, clase)
        //Enviar parametros(solamente variables primitivas)
        intentExplicito.putExtra("idSeleccion",idBandaGlobal)
        intentExplicito.putExtra("idSong",arregloC[idItemSeleccion].idCancion)
        //intentExplicito.putExtra("idGlobalBand",arregloCanciones[idItemSeleccion].idBanda)

        contentIntentExpl.launch(intentExplicito)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}