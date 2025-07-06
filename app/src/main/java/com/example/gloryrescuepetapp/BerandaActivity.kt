package com.example.gloryrescuepetapp

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class BerandaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.beranda_activity)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val tvBeranda: TextView = findViewById(R.id.tv_beranda)
        val tvPostingHewanMenu: TextView = findViewById(R.id.tv_posting_hewan_menu)
        val tvProfile: TextView = findViewById(R.id.tv_profile)
        val tvKeluar: TextView = findViewById(R.id.tv_keluar)

        val tvJumlahTeradopsi: TextView = findViewById(R.id.tv_jumlah_teradopsi)
        val tvHewanHilang: TextView = findViewById(R.id.tv_hewan_hilang)
        val tvJumlahKomunitas: TextView = findViewById(R.id.tv_jumlah_komunitas)

        val btnMulaiPosting: Button = findViewById(R.id.btn_mulai_posting)
        val btnMulaiPencarian: Button = findViewById(R.id.btn_mulai_pencarian)
        val btnLihatTips: Button = findViewById(R.id.btn_lihat_tips)

        val ivInstagram: ImageView = findViewById(R.id.iv_instagram)
        val ivTwitter: ImageView = findViewById(R.id.iv_twitter)
        val ivFacebook: ImageView = findViewById(R.id.iv_facebook)

        val cvPostingHewan: CardView = findViewById(R.id.cv_posting_hewan)
        val cvCariHewan: CardView = findViewById(R.id.cv_cari_hewan)
        val cvTipsPanduan: CardView = findViewById(R.id.cv_tips_panduan)

        // Navigasi atas
        tvBeranda.setOnClickListener {
            Toast.makeText(this, "Anda sudah di Beranda", Toast.LENGTH_SHORT).show()
        }

        tvPostingHewanMenu.setOnClickListener {
            val intent = Intent(this, PostingHewanActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Mengarahkan ke Posting Hewan (Menu)", Toast.LENGTH_SHORT).show()
        }

        tvProfile.setOnClickListener {
            Toast.makeText(this, "Mengarahkan ke Profile", Toast.LENGTH_SHORT).show()
        }

        tvKeluar.setOnClickListener {
            Toast.makeText(this, "Fitur Keluar belum diimplementasikan", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Tombol Posting Hewan dengan loading screen
        btnMulaiPosting.setOnClickListener {
            val loadingDialog = Dialog(this)
            val loadingView = LayoutInflater.from(this).inflate(R.layout.loading_dialog, null)
            loadingDialog.setContentView(loadingView)
            loadingDialog.setCancelable(false)
            loadingDialog.show()

            val imgLoading = loadingView.findViewById<ImageView>(R.id.imgLoading)
            val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_loading)
            imgLoading.startAnimation(rotate)

            Handler(Looper.getMainLooper()).postDelayed({
                loadingDialog.dismiss()
                val intent = Intent(this, PostingHewanActivity::class.java)
                startActivity(intent)
            }, 2000)
        }

        btnMulaiPencarian.setOnClickListener {
            Toast.makeText(this, "Mulai Pencarian diklik!", Toast.LENGTH_SHORT).show()
        }

        btnLihatTips.setOnClickListener {
            Toast.makeText(this, "Lihat Tips diklik!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TipsActivity::class.java)
            startActivity(intent)
        }

        // CardView aksi - trigger tombol yang sama
        cvPostingHewan.setOnClickListener {
            btnMulaiPosting.performClick()
        }

        cvCariHewan.setOnClickListener {
            btnMulaiPencarian.performClick()
        }

        cvTipsPanduan.setOnClickListener {
            btnLihatTips.performClick()
        }

        // Media sosial
        ivInstagram.setOnClickListener {
            openUrl("https://www.instagram.com/your_rescuepet_instagram")
        }

        ivTwitter.setOnClickListener {
            openUrl("https://twitter.com/your_rescuepet_twitter")
        }

        ivFacebook.setOnClickListener {
            openUrl("https://www.facebook.com/your_rescuepet_facebook")
        }
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Tidak dapat membuka tautan: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
