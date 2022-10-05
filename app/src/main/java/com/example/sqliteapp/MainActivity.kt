package com.example.sqliteapp

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var etnCodigo:EditText
    private lateinit var etDescripcion:EditText
    private lateinit var etndPrecio:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etnCodigo = findViewById(R.id.etn_Codigo)
        etDescripcion = findViewById(R.id.etn_Descripcion)
        etndPrecio = findViewById(R.id.etnd_Precio)
    }

    //Funcion para el boton registrar y mandar el metodo a la vista
    fun registrar(v:View){

        // Se crea la conexion a la base de datos
        val admin = AdminSQLite(this,  "Tienda", null, 1)
        val dataBase:SQLiteDatabase = admin.writableDatabase

        val codido = etnCodigo.text.toString()
        val descripcion = etDescripcion.text.toString()
        val precio = etndPrecio.text.toString().toDouble()

        if (!codido.isEmpty() && !descripcion.isEmpty()){
            val registro = ContentValues()
            registro.put("codigo", codido)
            registro.put("descripcion", descripcion)
            registro.put("precio", precio)

            dataBase.insert("Articulos", null, registro)
            dataBase.close()

            etnCodigo.setText("")
            etDescripcion.setText("")
            etndPrecio.setText("")

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this, "Debe llenar todos los campos para registrar", Toast.LENGTH_LONG).show()
        }
    }
}