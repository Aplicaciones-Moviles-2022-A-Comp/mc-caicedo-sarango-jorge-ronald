package com.example.examen_bim1

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.opengl.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BSongsView : AppCompatActivity() {
    var idItemSeleccion = 0
    var idCancionGlobal = 0
    var idBandaGlobal:Long = 0

    val db = Firebase.firestore
    val bandas = db.collection("bands")


    var arregloC = arrayListOf<BCancion>()
    var bandaSeleccionada:BBanda? = null


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

        val idBanda = intent.getLongExtra("idSeleccion",0)
        val banda = intent.getParcelableExtra<BBanda>("bandaSelected")
        idBandaGlobal = idBanda
        bandaSeleccionada = banda



        val titulo = findViewById<TextView>(R.id.tv_nombre_banda)
        titulo.text = bandaSeleccionada?.nombre.toString()


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
                abrirActividadConParametros(BEditSong::class.java)
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

        val adaptadorC = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloC)
        seleccionarTodasC(adaptadorC)

        listaCancion.adapter = adaptadorC
        adaptadorC.notifyDataSetChanged()
    }

    fun abrirDialogoEliminar(){
        val idCancion = arregloC[idItemSeleccion].idCancion

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar la banda seleccionada?")
        builder.setPositiveButton(
            "Aceptar", DialogInterface.OnClickListener { dialog, which ->
                actualizarTest()
                eliminarCancion(idCancion)
            }
        )
        builder.setNegativeButton("Cancelar",null)
        val dialogoConfirmacion = builder.create()
        dialogoConfirmacion.show()
    }

    fun anadirCancion() {

        val builder2 = AlertDialog.Builder(this)
        val inflater2 = layoutInflater


        val dialogLayout2 = inflater2.inflate(R.layout.campos_form_cancion, null)
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
                insertarCancion(
                    (arregloC.size+1).toLong(),
                    nombreC.text.toString(),
                    duracionC.text.toString(),
                    fechaC.text.toString(),
                    isSingleC.isChecked,
                    numPistaC.text.toString().toLong(),
                    gananciaC.text.toString().toDouble(),
                    idBandaGlobal.toLong()
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
    ){
        val intentExplicito = Intent(this, clase)
        //Enviar parametros(solamente variables primitivas)
        intentExplicito.putExtra("idSeleccion",idBandaGlobal)
        intentExplicito.putExtra("idSong",arregloC[idItemSeleccion].idCancion)
        intentExplicito.putExtra("cancionSelected",arregloC[idItemSeleccion])
        intentExplicito.putExtra("bandaSelected",bandaSeleccionada)

        contentIntentExpl.launch(intentExplicito)
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    //Funciones Firestore

    fun seleccionarTodasC(adaptador:ArrayAdapter<BCancion>){
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        bandas
            .document(idBandaGlobal.toString())
            .collection("songs")
            .get()
            .addOnSuccessListener {
                for (cancion in it){
                    anadirAArregloCanciones(arregloC,cancion,idBandaGlobal,adaptador)
                }
            }
    }

    fun insertarCancion(
        idCancion:Long,
        nombreC:String,
        duracion:String,
        fechaLanzamieto:String,
        isSingle:Boolean,
        numPista:Long,
        gananciasSencillo:Double,
        idBanda:Long
    ){
        val nuevaCancion= hashMapOf(
            "idCancion" to idCancion,
            "nombreC" to nombreC,
            "duracion" to duracion,
            "fechaLanzamieto" to fechaLanzamieto,
            "isSingle" to isSingle,
            "numPista" to numPista,
            "gananciasSencillo" to gananciasSencillo,
            "idBanda" to idBanda
        )
        bandas.document((idBandaGlobal).toString()).collection("songs").document((arregloC.size+1).toString()).set(nuevaCancion)
    }

    fun anadirAArregloCanciones(
        arregloNuevo:ArrayList<BCancion>,
        cancion: QueryDocumentSnapshot,
        idBanda:Long,
        adaptador: ArrayAdapter<BCancion>
    ){
        val nuevaCancion = BCancion(
            cancion.id.toLong(),
            cancion.data["nombreC"] as String,
            cancion.data["duracion"] as String,
            cancion.data["fechaLanzamieto"] as String,
            cancion.data["isSingle"] as Boolean,
            cancion.data["numPista"] as Long,
            cancion.data["gananciasSencillo"] as Double,
            idBanda
        )
        arregloNuevo.add(nuevaCancion)
        adaptador.notifyDataSetChanged()
    }

    fun limpiarArreglo(){
        arregloC.clear()
    }

    fun eliminarCancion(idCancion: Long){
        val refereciaCancion = bandas.document(idBandaGlobal.toString()).collection("songs").document(idCancion.toString())

        refereciaCancion.delete()
    }
}