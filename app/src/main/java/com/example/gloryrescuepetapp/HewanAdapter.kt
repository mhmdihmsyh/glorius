package com.example.gloryrescuepetapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HewanAdapter(private val daftarHewan: List<HewanModel>) :
    RecyclerView.Adapter<HewanAdapter.HewanViewHolder>() {

    inner class HewanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNama: TextView = itemView.findViewById(R.id.txtNamaHewan)
        val txtJarak: TextView = itemView.findViewById(R.id.txtJarakHewan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HewanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hewan_terdekat, parent, false)
        return HewanViewHolder(view)
    }

    override fun onBindViewHolder(holder: HewanViewHolder, position: Int) {
        val hewan = daftarHewan[position]
        holder.txtNama.text = hewan.nama
        holder.txtJarak.text = String.format("%.2f km", hewan.jarak)
    }

    override fun getItemCount(): Int = daftarHewan.size
}
