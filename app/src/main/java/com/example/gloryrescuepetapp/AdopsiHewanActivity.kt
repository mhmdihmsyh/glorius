package com.example.gloryrescuepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdopsiHewanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adopsi_hewan)

        // Inisialisasi button
        val btnBeranda = findViewById<Button>(R.id.btnBeranda)
        val btnPosting = findViewById<Button>(R.id.btnPosting)
        // val btnProfile = findViewById<Button>(R.id.btnProfile)
        val btnLogout = findViewById<Button>(R.id.btnKeluar)

        // Navigasi ke halaman Beranda
        btnBeranda.setOnClickListener {
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
        }

        // Navigasi ke halaman Posting Hewan
        btnPosting.setOnClickListener {
            startActivity(Intent(this, PostingHewanActivity::class.java))
        }

        // Navigasi ke halaman Profil (jika sudah dibuat)
        //btnProfile.setOnClickListener {
          //  startActivity(Intent(this, ProfileActivity::class.java))
        //}

        // Logout ke halaman Login
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish() // agar user tidak bisa kembali ke halaman ini setelah logout
        }

    }
}
