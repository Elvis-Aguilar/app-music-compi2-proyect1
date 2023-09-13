package com.example.reproductorapp

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.reproductorapp.analizadores.LexerSolicitud
import com.example.reproductorapp.analizadores.ParserSolicitud
import com.example.reproductorapp.objects.ErrorSingleton
import com.example.reproductorapp.objects.OptionConexion
import com.sistema.musicserver.instrucciones.music.ListaMusical
import com.sistema.musicserver.instrucciones.music.Solicitud
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Reader
import java.io.StringReader
import java.net.Socket
import java.util.ArrayList

class PantallaSolicitudes : AppCompatActivity() {
    private var actual: Socket? = null
    private val error = false
    private var results:String = "aun no ejecutado";
    private var tipoSolicitud:OptionConexion? = null
    private var resultadoSolicitud: Solicitud?=null
    var texto: String?= null
    lateinit var texArea: EditText
    lateinit var texResultado: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_solicitudes)
        texResultado = findViewById(R.id.areaTextResult)
    }
    fun goReproducir(view: View){
        val lanzar = Intent(this, PantallaReproduccion::class.java)
        if (resultadoSolicitud?.lista == null){
            Toast.makeText(this,"Debes solicitar una Lista de Reproduccion para reproducir", Toast.LENGTH_LONG).show()
        }else{
            lanzar.putExtra("lista", resultadoSolicitud?.lista as ListaMusical)
            startActivity(lanzar)
        }
    }


    fun enviarPeticion(view: View) {
        val datos = "Hika nnudfasdf"
        texArea = findViewById(R.id.areaText)
        texto = texArea.text.toString()
        val reader: Reader = StringReader(texto)
        val solicitudLexer: LexerSolicitud = LexerSolicitud(reader)
        val parserSolicitud : ParserSolicitud = ParserSolicitud(solicitudLexer)
        if(texto  == ""){
            Toast.makeText(this,"Area de Texto Vacio", Toast.LENGTH_LONG).show()
        }else {
            try {
                ErrorSingleton.getInstance().errores.clear()
                parserSolicitud.parse()
                if (ErrorSingleton.getInstance().errores.isEmpty()){
                    Toast.makeText(this,"Analizado con exito", Toast.LENGTH_LONG).show()
                    tipoSolicitud = parserSolicitud.getTipoSolicitud()
                    resultadoSolicitud = Solicitud(texto, parserSolicitud.nombreSol)
                    InicializarConexion().execute(datos)
                }else{
                    //mostrar errores
                    Toast.makeText(this, "Existen errores en el codigo a interpretar, dirijase al arrea de reporte de errores", Toast.LENGTH_LONG).show()

                }
            }catch (exe:Exception){
                //mostrar errores
                Toast.makeText(this, "Error desde y try catch: en parser o socket", Toast.LENGTH_LONG).show()

                exe.printStackTrace(System.err)
            }
        }

    }

    private inner class InicializarConexion : AsyncTask<String, Void, OptionConexion>() {
        override fun doInBackground(vararg params: String): OptionConexion {
            val HOST = ErrorSingleton.getInstance().ipConfig
            val PUERTO = 7000
            println(HOST)
            try {
                val sc = Socket(HOST, PUERTO)
                val ouput2 = DataOutputStream(sc.getOutputStream())
                val input = DataInputStream(sc.getInputStream())
                ouput2.writeUTF(tipoSolicitud.toString())
                val oss = ObjectOutputStream(sc.getOutputStream())
                oss.writeObject(resultadoSolicitud)
                val oss2 = ObjectInputStream(sc.getInputStream());
                resultadoSolicitud = oss2.readObject() as? Solicitud
                //results = ((oss2.readObject() as? String).toString())
                //val oss2 = ObjectInputStream(sc.getInputStream());
                //listasMusicales  = (oss2.readObject() as? ArrayList<ListaMusical>)
                return OptionConexion.valueOf(input.readUTF())
            } catch (ex: Exception) {
                println("Error en la conexión: ${ex.message}")
                ex.printStackTrace()
            }finally {
                //sc.close() // Cierra el socket después de usarlo
            }

            return OptionConexion.ERROR // Manejo de error aquí
        }
        override fun onPostExecute(result: OptionConexion) {
            super.onPostExecute(result)
            // Aquí puedes manejar la respuesta en el hilo principal
            println(resultadoSolicitud!!.textSolicitud)
            texResultado.setText(resultadoSolicitud!!.textSolicitud)
            //println(results)
            // println(result.toString())

        }
    }

}