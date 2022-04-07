package com.example.challengechapter4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challengechapter4.databinding.FragmentSignInBinding
import com.example.challengechapter4.databinding.FragmentSignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class signUpFragment : Fragment() {

    private var myDB: QasbonDatabase?= null

    private var _binding: FragmentSignUpBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDB = QasbonDatabase.getInstance(requireContext())

        binding.btnSignup.setOnClickListener {
            if (binding.etInputUsernameSignup.text.isNullOrEmpty() &&
                    binding.etInputEmailSignup.text.isNullOrEmpty() &&
                    binding.etInputPasswordSignup.text.isNullOrEmpty() &&
                    binding.etConfirmPasswordSignup.text.isNullOrEmpty()) {
                Toast.makeText(context, "Form regitrasi belum terisi, harap isi terlebih dahulu", Toast.LENGTH_LONG).show()
            } else if (binding.etInputUsernameSignup.text.isNullOrEmpty()) {
                binding.etInputUsernameSignup.error = "Tentukan Username kamu terlebih dahulu"
            } else if (binding.etInputEmailSignup.text.isNullOrEmpty()) {
                binding.etInputEmailSignup.error = "Masukkan Email yang akan kamu pakai"
            } else if (binding.etInputPasswordSignup.text.isNullOrEmpty()) {
                binding.etInputPasswordSignup.error = "Tentukan Password kamu terlebih dahulu"
            } else if (binding.etConfirmPasswordSignup.text.isNullOrEmpty()) {
                binding.etConfirmPasswordSignup.error = "Konfirmasi Password kamu"
            } else {
                GlobalScope.async {
                    val result = myDB?.qasbonDao()?.signUp(
                        User(null,
                            binding.etInputUsernameSignup.text.toString(),
                            binding.etInputEmailSignup.text.toString(),
                            binding.etInputPasswordSignup.text.toString())
                    )
                    runBlocking(Dispatchers.Main) {
                        if (result != 0.toLong()){
                            Toast.makeText(activity, "Berhasil Sign Up", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                        } else {
                            Toast.makeText(activity, "Gagal Sign Up", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}