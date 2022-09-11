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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.util.zip.Inflater

class BBandsView : AppCompatActivity() {

    var idItemSeleccionado = 0
    var contador=0

    //Vbles firestore
    var arregloB:ArrayList<BBanda> = arrayListOf()
    val db = Firebase.firestore
    val bandas = db.collection("bands")
    var bandaSeleccion = BBanda(0,"","","",true,0)


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
        setContentView(R.layout.activity_bbands_view)

        val listaBanda = findViewById<ListView>(R.id.lv_band_view)
        val adaptadorB = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloB)

        listaBanda.adapter = adaptadorB
        adaptadorB.notifyDataSetChanged()
        if (contador==0){
            //crearDatosBase()
            contador++
        }

        val botonAnadirB = findViewById<Button>(R.id.btn_anadir_band_view)
        botonAnadirB.setOnClickListener {
            anadirBanda()
        }

        registerForContextMenu(listaBanda)
        adaptadorB.notifyDataSetChanged()
    }

    fun actualizarTest(){
        limpiarArreglo()
        val listaBanda = findViewById<ListView>(R.id.lv_band_view)
        val adaptadorB = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloB)
        seleccionarTodasB(adaptadorB)

        listaBanda.adapter = adaptadorB
        adaptadorB.notifyDataSetChanged()
    }

    fun anadirBanda() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val dialogLayout = inflater.inflate(R.layout.campos_form_banda, null)
        val nombreB = dialogLayout.findViewById<EditText>(R.id.input_nombreB)
        val fechaCB = dialogLayout.findViewById<EditText>(R.id.input_fechaConfB)
        val lugarCB = dialogLayout.findViewById<EditText>(R.id.input_lugarOriB)
        val isActiveB = dialogLayout.findViewById<Switch>(R.id.sw_isActiveB)
        val numeroIntB = dialogLayout.findViewById<EditText>(R.id.input_numeroIntB)

        with(builder) {
            setTitle("Añadir Banda")
            setPositiveButton("Aceptar") { dialog, which ->
                //Por positivo
                insertarBanda(
                    nombreB.text.toString(),
                    fechaCB.text.toString(),
                    lugarCB.text.toString(),
                    isActiveB.isChecked,
                    numeroIntB.text.toString().toLong()
                )
                actualizarTest()
            }
            setNegativeButton("Cancelar") { dialog, which ->
                //Por negativo
            }
            setView(dialogLayout)
            show()
        }
    }

    fun abrirDialogoEliminar(){

        val idBanda = arregloB[idItemSeleccionado].idBanda
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar la banda seleccionada?")
        builder.setPositiveButton(
            "Aceptar",DialogInterface.OnClickListener { dialog, which ->
                eliminarBanda(idBanda)
                actualizarTest()
            }
        )
        builder.setNegativeButton("Cancelar",null)
        val dialogoConfirmacion = builder.create()
        dialogoConfirmacion.show()
    }

    //Crear menú contextual
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenamos las opciones del menú
        val inflater = menuInflater
        inflater.inflate(R.menu.menu1, menu)

        //Obtener el id del ArrayList Seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_ver_banda ->{
                abrirActividadConParametros(BSongsView::class.java)
                return true
            }
            R.id.mi_editar_banda ->{
                val listaBanda = findViewById<ListView>(R.id.lv_band_view)
                val adaptadorB = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloB)

                abrirActividadConParametros(BEditBand::class.java)

                listaBanda.adapter = adaptadorB
                adaptadorB.notifyDataSetChanged()
                return true
            }
            R.id.mi_eliminar_banda ->{
                abrirDialogoEliminar()
                return true
            }
            else -> super.onContextItemSelected(item)
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
        val idBanda=arregloB[idItemSeleccionado].idBanda
        bandaSeleccion = arregloB[idItemSeleccionado]
        intentExplicito.putExtra("idSeleccion",idBanda)
        intentExplicito.putExtra("bandaSelected",bandaSeleccion)

        contenidoIntentExplicito.launch(intentExplicito)
    }

    //Funciones Firestore
    fun crearDatosBase(){

        val banda1= hashMapOf(
            "idBanda" to 1,
            "nombre" to "Modern Talking",
            "fechaCreacion" to "17-02-1980",
            "lugarOrigen" to "Alemania",
            "isActive" to false,
            "numIntegrantes" to 2
        )

        val cancion11 = hashMapOf(
            "idCancion" to 1,
            "nombreC" to "Jet Airliner",
            "duracion" to "04:35:13",
            "fechaLanzamieto" to "01-01-1998",
            "isSingle" to true,
            "numPista" to 14,
            "gananciasSencillo" to 14.32,
            "idBanda" to 1
        )
        val cancion12 = hashMapOf(
            "idCancion" to 2,
            "nombreC" to "Witchqueen",
            "duracion" to "03:35:13",
            "fechaLanzamieto" to "01-01-1990",
            "isSingle" to false,
            "numPista" to 10,
            "gananciasSencillo" to 14.32,
            "idBanda" to 1
        )
        bandas.document("1").set(banda1)
        bandas.document("1").collection("songs").document("1").set(cancion11)
        bandas.document("1").collection("songs").document("2").set(cancion12)

        val banda2= hashMapOf(
            "idBanda" to 2,
            "nombre" to "Queen",
            "fechaCreacion" to "01-06-1970",
            "lugarOrigen" to "EEUU",
            "isActive" to false,
            "numIntegrantes" to 5
        )
        val cancion21 = hashMapOf(
            "idCancion" to 3,
            "nombreC" to "Bohemiam Rhapsody",
            "duracion" to "04:35:13",
            "fechaLanzamieto" to "01-01-1998",
            "isSingle" to true,
            "numPista" to 14,
            "gananciasSencillo" to 14.32,
            "idBanda" to 2
        )
        val cancion22 = hashMapOf(
            "idCancion" to 4,
            "nombreC" to "Dont stop me now",
            "duracion" to "04:35:13",
            "fechaLanzamieto" to "01-01-1998",
            "isSingle" to true,
            "numPista" to 14,
            "gananciasSencillo" to 14.32,
            "idBanda" to 2
        )
        bandas.document("2").set(banda2)
        bandas.document("2").collection("songs").document("1").set(cancion21)
        bandas.document("2").collection("songs").document("2").set(cancion22)

        val banda3= hashMapOf(
            "idBanda" to 3,
            "nombre" to "Joy",
            "fechaCreacion" to "03-04-1995",
            "lugarOrigen" to "EEUU",
            "isActive" to true,
            "numIntegrantes" to 4
        )
        val cancion31 = hashMapOf(
            "idCancion" to 5,
            "nombreC" to "JTouch by touch",
            "duracion" to "04:35:13",
            "fechaLanzamieto" to "01-01-1998",
            "isSingle" to true,
            "numPista" to 14,
            "gananciasSencillo" to 14.32,
            "idBanda" to 3
        )
        val cancion32 = hashMapOf(
            "idCancion" to 6,
            "nombreC" to "Hello",
            "duracion" to "03:35:13",
            "fechaLanzamieto" to "01-01-1990",
            "isSingle" to false,
            "numPista" to 10,
            "gananciasSencillo" to 14.32,
            "idBanda" to 3
        )
        bandas.document("3").set(banda3)
        bandas.document("3").collection("songs").document("1").set(cancion31)
        bandas.document("3").collection("songs").document("2").set(cancion32)

        val banda4= hashMapOf(
            "idBanda" to 3,
            "nombre" to "Mago de Oz",
            "fechaCreacion" to "03-04-1999",
            "lugarOrigen" to "Nicaragua",
            "isActive" to false,
            "numIntegrantes" to 6
        )
        val cancion41 = hashMapOf(
            "idCancion" to 7,
            "nombreC" to "Fiesta Pagana",
            "duracion" to "04:35:13",
            "fechaLanzamieto" to "01-01-1998",
            "isSingle" to true,
            "numPista" to 14,
            "gananciasSencillo" to 14.32,
            "idBanda" to 4
        )
        bandas.document("4").set(banda4)
        bandas.document("4").collection("songs").document("1").set(cancion41)
    }

    fun limpiarArreglo(){
        arregloB.clear()
    }

    fun seleccionarTodasB(adaptador:ArrayAdapter<BBanda>){
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        bandas
            .get()
            .addOnSuccessListener {
                for (banda in it) {
                    anadirAArregloBandas(arregloB,banda,adaptador)
                }
            }
    }

    fun anadirAArregloBandas(
        arregloNuevo:ArrayList<BBanda>,
        banda: QueryDocumentSnapshot,
        adaptador: ArrayAdapter<BBanda>
    ){
        val nuevaBanda = BBanda(
            banda.id.toLong(),
            banda.data["nombre"] as String?,
            banda.data["fechaCreacion"] as String?,
            banda.data["lugarOrigen"] as String?,
            banda.data["isActive"] as Boolean,
            banda.data["numIntegrantes"] as Long?
        )
        arregloNuevo.add(
            nuevaBanda
        )
        adaptador.notifyDataSetChanged()
    }

    fun insertarBanda(
        nombre:String,
        fechaCreacion:String,
        lugarOrigen:String,
        isActive:Boolean,
        numIntegrantes:Long
    ){
        val nuevaBanda= hashMapOf(
            "nombre" to nombre,
            "fechaCreacion" to fechaCreacion,
            "lugarOrigen" to lugarOrigen,
            "isActive" to isActive,
            "numIntegrantes" to numIntegrantes,
            "canciones" to listOf<BCancion>()
        )
        bandas.document((arregloB.size+1).toString()).set(nuevaBanda)
    }

    fun eliminarBanda(idBanda:Long?){
        val referenciaBanda = bandas.document(idBanda.toString())

        referenciaBanda.delete()
    }


}