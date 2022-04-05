package com.example.challengechapter4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challengechapter4.databinding.FragmentWelcomeBinding

class welcomeFragment : Fragment() {

    lateinit var binding: FragmentWelcomeBinding

    val sharedPreferences = "sharedPreferences"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnWelcome.setOnClickListener {

            val welcomeScreen: SharedPreferences = requireActivity().getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
            val isFirstTime: Boolean = welcomeScreen.getBoolean("firstTime", true)

            if (isFirstTime){
                val editor: SharedPreferences.Editor = welcomeScreen.edit()
                editor.putBoolean("firstTime", false)
                editor.commit()
                findNavController().navigate(R.id.action_spalshFragment_to_welcomeFragment)
            } else {
                findNavController().navigate(R.id.action_spalshFragment_to_welcomeFragment)
            }
        }
    }
}