package com.example.reproductorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.reproductorapp.reproductor.HiloReproductorDePista
import com.sistema.musicserver.instrucciones.music.ListaMusical

class PantallaReproduccion : AppCompatActivity() {
    var lista : ListaMusical?=null
    var index : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_reproduccion)
        val bun = intent.extras
        lista = bun?.get("lista") as ListaMusical
//        println(lista?.nombre)
        val items = mutableListOf<String>()
        for (pista in lista?.listasMusicales!!){
            items.add(pista.nombre)
        }
        val autoComplete : AutoCompleteTextView = findViewById(R.id.autoComplete)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
                index = i;
        }
    }


    fun reproducir(view: View){
        if (index != -1){
            //val reproductor = HiloReproductorDePista()
            //reproductor.prepararReproduccion(lista?.listasMusicales!!.get(index).canales)
            //reproductor.start()
            Toast.makeText(this,"Pista en reproduccion :(", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Debes Seleccionar una Pista para Reproducirla", Toast.LENGTH_LONG).show()
        }
    }







}