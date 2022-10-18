package com.example.sqliteapp

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var etnCodigo:EditText
    private lateinit var etDescripcion:EditText
    private lateinit var etnPrecio:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etnCodigo = findViewById(R.id.etn_Codigo)
        etDescripcion = findViewById(R.id.etn_Descripcion)
        etnPrecio = findViewById(R.id.etn_Precio)
    }

    //Funcion para el boton registrar y mandar el metodo a la vista
    fun registrar(v:View){

        // Se crea la conexion a la base de datos
        val admin = AdminSQLite(this,  "Tienda", null, 1)
        val dataBase:SQLiteDatabase = admin.writableDatabase

        val codido = etnCodigo.text.toString()
        val descripcion = etDescripcion.text.toString()
        val precio = etnPrecio.text.toString().toDouble()

        if (!codido.isEmpty() && !descripcion.isEmpty()){
            val registro = ContentValues()
            registro.put("codigo", codido)
            registro.put("descripcion", descripcion)
            registro.put("precio", precio)

            dataBase.insert("Articulos", null, registro)
            dataBase.close()

            etnCodigo.setText("")
            etDescripcion.setText("")
            etnPrecio.setText("")

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this, "Debe llenar todos los campos para registrar", Toast.LENGTH_LONG).show()
        }
    }

    fun Buscar(v:View){
        // Se crea la conexion a la base de datos
        val admin = AdminSQLite(this,  "Tienda", null, 1)
        val dataBase:SQLiteDatabase = admin.writableDatabase

        val codigo = etnCodigo.text.toString()

        if (!codigo.isEmpty()){
            val fila = dataBase.rawQuery(
                "select descripcion, precio from articulos where codigo = $codigo", null
            )
            if (fila.moveToFirst()){
                etDescripcion.setText(fila.toString())
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_LONG).show()
                dataBase.close()
            }
        }else{
            Toast.makeText(this, "Debe ingresar su codigo", Toast.LENGTH_LONG).show()
        }
    }

    fun Modificar (v:View){
        // Se crea la conexion a la base de datos
        val admin = AdminSQLite(this,  "Tienda", null, 1)
        val dataBase:SQLiteDatabase = admin.writableDatabase

        val codido = etnCodigo.text.toString()
        val descripcion = etDescripcion.text.toString()
        val precio = etnPrecio.text.toString()

        if (!codido.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            val registro = ContentValues()
            //registro.put("codigo", codido)
            registro.put("descripcion", descripcion)
            registro.put("precio", precio)

            val cantidad:Int = dataBase.update("articulo", registro, "codigo=$codido", null)
            dataBase.close()

            if (cantidad==1){
                Toast.makeText(this, "Articulo modificado correctamente", Toast.LENGTH_LONG).show()
                dataBase.close()
            } else {
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_LONG).show()
                dataBase.close()
            }

            etnCodigo.setText("")
            etDescripcion.setText("")
            etnPrecio.setText("")

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Debe llenar todos los campos para registrar", Toast.LENGTH_LONG).show()
        }

    }

    fun Eliminar (v:View){
        // Se crea la conexion a la base de datos
        val admin = AdminSQLite(this,  "Tienda", null, 1)
        val dataBase:SQLiteDatabase = admin.writableDatabase

        val codigo = etnCodigo.text.toString()
        if (!codigo.isEmpty()){
            val cantidad:Int = dataBase.delete("articulo", "codigo=$codigo",null)
            dataBase.close()

            etnCodigo.setText("")
            etDescripcion.setText("")
            etnPrecio.setText("")

            if (cantidad==1){
                Toast.makeText(this, "Articulo eliminado", Toast.LENGTH_LONG).show()
                dataBase.close()
            } else
            {
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_LONG).show()
                dataBase.close()
            }
        } else {
            Toast.makeText(this, "Debes incresar un codigo", Toast.LENGTH_LONG).show()
            dataBase.close()
        }
    }

    fun Siguiente (v:View){
        val ventana:Intent = Intent(applicationContext, SharedPreferenceApp::class.java)
        startActivity(ventana)
    }
}