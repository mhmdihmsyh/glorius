package com.example.gloryrescuepetapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gloryrescuepetapp.*

class FormAdopsiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_adopsi)

        // Ambil intent jenis hewan
        val jenis = intent.getStringExtra("jenis") ?: ""

        // Inisialisasi View
        val judulForm = findViewById<TextView>(R.id.textJudulForm)
        val inputNama = findViewById<EditText>(R.id.inputNama)
        val inputEmail = findViewById<EditText>(R.id.inputEmail)
        val inputWhatsapp = findViewById<EditText>(R.id.inputWhatsapp)
        val inputKota = findViewById<EditText>(R.id.inputKota)
        val inputJenisHewan = findViewById<EditText>(R.id.inputJenisHewan)
        val inputNamaHewan = findViewById<EditText>(R.id.inputNamaHewan)
        val inputAlasan = findViewById<EditText>(R.id.inputAlasan)
        val inputPengalaman = findViewById<EditText>(R.id.inputPengalaman)
        val btnAjukan = findViewById<Button>(R.id.btnAjukan)
        val btnReset = findViewById<Button>(R.id.btnReset)

        // Navigasi tombol atas
        val btnBeranda = findViewById<Button>(R.id.btnBeranda)
        val btnPosting = findViewById<Button>(R.id.btnPosting)
        val btnKeluar = findViewById<Button>(R.id.btnKeluar)

        // Navigasi ke halaman beranda
        btnBeranda?.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }

        // Navigasi ke halaman posting
        btnPosting?.setOnClickListener {
            startActivity(Intent(this, PostingHewanActivity::class.java))
        }

        // Logout ke halaman login
        btnKeluar?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Set jenis hewan jika tersedia
        if (jenis.isNotEmpty()) {
            inputJenisHewan.setText(jenis)
            judulForm.text = "Formulir Adopsi $jenis"
        }

        // Tombol Ajukan
        btnAjukan.setOnClickListener {
            Toast.makeText(this, "Permohonan adopsi berhasil dikirim!", Toast.LENGTH_SHORT).show()
            // Tambahkan pengiriman ke Firebase/server jika diperlukan
        }

        // Tombol Reset
        btnReset.setOnClickListener {
            inputNama.text.clear()
            inputEmail.text.clear()
            inputWhatsapp.text.clear()
            inputKota.text.clear()
            inputJenisHewan.text.clear()
            inputNamaHewan.text.clear()
            inputAlasan.text.clear()
            inputPengalaman.text.clear()
        }
    }
}
