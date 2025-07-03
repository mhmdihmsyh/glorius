package com.example.gloryrescuepetapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TipsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tips_activity) // Layout utama Activity

        // Ganti dengan ID tombol yang benar dari tips_activity.xml
        val btnTips = findViewById<Button>(R.id.)

        btnTips.setOnClickListener {
            // Menampilkan layout pop-up yang terpisah
            val dialogView = LayoutInflater.from(this).inflate(R.layout.tips_activity, null)
            val dialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            // Tombol di dalam layout pop-up
            val btnClose = dialogView.findViewById<Button>(R.id.btnClose)
            btnClose.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }
}
