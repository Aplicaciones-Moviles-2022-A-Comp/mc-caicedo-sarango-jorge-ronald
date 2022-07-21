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

    var arregloC:ArrayList<BCancion> = BBaseDeDatosMemoria.arregloCancion
    var arregloCanciones = ArrayList<BCancion>()


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

        val idBanda = intent.getIntExtra("idGlobalBand",0)
        val banda = intent.getStringExtra("nombreBand")
        idBandaGlobal = idBanda


        arregloC.forEach { bCancion -> if(bCancion.idBanda == idBanda){
            arregloCanciones.add(bCancion)
        } }

        val titulo = findViewById<TextView>(R.id.tv_nombre_banda)
        titulo.text = banda.toString()

        val listaCancion = findViewById<ListView>(R.id.lv_song_view)
        val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloCanciones)

        listaCancion.adapter = adaptadorC
        adaptadorC.notifyDataSetChanged()

        val botonAnadirC = findViewById<Button>(R.id.btn_anadir_song)
        botonAnadirC.setOnClickListener {
            anadirCancion()
        }
        registerForContextMenu(listaCancion)
        //adaptadorC.notifyDataSetChanged()

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
                val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloCanciones)

                val cancionSelected:BCancion = arregloCanciones[idItemSeleccion]
                abrirActividadConParametros(BEditSong::class.java,cancionSelected)

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


    fun abrirDialogoEliminar(){
        val listaCancion = findViewById<ListView>(R.id.lv_song_view)
        val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloCanciones)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar la banda seleccionada?")
        builder.setPositiveButton(
            "Aceptar", DialogInterface.OnClickListener { dialog, which ->
                arregloCanciones.removeAt(idItemSeleccion)
                arregloC.removeAt(idCancionGlobal)

                listaCancion.adapter = adaptadorC
                adaptadorC.notifyDataSetChanged()
            }
        )
        builder.setNegativeButton("Cancelar",null)
        val dialogoConfirmacion = builder.create()
        dialogoConfirmacion.show()
    }

    fun anadirCancion() {
        val listaCancion = findViewById<ListView>(R.id.lv_song_view)
        val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloCanciones)

        val builder2 = AlertDialog.Builder(this)
        val inflater2 = layoutInflater


        val dialogLayout2 = inflater2.inflate(R.layout.campos_form_cancion, null)
        val idCancion = dialogLayout2.findViewById<EditText>(R.id.input_idCancion)
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
                arregloC.add(
                    BCancion(
                        idCancion.text.toString().toInt(),
                        nombreC.text.toString(),
                        duracionC.text.toString(),
                        fechaC.text.toString(),
                        isSingleC.isChecked,
                        numPistaC.text.toString().toInt(),
                        gananciaC.text.toString().toDouble(),
                        arregloCanciones[0].idBanda.toString().toInt()
                    )
                )
                arregloCanciones.add(
                    BCancion(
                        idCancion.text.toString().toInt(),
                        nombreC.text.toString(),
                        duracionC.text.toString(),
                        fechaC.text.toString(),
                        isSingleC.isChecked,
                        numPistaC.text.toString().toInt(),
                        gananciaC.text.toString().toDouble(),
                        arregloCanciones[0].idBanda.toString().toInt()
                    )
                )
                listaCancion.adapter = adaptadorC
                adaptadorC.notifyDataSetChanged()
            }
            setNegativeButton("Cancelar") { dialog, which ->
                abrirActividadConParametros(BBandsView::class.java,null)
            }
            setView(dialogLayout2)
            show()
        }
    }

    override fun onResume() {
        super.onResume()
        val listaCancion = findViewById<ListView>(R.id.lv_song_view)
        val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloCanciones)

        listaCancion.adapter = adaptadorC
        adaptadorC.notifyDataSetChanged()
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        cancion: BCancion?
    ){
        val intentExplicito = Intent(this, clase)
        //Enviar parametros(solamente variables primitivas)
        intentExplicito.putExtra("cancion",cancion)
        intentExplicito.putExtra("idGlobalSong",arregloCanciones[idItemSeleccion].idCancion)
        intentExplicito.putExtra("idGlobalBand",arregloCanciones[idItemSeleccion].idBanda)

        contentIntentExpl.launch(intentExplicito)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}