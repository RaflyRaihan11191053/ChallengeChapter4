package com.example.challengechapter4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.challengechapter4.databinding.FragmentSignInBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class signInFragment : Fragment() {

    private var myDB: QasbonDatabase?= null

    private var _binding: FragmentSignInBinding?= null
    private val binding get() = _binding!!

    val sharedPreferences = "sharedPreferences"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInScreen: SharedPreferences = requireActivity().getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)

        myDB = QasbonDatabase.getInstance(requireContext())

        binding.btnSignin.setOnClickListener {
            GlobalScope.async {
                val result = myDB?.qasbonDao()?.signIn(binding.etInputUsernameSignin.text.toString(), binding.etInputPasswordSignin.text.toString())
                runBlocking(Dispatchers.Main) {
                    if (result == false){
                        Toast.makeText(context, "Sign In gagal", Toast.LENGTH_SHORT).show()
                    } else {
                        val editor: SharedPreferences.Editor = signInScreen.edit()
                        editor.putString("username", binding.etInputUsernameSignin.text.toString())
                        editor.apply()
                        Toast.makeText(context, "Sign In Berhasil", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                    }
                }
            }
        }

        binding.tvSigninSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}