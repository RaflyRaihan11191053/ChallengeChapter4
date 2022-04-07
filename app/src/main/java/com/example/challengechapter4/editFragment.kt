package com.example.challengechapter4

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.challengechapter4.databinding.FragmentAddBinding
import com.example.challengechapter4.databinding.FragmentEditBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class editFragment() : DialogFragment() {
    lateinit var listCash: Cash
    constructor(listCash: Cash): this(){
        this.listCash = listCash
    }

    private var myDB: QasbonDatabase?= null

    private var _binding: FragmentEditBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        binding.etEditDate.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        myDB = QasbonDatabase.getInstance(requireContext())

        if (this::listCash.isInitialized) {
            binding.etEditDate.setText(listCash.date)
            binding.etEditCash.setText(listCash.cash.toString())
            binding.etEditIncome.setText(listCash.income.toString())
            binding.etEditOutcome.setText(listCash.outcome.toString())
        }

        binding.btnEdit.setOnClickListener {

            val objectCash = listCash
            objectCash.date = binding.etEditDate.text.toString()
            objectCash.cash = binding.etEditCash.text.toString().toInt()
            objectCash.income = binding.etEditIncome.text.toString().toInt()
            objectCash.outcome = binding.etEditOutcome.text.toString().toInt()

            GlobalScope.async {
                val result = myDB?.qasbonDao()?.updateCash(objectCash)
                runBlocking(Dispatchers.Main) {
                    if (result != 0) {
                        Toast.makeText(requireContext(), "Sukses mengubah data kas pada tanggal ${objectCash.date}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), "Gagal mengubah data kas pada tanggal${objectCash.date}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dialog?.dismiss()
        }
    }

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.CHINA)
        binding.etEditDate.setText(sdf.format(myCalendar.time))
    }

}