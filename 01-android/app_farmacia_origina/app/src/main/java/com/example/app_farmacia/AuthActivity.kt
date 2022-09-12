package com.example.app_farmacia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //SetUp
        setUp()

    }
    private fun setUp(){
        title = "Autenticación"
        val btn_registrar = findViewById<Button>(R.id.btn_registrar)
        val btn_login = findViewById<Button>(R.id.btn_login)
        val txtEmail = findViewById<EditText>(R.id.et_email)
        val txtPwd = findViewById<EditText>(R.id.et_pwd)
        btn_registrar.setOnClickListener {
            if(txtEmail.text.isNotEmpty() && txtPwd.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtEmail.text.toString(),txtPwd.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }
        }
        btn_login.setOnClickListener {
            if(txtEmail.text.isNotEmpty() && txtPwd.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(txtEmail.text.toString(),txtPwd.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al autenticar al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }

    private fun showHome(email:String, provider:ProviderType){
        val homeintent = Intent(this,UsuarioView::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeintent)
    }

}