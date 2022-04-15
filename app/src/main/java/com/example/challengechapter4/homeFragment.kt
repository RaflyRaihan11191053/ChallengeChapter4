package com.example.challengechapter4

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.challengechapter4.databinding.FragmentHomeBinding
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

    var qasbonRepository: QasbonRepository?= null

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
        qasbonRepository = QasbonRepository(requireContext())

        val homeScreen: SharedPreferences = requireActivity().getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE)

        binding.tvUsername.text = "Halo, ${homeScreen.getString("username", "Admin")}!"

        binding.tvLogout.setOnClickListener {
            val alert = AlertDialog.Builder(requireContext())
            alert.apply{
                setTitle("Logout")
                setMessage("Apakah anda yakin ingin log out?")
                setNegativeButton("Batal"){dialog,which->
                    dialog.dismiss()
                }
                setPositiveButton("Logout"){dialog,which->
                    dialog.dismiss()

                    homeScreen.edit().clear().apply()
                    findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
                }
            }
            alert.show()
        }

        binding.ibAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

    }

    fun fetchData() {
        GlobalScope.launch {
            val listCash = qasbonRepository?.getAllCash()
            runBlocking(Dispatchers.Main) {
                listCash.let {
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