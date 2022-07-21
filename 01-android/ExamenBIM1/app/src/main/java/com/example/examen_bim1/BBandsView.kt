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
    var arregloB:ArrayList<BBanda> = BBaseDeDatosMemoria.arregloBBanda
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
                val bandaSelected:BBanda = arregloB[idItemSeleccionado]
                abrirActividadConParametros(BSongsView::class.java,bandaSelected)
                return true
            }
            R.id.mi_editar_banda ->{
                val listaBanda = findViewById<ListView>(R.id.lv_band_view)
                val adaptadorB = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloB)
                val bandaSelected:BBanda = arregloB[idItemSeleccionado]

                abrirActividadConParametros(BEditBand::class.java,bandaSelected)

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
        val listaBanda = findViewById<ListView>(R.id.lv_band_view)
        val adaptadorB = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloB)

        listaBanda.adapter = adaptadorB
        adaptadorB.notifyDataSetChanged()
    }

    fun abrirDialogoEliminar(){
        val listaBanda = findViewById<ListView>(R.id.lv_band_view)
        val adaptadorB = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloB)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar la banda seleccionada?")
        builder.setPositiveButton(
            "Aceptar",DialogInterface.OnClickListener { dialog, which -> arregloB.removeAt(idItemSeleccionado)
                listaBanda.adapter = adaptadorB
                adaptadorB.notifyDataSetChanged()}
        )
        builder.setNegativeButton("Cancelar",null)
        val dialogoConfirmacion = builder.create()
        dialogoConfirmacion.show()
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        banda: BBanda
    ){
        val intentExplicito = Intent(this, clase)
        //Enviar parametros(solamente variables primitivas)
        intentExplicito.putExtra("nombreBand",banda.nombre)
        intentExplicito.putExtra("idSeleccion",idItemSeleccionado)
        intentExplicito.putExtra("idGlobalBand",banda.idBanda)
        intentExplicito.putExtra("banda",banda)

        contenidoIntentExplicito.launch(intentExplicito)
    }

    fun anadirBanda() {
        val listaBanda = findViewById<ListView>(R.id.lv_band_view)
        val adaptadorB = ArrayAdapter(this,android.R.layout.simple_list_item_1,arregloB)

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val dialogLayout = inflater.inflate(R.layout.campos_form_banda, null)
        val idBanda = dialogLayout.findViewById<EditText>(R.id.input_idBanda)
        val nombreB = dialogLayout.findViewById<EditText>(R.id.input_nombreB)
        val fechaCB = dialogLayout.findViewById<EditText>(R.id.input_fechaConfB)
        val lugarCB = dialogLayout.findViewById<EditText>(R.id.input_lugarOriB)
        val isActiveB = dialogLayout.findViewById<Switch>(R.id.sw_isActiveB)
        val numeroIntB = dialogLayout.findViewById<EditText>(R.id.input_numeroIntB)

        with(builder) {
            setTitle("Añadir Banda")
            setPositiveButton("Aceptar") { dialog, which ->
                //Por positivo
                arregloB.add(
                    BBanda(
                        idBanda.text.toString().toInt(),
                        nombreB.text.toString(),
                        fechaCB.text.toString(),
                        lugarCB.text.toString(),
                        isActiveB.isChecked,
                        numeroIntB.text.toString().toInt()
                    )
                )
                listaBanda.adapter = adaptadorB
                adaptadorB.notifyDataSetChanged()
            }
            setNegativeButton("Cancelar") { dialog, which ->
                //Por negativo
            }
            setView(dialogLayout)
            show()
        }
    }

}