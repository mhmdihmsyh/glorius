package com.example.gloryrescuepetapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gloryrescuepetapp.*
import com.google.firebase.firestore.FirebaseFirestore

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

        btnBeranda?.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
        }

        btnPosting?.setOnClickListener {
            startActivity(Intent(this, PostingHewanActivity::class.java))
        }

        btnKeluar?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        if (jenis.isNotEmpty()) {
            inputJenisHewan.setText(jenis)
            judulForm.text = "Formulir Adopsi $jenis"
        }

        btnAjukan.setOnClickListener {
            val nama = inputNama.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val whatsapp = inputWhatsapp.text.toString().trim()
            val kota = inputKota.text.toString().trim()
            val jenisHewan = inputJenisHewan.text.toString().trim()
            val namaHewan = inputNamaHewan.text.toString().trim()
            val alasan = inputAlasan.text.toString().trim()
            val pengalaman = inputPengalaman.text.toString().trim()

            if (nama.isEmpty() || email.isEmpty() || whatsapp.isEmpty() || kota.isEmpty() ||
                jenisHewan.isEmpty() || namaHewan.isEmpty() || alasan.isEmpty() || pengalaman.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua kolom.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf(
                "nama" to nama,
                "email" to email,
                "whatsapp" to whatsapp,
                "kota" to kota,
                "jenis_hewan" to jenisHewan,
                "nama_hewan" to namaHewan,
                "alasan" to alasan,
                "pengalaman" to pengalaman,
                "timestamp" to System.currentTimeMillis()
            )

            val db = FirebaseFirestore.getInstance()
            db.collection("permohonan_adopsi")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Permohonan adopsi berhasil dikirim!", Toast.LENGTH_LONG).show()
                    inputNama.text.clear()
                    inputEmail.text.clear()
                    inputWhatsapp.text.clear()
                    inputKota.text.clear()
                    inputJenisHewan.text.clear()
                    inputNamaHewan.text.clear()
                    inputAlasan.text.clear()
                    inputPengalaman.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal mengirim data: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

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
