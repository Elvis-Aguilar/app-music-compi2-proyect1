package com.example.reproductorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.reproductorapp.objects.ErrorSingleton
import com.example.reproductorapp.objects.Errores

class PantallaErrores : AppCompatActivity() {
    var errores : ArrayList<Errores> = arrayListOf()
    var textView: TextView?=null
    var tabla: TableLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_errores)
        errores = ErrorSingleton.getInstance().errores
        textView =findViewById(R.id.textRow) as TextView
        tabla=findViewById(R.id.errrorTeble) as TableLayout;
        textView!!.setText("");

        errores.forEach(){
            var lexema = TextView(this);
            var linea = TextView(this);
            var columna = TextView(this);
            var tipo = TextView(this);
            var descripcion = TextView(this);
            var row= TableRow(this);
            lexema.setText(it.token.lexeme+" ")
            linea.setText("${it.token.line}")
            columna.setText("${it.token.column}")
            tipo.setText("${it.tipo.toString()}")
            descripcion.setText(it.descripcion)
            row.addView(lexema)
            row.addView(linea);
            row.addView(columna);
            row.addView(tipo);
            row.addView(descripcion);
            tabla!!.addView(row);

        }



    }


}