package com.example.movcompjrcs2022a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ActividadAcumular : AppCompatActivity() {
    var acumulador=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_acumular)
        val botonAcumular = findViewById<Button>(R.id.btn_acumulador)
        var textoNumero = findViewById<TextView>(R.id.tv_numero)
        botonAcumular.setOnClickListener {
            acumulador++
            textoNumero.text = acumulador.toString()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            //Guardar las variables
            //Primitivos
            putString("textoGuardado", acumulador.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val textoRecuperado:String?=savedInstanceState.getString("textoGuardado")
        if(textoRecuperado!=null){
            acumulador=textoRecuperado.toInt()
        }
    }

    override fun onResume() {
        super.onResume()
        var txtNumber = findViewById<TextView>(R.id.tv_numero)
        txtNumber.setText(acumulador.toString())
    }


}