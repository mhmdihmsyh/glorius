package com.example.gloryrescuepetapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.*

class CariHewanTerdekat : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HewanAdapter
    private val daftarHewan = mutableListOf<HewanModel>()

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 100
        private const val TAG = "CariHewanTerdekat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carihewanterdekat)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        recyclerView = findViewById(R.id.recyclerHewanTerdekat)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HewanAdapter(daftarHewan)
        recyclerView.adapter = adapter

        val btnLokasi = findViewById<Button>(R.id.btnGunakanLokasi)
        btnLokasi.setOnClickListener {
            ambilLokasiPengguna()
        }
    }

    private fun ambilLokasiPengguna() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            return
        }

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGpsEnabled) {
            Toast.makeText(this, "GPS tidak aktif. Aktifkan terlebih dahulu.", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { lokasi: Location? ->
                if (lokasi != null) {
                    Log.d(TAG, "Lokasi ditemukan: ${lokasi.latitude}, ${lokasi.longitude}")
                    cariHewanTerdekat(lokasi.latitude, lokasi.longitude)
                } else {
                    Toast.makeText(this, "Lokasi tidak ditemukan. Coba lagi.", Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "Lokasi null.")
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal mengambil lokasi", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Gagal ambil lokasi", it)
            }
    }

    private fun cariHewanTerdekat(latUser: Double, lonUser: Double) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("hewan")
            .get()
            .addOnSuccessListener { snapshot ->
                daftarHewan.clear()

                Log.d(TAG, "Jumlah dokumen Firestore: ${snapshot.size()}")

                if (snapshot.isEmpty) {
                    Toast.makeText(this, "Tidak ada data hewan ditemukan di Firestore.", Toast.LENGTH_LONG).show()
                    return@addOnSuccessListener
                }

                for (doc in snapshot) {
                    val id = doc.id
                    val nama = doc.getString("nama") ?: "Tanpa Nama"
                    val jenis = doc.getString("jenis") ?: ""
                    val deskripsi = doc.getString("deskripsi") ?: ""
                    val fotoUrl = doc.getString("fotoUrl") ?: ""
                    val status = doc.getString("status") ?: "tersedia"

                    val lat = doc.getDouble("latitude") ?: continue
                    val lon = doc.getDouble("longitude") ?: continue
                    val jarak = hitungJarak(latUser, lonUser, lat, lon)

                    Log.d(TAG, "Hewan: $nama, Lat: $lat, Lon: $lon, Jarak: ${"%.2f".format(jarak)} km")

                    if (jarak <= 10.0) {
                        val hewan = HewanModel(
                            id = id,
                            nama = nama,
                            jenis = jenis,
                            deskripsi = deskripsi,
                            fotoUrl = fotoUrl,
                            latitude = lat,
                            longitude = lon,
                            jarak = jarak,
                            status = status
                        )
                        daftarHewan.add(hewan)
                    }
                }

                if (daftarHewan.isEmpty()) {
                    Toast.makeText(this, "Tidak ada hewan dalam radius 10 km.", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Hewan dalam jarak <= 10km: 0")
                } else {
                    Log.d(TAG, "Hewan dalam jarak <= 10km: ${daftarHewan.size}")
                }

                daftarHewan.sortBy { it.jarak }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal memuat data hewan", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Firestore gagal ambil data", it)
            }
    }

    private fun hitungJarak(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            ambilLokasiPengguna()
        } else {
            Toast.makeText(this, "Izin lokasi diperlukan", Toast.LENGTH_SHORT).show()
        }
    }
}
