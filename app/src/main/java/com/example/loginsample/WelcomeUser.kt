package com.example.loginsample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginsample.databinding.ActivityWelcomeUserBinding

class WelcomeUser : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el nombre del suario
        val username = intent.getStringExtra("username") ?: "Usuario no disponible"

        // Muestra solo el mensaje de bienvenida
        binding.textViewUserInfo.text = "Bienvenido, $username"
    }
}
