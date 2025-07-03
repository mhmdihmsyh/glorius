package com.example.gloryrescuepetapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar // Tambahkan import untuk Toolbar
import androidx.cardview.widget.CardView

class BerandaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Baris ini menghubungkan file XML layout ke Activity ini.
        // Pastikan nama file XML Anda adalah 'activity_beranda.xml'
        // dan bukan 'beranda_activity.xml' jika Anda mengikuti standar penamaan Android.
        // Jika nama file XML Anda memang 'beranda_activity.xml', maka tidak masalah.
        setContentView(R.layout.beranda_activity) // Ubah ini jika nama file XML Anda berbeda

        // 1. Inisialisasi Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar) // Dapatkan referensi ke Toolbar dari layout
        setSupportActionBar(toolbar) // Atur Toolbar ini sebagai action bar untuk aktivitas

        // Opsional: Sembunyikan judul default yang mungkin ditambahkan oleh setSupportActionBar
        supportActionBar?.setDisplayShowTitleEnabled(false)


        // 2. Menginisialisasi Elemen UI lainnya (Menemukan elemen dari layout XML berdasarkan ID)
        // TextViews di Navigasi Atas
        val tvBeranda: TextView = findViewById(R.id.tv_beranda)
        val tvPostingHewanMenu: TextView = findViewById(R.id.tv_posting_hewan_menu)
        val tvProfile: TextView = findViewById(R.id.tv_profile)
        val tvKeluar: TextView = findViewById(R.id.tv_keluar)

        // TextViews di Bagian Statistik (Optional, jika Anda ingin mengubah teksnya secara dinamis)
        val tvJumlahTeradopsi: TextView = findViewById(R.id.tv_jumlah_teradopsi)
        val tvHewanHilang: TextView = findViewById(R.id.tv_hewan_hilang)
        val tvJumlahKomunitas: TextView = findViewById(R.id.tv_jumlah_komunitas)

        // Buttons di Bagian Konten
        val btnMulaiPosting: Button = findViewById(R.id.btn_mulai_posting)
        val btnMulaiPencarian: Button = findViewById(R.id.btn_mulai_pencarian)
        val btnLihatTips: Button = findViewById(R.id.btn_lihat_tips)

        // ImageViews di Bagian Social Media
        val ivInstagram: ImageView = findViewById(R.id.iv_instagram)
        val ivTwitter: ImageView = findViewById(R.id.iv_twitter)
        val ivFacebook: ImageView = findViewById(R.id.iv_facebook)

        // CardViews (Optional, jika Anda ingin mengatur click listener untuk seluruh card)
        val cvPostingHewan: CardView = findViewById(R.id.cv_posting_hewan)
        val cvCariHewan: CardView = findViewById(R.id.cv_cari_hewan)
        val cvTipsPanduan: CardView = findViewById(R.id.cv_tips_panduan)


        // 3. Mengatur Click Listener untuk Elemen UI (Memberikan "aksi" saat diklik)

        // Click Listener untuk Navigasi Atas
        tvBeranda.setOnClickListener {
            // Karena ini adalah aktivitas saat ini, tidak perlu aksi atau bisa refresh
            Toast.makeText(this, "Anda sudah di Beranda", Toast.LENGTH_SHORT).show()
        }

        tvPostingHewanMenu.setOnClickListener {
            // Contoh: Navigasi ke PostingHewanActivity (Anda perlu membuat Activity ini)
            val intent = Intent(this, PostingHewanActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Mengarahkan ke Posting Hewan (Menu)", Toast.LENGTH_SHORT).show()
        }

        tvProfile.setOnClickListener {
            // Contoh: Navigasi ke ProfileActivity (Anda perlu membuat Activity ini)
            // val intent = Intent(this, ProfileActivity::class.java)
            // startActivity(intent)
            Toast.makeText(this, "Mengarahkan ke Profile", Toast.LENGTH_SHORT).show()
        }

        tvKeluar.setOnClickListener {
            // Contoh: Implementasi logika logout atau kembali ke layar login
            Toast.makeText(this, "Fitur Keluar belum diimplementasikan", Toast.LENGTH_SHORT).show()
            finish() // Menutup aktivitas saat ini
        }

        // Click Listener untuk Tombol Aksi Utama
        btnMulaiPosting.setOnClickListener {
            // Aksi untuk tombol "Mulai Posting"
            Toast.makeText(this, "Mulai Posting diklik!", Toast.LENGTH_SHORT).show()
            // Anda kemungkinan akan memulai aktivitas baru di sini, contoh:
             val intent = Intent(this, PostingHewanActivity::class.java)
             startActivity(intent)
        }

        btnMulaiPencarian.setOnClickListener {
            // Aksi untuk tombol "Mulai Pencarian"
            Toast.makeText(this, "Mulai Pencarian diklik!", Toast.LENGTH_SHORT).show()
            // Anda kemungkinan akan memulai aktivitas baru di sini, contoh:
            // val intent = Intent(this, SearchActivity::class.java)
            // startActivity(intent)
        }

        btnLihatTips.setOnClickListener {
            // Aksi untuk tombol "Lihat Tips"
            Toast.makeText(this, "Lihat Tips diklik!", Toast.LENGTH_SHORT).show()
            // Anda kemungkinan akan memulai aktivitas baru di sini, contoh:
            val intent = Intent(this, TipsActivity::class.java)
             startActivity(intent)
        }

        // Contoh: Anda juga bisa mengatur click listener untuk seluruh CardView
        cvPostingHewan.setOnClickListener {
            Toast.makeText(this, "Card Posting Hewan diklik!", Toast.LENGTH_SHORT).show()
            // Biasanya, Anda akan memanggil aksi yang sama dengan tombol di dalamnya
            // btnMulaiPosting.performClick()
        }

        cvCariHewan.setOnClickListener {
            Toast.makeText(this, "Card Cari Hewan diklik!", Toast.LENGTH_SHORT).show()
        }

        cvTipsPanduan.setOnClickListener {
            Toast.makeText(this, "Card Tips & Panduan diklik!", Toast.LENGTH_SHORT).show()
        }


        // Click Listener untuk Ikon Media Sosial
        ivInstagram.setOnClickListener {
            openUrl("https://www.instagram.com/your_rescuepet_instagram") // Ganti dengan URL Instagram Anda yang sebenarnya
        }

        ivTwitter.setOnClickListener {
            openUrl("https://twitter.com/your_rescuepet_twitter") // Ganti dengan URL Twitter Anda yang sebenarnya
        }

        ivFacebook.setOnClickListener {
            openUrl("https://www.facebook.com/your_rescuepet_facebook") // Ganti dengan URL Facebook Anda yang sebenarnya
        }

        // Contoh bagaimana Anda bisa mengubah teks secara dinamis (opsional)
        // tvJumlahTeradopsi.text = "1,500"
    }

    // Fungsi Pembantu untuk Membuka URL di Browser
    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Tidak dapat membuka tautan: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
