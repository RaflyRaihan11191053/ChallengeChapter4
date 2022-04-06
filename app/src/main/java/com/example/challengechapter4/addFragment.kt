package com.example.challengechapter4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.challengechapter4.databinding.FragmentAddBinding
import com.example.challengechapter4.databinding.FragmentSignInBinding

class addFragment : DialogFragment() {

    private var _binding: FragmentAddBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }
}