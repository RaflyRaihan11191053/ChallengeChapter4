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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class homeFragment : Fragment() {

    private lateinit var adapter: CashAdapter

    private var _binding: FragmentHomeBinding?= null
    private val binding get() = _binding!!

    val sharedPreferences = "sharedPreferences"

    private var myDB: QasbonDatabase?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CashAdapter()
        binding.rvCash.adapter = adapter
        fetchData()

        myDB = QasbonDatabase.getInstance(requireContext())

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

    fun fetchData() {

        GlobalScope.launch {
            val listCash = myDB?.qasbonDao()?.getAllCash()
            runBlocking(Dispatchers.Main) {
                if (listCash.isNullOrEmpty()){
                    binding.rvCash.visibility = View.GONE
                } else {
                    binding.rvCash.visibility = View.VISIBLE
                    binding.laEmpty.visibility = View.GONE
                }
                listCash?.let {
                    adapter.setData(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        QasbonDatabase.destroyInstance()
    }
}