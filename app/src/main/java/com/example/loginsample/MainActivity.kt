package com.example.loginsample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts



import com.example.loginsample.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuramos la vista con la debendencia viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Lo relacionamos con los layout creados en xml, por lo que deb coincidir con el nombre establecido
        val edtUsername = binding.edtUsername
        val edtPassword = binding.edtPassword
        val btnLogin = binding.btnLogin

        // funcionalidad del boton login
        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            if (checkCredentialsFromFile(username, password)) {
                Toast.makeText(applicationContext, "Bienvenido $username", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Bienvenido $username")

                // Enviar el nombre de usuario directamente al Intent
                val intent = Intent(this, WelcomeUser::class.java).apply {
                    putExtra("username", username) // Pasa solo el username
                }
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Cuenta no encontrada", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Cuenta no encontrada")
            }
        }


        //Añadimos la navegabilidad
        val btnRegistro = binding.txtRegister2

        btnRegistro.setOnClickListener{
            val intent : Intent = Intent(this, RegistroConference::class.java)
            //startActivity(intent)
            activityResultLauncher.launch(intent)
        }

        val btnShowAccounts = binding.btnVer // Boton que imprime todas las cuentas en cuentas.txt

        btnShowAccounts.setOnClickListener {
            printAllAccounts() // Llama al método para imprimir todas las cuentas
        }

    }
    //Activity para almacenar la infromacion registrada en los txt al dar aceptar y al cancelar solo medevuelve al login
    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        val resultCode = activityResult.resultCode
        Log.d("LoginActivity", "reusltado: $resultCode")

        if (resultCode == RegistroConference.ACCOUNT_ACEPTAR) {
            val data = activityResult.data
            val accountRecord = data?.getStringExtra(RegistroConference.ACCOUNT_RECORD)

            val gson = Gson()
            val accountEntity = gson.fromJson(accountRecord, RegistroConferenceEntity::class.java)

            val firstname = accountEntity.getFirstName()
            Toast.makeText(applicationContext, "Nombre: $firstname", Toast.LENGTH_LONG).show()
            Log.d("LoginActivity", "Nombre: $firstname")

            // Save account to cuentas.txt
            saveAccountToFile(accountEntity)

        } else if (resultCode == RegistroConference.ACCOUNT_CANCELAR) {
            Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_LONG).show()
            Log.d("LoginActivity", "Cancelado")
        }
    }

    //Metodo para guardar la infromacion en el archivo cuentas.txt
    private fun saveAccountToFile(accountEntity: RegistroConferenceEntity) {
        val filename = "cuentas.txt"
        val fileContents = "Nombre: ${accountEntity.getFirstName()}\nApellido: ${accountEntity.getLastName()}\n" +
                "Email: ${accountEntity.getEmail()}\nTeléfono: ${accountEntity.getPhone()}\n" +
                "Usuario: ${accountEntity.getUsername()}\nContraseña: ${accountEntity.getPassword()}\n"

        try {
            openFileOutput(filename, MODE_PRIVATE or MODE_APPEND).use { outputStream ->
                outputStream.write(fileContents.toByteArray())
            }
            Log.d(TAG, "Cuenta correctamente guardada en $filename")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(TAG, "Error al guardar archivo")
        }
    }

    private fun checkCredentialsFromFile(username: String, password: String): Boolean {
        val filename = "cuentas.txt"
        var currentUsername = ""
        var currentPassword = ""

        try {
            openFileInput(filename).use { inputStream ->
                inputStream.bufferedReader().useLines { lines ->
                    lines.forEach { line ->
                        when {
                            line.startsWith("Usuario: ") -> {
                                currentUsername = line.removePrefix("Usuario: ").trim()
                            }
                            line.startsWith("Contraseña: ") -> {
                                currentPassword = line.removePrefix("Contraseña: ").trim()

                                // Verificamos si el username y password coinciden
                                if (currentUsername == username && currentPassword == password) {
                                    return true
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(TAG, "Error al leer archivo o archivo no existe")
        }
        return false
    }


    private fun printAllAccounts() {
        val filename = "cuentas.txt"
        try {
            openFileInput(filename).use { inputStream ->
                inputStream.bufferedReader().useLines { lines ->
                    val allAccounts = StringBuilder()
                    lines.forEach { line ->
                        allAccounts.append(line).append("\n")
                    }
                    Log.d(TAG, "Cuentas almacenadas:\n$allAccounts")
                    Toast.makeText(applicationContext, "Cuentas almacenadas:\n$allAccounts", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(TAG, "Error al leer archivo o archivo no existe")
            Toast.makeText(applicationContext, "Error al leer archivo o archivo no existe", Toast.LENGTH_SHORT).show()
        }
    }


}