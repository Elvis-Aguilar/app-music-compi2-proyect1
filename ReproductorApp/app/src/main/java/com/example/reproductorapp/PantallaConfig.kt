package com.example.reproductorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.reproductorapp.objects.ErrorSingleton

class PantallaConfig : AppCompatActivity() {
    lateinit var texArea: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_config)
    }

    fun cambiarIp(view: View){
        texArea = findViewById(R.id.extconfig)
        ErrorSingleton.getInstance().ipConfig = texArea.text.toString()
        if(ErrorSingleton.getInstance().ipConfig   == ""){
            Toast.makeText(this,"ingrese una ip", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Configuracion realizada con exito", Toast.LENGTH_LONG).show()

        }
    }
}