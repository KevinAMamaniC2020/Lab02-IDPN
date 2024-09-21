package com.example.loginsample

import android.content.Intent
import java.io.*

import  android.os.Bundle
import android.util.Log
import android.widget.Button

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginsample.databinding.ActivityMainBinding
import com.example.loginsample.databinding.ActivityRegistroConferenceBinding
import com.google.gson.Gson

class RegistroConference : AppCompatActivity() {

    companion object {
        const val ACCOUNT_ACEPTAR = 100
        const val ACCOUNT_CANCELAR = 200
        val ACCOUNT_RECORD: String = ""
    }


    private lateinit var binding: ActivityRegistroConferenceBinding
    private val filename = "datos.txt"
    private val TAG = "RegistroActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_conference)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityRegistroConferenceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val btnSaves = binding.btnSave
        val btnViews = binding.btnView


        btnSaves.setOnClickListener { saveData() }
        btnViews.setOnClickListener { readData() }


        val btnAccept = binding.btnAceptar
        val btnCancel = binding.btnCancelar

        btnAccept.setOnClickListener{ saveUser() }
        btnCancel.setOnClickListener { cancelRegister() }

    }

    private fun saveUser(){
        var edtName = binding.txtRegName.text.toString()
        var edtLastName = binding.txtRegApellido.text.toString()
        var edtEmail = binding.txtRegEmail.text.toString()
        var edtPhone = binding.txtRegTelefono.text.toString()
        var edtDate = binding.txtRegNacimiento.text.toString()
        var edtUser = binding.txtUser.text.toString()
        var edtPassword = binding.txtPassword.text.toString()

        val registroConferenceEntity = RegistroConferenceEntity().apply {
            setFirstName(edtName.toString())
            setLastName(edtLastName.toString())
            setEmail(edtEmail.toString())
            setPhone(edtPhone.toString())
            setNacimiento(edtDate.toString())
            setUsername(edtUser.toString())
            setPassword(edtPassword.toString())
        }

        val gson = Gson()
        val accountJson:String = gson.toJson(registroConferenceEntity)

        val data = Intent()
        data.putExtra(ACCOUNT_RECORD,accountJson)

        setResult(ACCOUNT_ACEPTAR,data)
        finish()
    }
    private fun cancelRegister (){
        setResult(ACCOUNT_CANCELAR)
        finish()
    }




    private fun saveData() {
        val edtName = binding.txtRegName.text.toString()
        val edtLastName = binding.txtRegApellido.text.toString()
        val edtEmail = binding.txtRegEmail.text.toString()
        val edtPhone = binding.txtRegTelefono.text.toString()
        val edtDate = binding.txtRegNacimiento.text.toString()

        // Crear el contenido a guardar
        val contenido = "Nombre: $edtName\nApellido: $edtLastName\nEmail: $edtEmail\nTelefono: $edtPhone\nFechaNacimiento: $edtDate"

        // Guardar en archivo de texto plano
        try {
            val fileOutputStream = openFileOutput(filename, MODE_PRIVATE)
            fileOutputStream.write(contenido.toByteArray())
            fileOutputStream.close()
            Log.d(TAG, "Datos guardados exitosamente")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readData() {
        try {
            val fileInputStream: FileInputStream = openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var texto: String?

            while (bufferedReader.readLine().also { texto = it } != null) {
                stringBuilder.append(texto).append("\n")
            }

            bufferedReader.close()

            // Mostrar el contenido leído en la consola con Log
            Log.d(TAG, "Datos leídos: \n$stringBuilder")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "Error al leer los datos")
        }
    }
}