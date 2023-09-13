package com.example.reproductorapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.reproductorapp.objects.ErrorSingleton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goAreaSolicitudes(view: View) {
        val lanzar = Intent(this, PantallaSolicitudes::class.java)
        startActivity(lanzar)
    }

    fun goErrores(view: View){
        if (ErrorSingleton.getInstance().errores.isEmpty()){
            Toast.makeText(this, "No existen errores en el codigo, los errores estan vacios", Toast.LENGTH_LONG).show()
        }else{
            val lanzar = Intent(this, PantallaErrores::class.java)
            startActivity(lanzar)
        }
    }

    fun goIpConfig(view: View){
        val lanzar = Intent(this, PantallaConfig::class.java)
        startActivity(lanzar)
    }
}