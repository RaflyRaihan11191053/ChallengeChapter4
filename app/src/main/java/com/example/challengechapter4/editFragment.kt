package com.example.challengechapter4

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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class editFragment : DialogFragment() {

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

        myDB = QasbonDatabase.getInstance(requireContext())

        val objectCash = arguments?.getParcelable<Cash>("cash")

    }

}