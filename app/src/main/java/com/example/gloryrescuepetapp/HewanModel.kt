package com.example.gloryrescuepetapp

data class HewanModel(
    val id: String = "",              // ID dokumen Firestore (tidak wajib, tapi berguna untuk update/delete)
    val nama: String = "",
    val jenis: String = "",           // Contoh: Kucing, Anjing
    val deskripsi: String = "",       // Keterangan singkat
    val fotoUrl: String = "",         // URL gambar dari Firebase Storage
    val latitude: Double? = null,     // Gunakan null-safe jika data bisa kosong
    val longitude: Double? = null,
    val jarak: Double = 0.0,          // Dihitung di sisi client, bukan dari Firestore
    val status: String = "tersedia"   // Bisa: "tersedia", "diadopsi", dll
)
