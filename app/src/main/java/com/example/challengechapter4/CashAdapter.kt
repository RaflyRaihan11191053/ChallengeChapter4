package com.example.challengechapter4

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.challengechapter4.databinding.LayoutCashBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CashAdapter(val listCash: List<Cash>): RecyclerView.Adapter<CashAdapter.ViewHolder>() {

    class ViewHolder(val binding: LayoutCashBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutCashBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCash.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvDate.text = listCash[position].date
            tvCash.text = listCash[position].cash.toString()
            tvIncome.text = listCash[position].income.toString()
            tvOutcome.text = listCash[position].outcome.toString()

            ibEdit.setOnClickListener {
                val intent = Intent(it.context, editFragment::class.java)
                intent.putExtra("cash", listCash[position])
                it.context.startActivity(intent)
            }

            ibDelete.setOnClickListener {
                AlertDialog.Builder(it.context).setPositiveButton("Ya") {
                        p0, p1 -> val mDb = QasbonDatabase.getInstance(holder.itemView.context)

                    GlobalScope.async {
                        val result = mDb?.qasbonDao()?.deleteCash(listCash[position])

                        (holder.itemView.context as homeFragment).run {
                            if (result != 0) {
                                Toast.makeText(it.context, "Data kas tanggal ${listCash[position].date} berhasil dihapus", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(it.context, "Data kas tanggal ${listCash[position].date} gagal dihapus", Toast.LENGTH_LONG).show()
                            }
                        }
                        (holder.itemView.context as homeFragment).fetchData()
                    }
                }.setNegativeButton("Tidak") {
                        p0, p1 -> p0.dismiss()
                }.setMessage("Apakah anda yakin ingin menghapus data kas pada tanggal ${listCash[position].date}").setTitle("Konfirmasi hapus").create().show()
            }
        }
    }

}