package com.example.challengechapter4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.HandlerCompat.postDelayed
import androidx.navigation.fragment.findNavController
import com.example.challengechapter4.databinding.FragmentSpalshBinding

class spalshFragment : Fragment() {

    lateinit var binding: FragmentSpalshBinding

    val sharedPreferences = "sharedPreferences"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpalshBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({

            val splashScreen: SharedPreferences = requireActivity().getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
            val isFirstTime: Boolean = splashScreen.getBoolean("firstTime", true)

            if (isFirstTime){
                val editor: SharedPreferences.Editor = splashScreen.edit()
                editor.putBoolean("firstTime", false)
                editor.commit()
                findNavController().navigate(R.id.action_spalshFragment_to_welcomeFragment)
            } else {
                findNavController().navigate(R.id.action_spalshFragment_to_welcomeFragment)
            }
        }, 5000)
    }
}