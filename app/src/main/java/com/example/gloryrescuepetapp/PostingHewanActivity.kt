package com.example.gloryrescuepetapp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PostingHewanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.postinghewan)

        val btnBeranda = findViewById<TextView>(R.id.btnBeranda)
        val btnPosting = findViewById<TextView>(R.id.btnPosting)
        val btnKeluar = findViewById<TextView>(R.id.btnKeluar)

        // ✅ Aksi klik ke LinearLayout "Adopsi Hewan"
        val cardAdopsi = findViewById<LinearLayout>(R.id.card_adopsi)
        cardAdopsi.setOnClickListener {
            val intent = Intent(this, AdopsiHewanActivity::class.java)
            startActivity(intent)
        }

        btnBeranda.setOnClickListener {
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
        }

        btnPosting.setOnClickListener {
            val intent = Intent(this, PostingHewanActivity::class.java)
            startActivity(intent)
        }

        btnKeluar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
