package com.example.challengechapter4

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.challengechapter4.databinding.FragmentAddBinding
import com.example.challengechapter4.databinding.FragmentSignInBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class addFragment : DialogFragment() {

    private var myDB: QasbonDatabase?= null

    private var _binding: FragmentAddBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
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

        binding.etInputDate.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        myDB = QasbonDatabase.getInstance(requireContext())

        binding.btnAdd.setOnClickListener {
            val objectCash = Cash(
                null,
                binding.etInputDate.text.toString(),
                binding.etInputCash.text.toString().toInt(),
                binding.etInputIncome.text.toString().toInt(),
                binding.etInputOutcome.text.toString().toInt()
            )

            GlobalScope.async {
                val result = myDB?.qasbonDao()?.insertCash(objectCash)
                runBlocking(Dispatchers.Main) {
                    if (result != 0.toLong()) {
                        Toast.makeText(requireContext(), "Sukses menambahkan Kas pada tanggal ${objectCash.date}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), "Gagal menambahkan Kas pada tanggal ${objectCash.date}", Toast.LENGTH_LONG).show()
                    }
                    activity?.finish()
                }
            }
        }
    }

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.CHINA)
        binding.etInputDate.setText(sdf.format(myCalendar.time))
    }
}