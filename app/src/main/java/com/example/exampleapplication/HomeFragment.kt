package com.example.exampleapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampleapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var myPreferences: SharedPreferences

    private val ll = ArrayList<ArrayList<String>>()

    private lateinit var listView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        myPreferences = requireContext().getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)

        listView = binding.listView

        ll.add(arrayListOf("Name 1", "455", "12"))
        ll.add(arrayListOf("Name 2", "753", "54"))
        ll.add(arrayListOf("Name 3", "235", "23"))
        ll.add(arrayListOf("Name 4", "562", "67"))

        val adapter = Adapter(ll)
        listView.layoutManager = LinearLayoutManager(requireContext())
        listView.adapter = adapter

        binding.btnLogout.setOnClickListener {
            val editor = myPreferences.edit()
            editor.putLong("loginId", -1)
            editor.apply()

            val action = HomeFragmentDirections.actionHomeFragmentToMainFragment()
            findNavController().navigate(action)
        }

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = "Home"

        return binding.root
    }
}