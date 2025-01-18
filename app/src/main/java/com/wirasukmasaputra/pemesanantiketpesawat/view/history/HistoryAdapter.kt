package com.wirasukmasaputra.pemesanantiketpesawat.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wirasukmasaputra.pemesanantiketpesawat.databinding.ListItemHistoryBinding
import com.wirasukmasaputra.pemesanantiketpesawat.model.ModelDatabase
import com.wirasukmasaputra.pemesanantiketpesawat.utils.FunctionHelper.rupiahFormat

class HistoryAdapter(private var modelDatabase: MutableList<ModelDatabase>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    fun setDataAdapter(items: List<ModelDatabase>) {
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelDatabase[position]

        with(holder.binding) {
            when (data.keberangkatan) {
                "Jakarta" -> tvKode1.text = "JKT"
                "Semarang" -> tvKode1.text = "SRG"
                "Surabaya" -> tvKode1.text = "SUB"
                "Bali" -> tvKode1.text = "DPS"
            }

            when (data.tujuan) {
                "Jakarta" -> tvKode2.text = "JKT"
                "Semarang" -> tvKode2.text = "SRG"
                "Surabaya" -> tvKode2.text = "SUB"
                "Bali" -> tvKode2.text = "DPS"
            }

            tvKelas.text = data.kelas
            tvDate.text = data.tanggal
            tvNama.text = data.namaPenumpang
            tvKeberangkatan.text = data.keberangkatan
            tvTujuan.text = data.tujuan
            tvHargaTiket.text = rupiahFormat(data.hargaTiket)
        }
    }

    override fun getItemCount(): Int = modelDatabase.size

    inner class ViewHolder(val binding: ListItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    fun setSwipeRemove(position: Int): ModelDatabase {
        return modelDatabase.removeAt(position).also {
            notifyItemRemoved(position)
        }
    }
}
