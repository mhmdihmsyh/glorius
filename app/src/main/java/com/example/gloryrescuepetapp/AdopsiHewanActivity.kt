package com.example.gloryrescuepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.gloryrescuepetapp.ui.main.FormAdopsiActivity

class AdopsiHewanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adopsi_hewan)

        // Inisialisasi tombol navigasi
        val btnBeranda = findViewById<Button>(R.id.btnBeranda)
        val btnPosting = findViewById<Button>(R.id.btnPosting)
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

        // Logout ke halaman Login
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        // Inisialisasi gambar hewan
        val dogImage = findViewById<ImageView>(R.id.imgDog)
        val catImage = findViewById<ImageView>(R.id.imgCat)

        // Klik gambar anjing
        dogImage.setOnClickListener {
            val intent = Intent(this, FormAdopsiActivity::class.java)
            intent.putExtra("jenis", "Anjing")
            startActivity(intent)
        }

        // Klik gambar kucing
        catImage.setOnClickListener {
            val intent = Intent(this, FormAdopsiActivity::class.java)
            intent.putExtra("jenis", "Kucing")
            startActivity(intent)
        }
    }
}
