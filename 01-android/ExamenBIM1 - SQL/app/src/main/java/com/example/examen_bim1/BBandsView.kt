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
import java.util.zip.Inflater

class BBandsView : AppCompatActivity() {

    var idItemSeleccionado = 0

    var arregloB:ArrayList<BBanda> = CBaseDeDatos.Tablas!!.seleccionarTodasB()

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

        val botonAnadirB = findViewById<Button>(R.id.btn_anadir_band_view)
        botonAnadirB.setOnClickListener {
            anadirBanda()
        }

        registerForContextMenu(listaBanda)
        adaptadorB.notifyDataSetChanged()
    }
    fun actualizarTest(){
        val listaBanda = findViewById<ListView>(R.id.lv_band_view)
        arregloB=CBaseDeDatos.Tablas!!.seleccionarTodasB()
        val adaptadorB = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloB)
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
                CBaseDeDatos.Tablas!!.insertarBanda(
                    nombreB.text.toString(),
                    fechaCB.text.toString(),
                    lugarCB.text.toString(),
                    isActiveB.isChecked,
                    numeroIntB.text.toString().toInt()
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
                CBaseDeDatos.Tablas!!.eliminarBanda(idBanda!!)
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
        intentExplicito.putExtra("idSeleccion",idBanda)

        contenidoIntentExplicito.launch(intentExplicito)
    }
}