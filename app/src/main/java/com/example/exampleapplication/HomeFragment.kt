package com.example.exampleapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.exampleapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var myPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        myPreferences = requireContext().getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)

        binding.btnLogout.setOnClickListener {
            val editor = myPreferences.edit()
            editor.putLong("loginId", -1)
            editor.apply()

            val action = HomeFragmentDirections.actionHomeFragmentToMainFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}