package com.example.gloryrescuepetapp.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log // Import Log untuk debugging
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.gloryrescuepetapp.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class HewanHilangActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var imgPreview: ImageView

    private lateinit var inputNamaPelapor: TextInputEditText
    private lateinit var inputKontak: TextInputEditText
    private lateinit var inputKota: TextInputEditText
    private lateinit var inputJenisHewan: TextInputEditText
    private lateinit var inputNamaHewan: TextInputEditText
    private lateinit var inputCiriKhusus: TextInputEditText
    private lateinit var inputTanggalHilang: TextInputEditText
    private lateinit var inputLokasiTerakhir: TextInputEditText
    private lateinit var inputPesanTambahan: TextInputEditText

    // Tag untuk Logcat, bisa diubah sesuai keinginan
    private val TAG = "HewanHilangActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_hewan_hilang)

        inputNamaPelapor = findViewById(R.id.inputNamaPelapor)
        inputKontak = findViewById(R.id.inputKontak)
        inputKota = findViewById(R.id.inputKota)
        inputJenisHewan = findViewById(R.id.inputJenisHewan)
        inputNamaHewan = findViewById(R.id.inputNamaHewan)
        inputCiriKhusus = findViewById(R.id.inputCiriKhusus)
        inputTanggalHilang = findViewById(R.id.inputTanggalHilang)
        inputLokasiTerakhir = findViewById(R.id.inputLokasiTerakhir)
        inputPesanTambahan = findViewById(R.id.inputPesanTambahan)

        val btnLaporkan = findViewById<Button>(R.id.btnLaporkan)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val btnPilihGambar = findViewById<Button>(R.id.btnPilihGambar)
        imgPreview = findViewById(R.id.imgPreview)

        val btnBeranda = findViewById<Button>(R.id.btnBeranda)
        val btnPosting = findViewById<Button>(R.id.btnPosting)
        val btnProfile = findViewById<Button>(R.id.btnProfile)
        val btnKeluar = findViewById<Button>(R.id.btnKeluar)

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUri = result.data?.data
                imgPreview.setImageURI(imageUri)
                imgPreview.visibility = View.VISIBLE
                Log.d(TAG, "Image selected: $imageUri") // Debug log: Gambar dipilih
            } else {
                Toast.makeText(this, "Pemilihan gambar dibatalkan.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Image selection cancelled.") // Debug log: Pemilihan gambar dibatalkan
            }
        }

        btnPilihGambar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
            Log.d(TAG, "Pick image button clicked. Launching gallery intent.") // Debug log: Tombol pilih gambar ditekan
        }

        btnBeranda.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
            finish()
        }

        btnPosting.setOnClickListener {
            startActivity(Intent(this, PostingHewanActivity::class.java))
            finish()
        }

        btnProfile.setOnClickListener {
            Toast.makeText(this, "Fitur Profil akan datang!", Toast.LENGTH_SHORT).show()
        }

        btnKeluar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnLaporkan.setOnClickListener {
            val namaPelapor = inputNamaPelapor.text.toString().trim()
            val kontak = inputKontak.text.toString().trim()
            val kota = inputKota.text.toString().trim()
            val jenisHewan = inputJenisHewan.text.toString().trim()
            val namaHewan = inputNamaHewan.text.toString().trim()
            val ciriKhusus = inputCiriKhusus.text.toString().trim()
            val tanggalHilang = inputTanggalHilang.text.toString().trim()
            val lokasiTerakhir = inputLokasiTerakhir.text.toString().trim()
            val pesanTambahan = inputPesanTambahan.text.toString().trim()

            Log.d(TAG, "Laporkan button clicked.") // Debug log: Tombol Laporkan ditekan
            Log.d(TAG, "Form data: Nama Pelapor=$namaPelapor, Kontak=$kontak, Kota=$kota, Jenis Hewan=$jenisHewan, Nama Hewan=$namaHewan, Ciri Khusus=$ciriKhusus, Tanggal Hilang=$tanggalHilang, Lokasi Terakhir=$lokasiTerakhir, Pesan Tambahan=$pesanTambahan")

            if (namaPelapor.isEmpty() || kontak.isEmpty() || kota.isEmpty() || jenisHewan.isEmpty() ||
                namaHewan.isEmpty() || ciriKhusus.isEmpty() || tanggalHilang.isEmpty() || lokasiTerakhir.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua kolom yang wajib diisi.", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Validation failed: Required fields are empty.") // Debug log: Validasi gagal
                return@setOnClickListener
            }

            if (imageUri != null) {
                Log.d(TAG, "Image URI exists. Starting upload to Supabase.") // Debug log: URI gambar ada
                uploadToSupabase(imageUri!!) { imageUrl ->
                    Log.d(TAG, "Image uploaded to Supabase. URL: $imageUrl. Now saving to Firestore.") // Debug log: Upload berhasil
                    simpanKeFirestore(namaPelapor, kontak, kota, jenisHewan, namaHewan, ciriKhusus, tanggalHilang, lokasiTerakhir, pesanTambahan, imageUrl)
                }
            } else {
                Toast.makeText(this, "Gambar hewan belum dipilih. Melanjutkan tanpa gambar.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "No image selected. Saving data to Firestore without image URL.") // Debug log: Tidak ada gambar dipilih
                simpanKeFirestore(namaPelapor, kontak, kota, jenisHewan, namaHewan, ciriKhusus, tanggalHilang, lokasiTerakhir, pesanTambahan, "")
            }
        }

        btnReset.setOnClickListener {
            clearFormFields()
            Toast.makeText(this, "Formulir telah direset.", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Form fields reset.") // Debug log: Form direset
        }
    }

    private fun uploadToSupabase(imageUri: Uri, callback: (String) -> Unit) {
        Log.d(TAG, "Starting uploadToSupabase for URI: $imageUri") // Debug log: Memulai upload
        CoroutineScope(Dispatchers.IO).launch {
            var tempFile: File? = null // Declare tempFile here to make it accessible in finally block
            try {
                val contentResolver = contentResolver
                val inputStream = contentResolver.openInputStream(imageUri) ?: run {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@HewanHilangActivity, "Gagal membaca gambar.", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Failed to open input stream for URI: $imageUri") // Debug log: Gagal membuka stream
                    }
                    return@launch
                }

                val fileName = "${UUID.randomUUID()}.jpg"
                tempFile = File(cacheDir, fileName)
                val outputStream = FileOutputStream(tempFile)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
                Log.d(TAG, "Image copied to temp file: ${tempFile.absolutePath}") // Debug log: Gambar disalin ke temp file

                val requestBody = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

                val client = OkHttpClient()
                val supabaseUrl = "https://vxpxmpqgmerhnlgwsfuv.supabase.co/storage/v1/object/hewanhilang/$fileName"
                val supabaseAnonKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZ4cHhtcHFnbWVyaG5sZ3dzZnV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTI0OTIxNjEsImV4cCI6MjA2ODA2ODE2MX0.kflK1IqZpf0slxrP6fijqbBM-OxmffFCd44zLX98GdM"

                Log.d(TAG, "Supabase URL: $supabaseUrl") // Debug log: URL Supabase
                Log.d(TAG, "Supabase Anon Key (first 5 chars): ${supabaseAnonKey.take(5)}...") // Debug log: Kunci Anon (jangan log penuh)

                val request = Request.Builder()
                    .url(supabaseUrl)
                    .header("Authorization", "Bearer $supabaseAnonKey")
                    .header("Content-Type", "image/jpeg")
                    .put(requestBody)
                    .build()

                Log.d(TAG, "Sending HTTP PUT request to Supabase Storage.") // Debug log: Mengirim request
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val imageUrl = supabaseUrl
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@HewanHilangActivity, "Gambar berhasil diunggah!", Toast.LENGTH_SHORT).show()
                        Log.i(TAG, "Image upload successful. Public URL: $imageUrl") // Debug log: Upload berhasil
                        callback(imageUrl)
                    }
                } else {
                    val errorBody = response.body?.string() ?: "Unknown error"
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@HewanHilangActivity, "Upload gagal: ${response.code} - $errorBody", Toast.LENGTH_LONG).show()
                        Log.e(TAG, "Image upload failed! Response code: ${response.code}, Message: ${response.message}, Error Body: $errorBody") // Debug log: Upload gagal
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@HewanHilangActivity, "Error upload: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e(TAG, "Exception during image upload: ${e.message}", e) // Debug log: Exception selama upload
                }
            } finally {
                tempFile?.delete() // Hapus file sementara setelah upload selesai (atau gagal)
                Log.d(TAG, "Temporary file deleted: ${tempFile?.absolutePath}") // Debug log: File sementara dihapus
            }
        }
    }

    private fun simpanKeFirestore(
        namaPelapor: String, kontak: String, kota: String, jenisHewan: String,
        namaHewan: String, ciriKhusus: String, tanggalHilang: String, lokasiTerakhir: String,
        pesanTambahan: String, imageUrl: String?
    ) {
        val data = hashMapOf(
            "nama_pelapor" to namaPelapor,
            "kontak" to kontak,
            "kota" to kota,
            "jenis_hewan" to jenisHewan,
            "nama_hewan" to namaHewan,
            "ciri_khusus" to ciriKhusus,
            "tanggal_hilang" to tanggalHilang,
            "lokasi_terakhir" to lokasiTerakhir,
            "pesan_tambahan" to pesanTambahan,
            "image_url" to (imageUrl ?: ""),
            "timestamp" to System.currentTimeMillis()
        )

        Log.d(TAG, "Attempting to save data to Firestore: $data") // Debug log: Mencoba menyimpan ke Firestore

        FirebaseFirestore.getInstance().collection("laporan_hewan_hilang")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Laporan berhasil dikirim!", Toast.LENGTH_LONG).show()
                Log.i(TAG, "Data successfully saved to Firestore with ID: ${documentReference.id}") // Debug log: Berhasil disimpan
                clearFormFields()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal mengirim laporan: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e(TAG, "Error saving data to Firestore: ${e.message}", e) // Debug log: Gagal menyimpan
            }
    }

    private fun clearFormFields() {
        inputNamaPelapor.text?.clear()
        inputKontak.text?.clear()
        inputKota.text?.clear()
        inputJenisHewan.text?.clear()
        inputNamaHewan.text?.clear()
        inputCiriKhusus.text?.clear()
        inputTanggalHilang.text?.clear()
        inputLokasiTerakhir.text?.clear()
        inputPesanTambahan.text?.clear()
        imageUri = null
        imgPreview.setImageDrawable(null)
        imgPreview.visibility = View.GONE
    }
}