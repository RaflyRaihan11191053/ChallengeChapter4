package com.example.challengechapter4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.challengechapter4.databinding.FragmentHomeBinding
import com.example.challengechapter4.databinding.FragmentSignInBinding

class homeFragment : Fragment() {

    private var _binding: FragmentHomeBinding?= null
    private val binding get() = _binding!!

    val sharedPreferences = "sharedPreferences"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeScreen: SharedPreferences = requireActivity().getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)

        binding.tvUsername.text = "Halo, ${homeScreen.getString("username", "Admin")}!"

        binding.tvLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = homeScreen.edit()
            editor.clear()
            editor.apply()
            findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
        }

        binding.ibAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
    }
}