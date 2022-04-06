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
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import androidx.navigation.fragment.findNavController
import com.example.challengechapter4.databinding.FragmentSpalshBinding

class spalshFragment : Fragment() {

    private var _binding: FragmentSpalshBinding?= null
    private val binding get() = _binding!!

    val sharedPreferences = "sharedPreferences"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpalshBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({

            val splashScreen: SharedPreferences = requireActivity().getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)
            val isFirstTime: Boolean = splashScreen.getBoolean("firstTime", true)
            val username = splashScreen.getString("username", "Admin")

            if (isFirstTime){
                findNavController().navigate(R.id.action_spalshFragment_to_welcomeFragment)
                val editor: SharedPreferences.Editor = splashScreen.edit()
                editor.putBoolean("firstTime", false)
                editor.apply()
            } else {
                if (username != "Admin") {
                    findNavController().navigate(R.id.action_spalshFragment_to_homeFragment)
                } else {
                    findNavController().navigate(R.id.action_spalshFragment_to_signInFragment)
                }
            }
        }, 5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}