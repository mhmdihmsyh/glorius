package com.example.gloryrescuepetapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class TipsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tips_activity)

        val btnTips = findViewById<Button>(R.id.btnClose)

        btnTips.setOnClickListener {
            // loading dialog akan ditampilkan
            val loadingDialog = Dialog(this)
            val loadingView = LayoutInflater.from(this).inflate(R.layout.loading_dialog, null)
            loadingDialog.setContentView(loadingView)
            loadingDialog.setCancelable(false)
            loadingDialog.show()

            // untuk animasi rotasi
            val imgLoading = loadingView.findViewById<ImageView>(R.id.imgLoading)
            val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_loading)
            imgLoading.startAnimation(rotate)

            // Setelah 3 detik, menampilkan dialog
            Handler(Looper.getMainLooper()).postDelayed({
                loadingDialog.dismiss()

                AlertDialog.Builder(this)
                    .setTitle("Tips dan Panduan")
                    .setMessage("Berikut beberapa tips merawat hewan:\n\n" +
                            "1. Beri makan secara teratur.\n" +
                            "2. Lakukan vaksinasi.\n" +
                            "3. Jaga kebersihan.\n" +
                            "4. Berikan perhatian dan kasih sayang.")
                    .setPositiveButton("Tutup") { dialog, _ -> dialog.dismiss() }
                    .show()

            }, 3000)
        }
    }
}
