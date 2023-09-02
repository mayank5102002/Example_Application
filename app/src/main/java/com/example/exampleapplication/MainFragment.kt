package com.example.exampleapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.findNavController
import com.example.exampleapplication.databinding.ActivityMainBinding
import com.example.exampleapplication.viewmodels.LoginVM

class MainFragment : Fragment() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel : LoginVM by activityViewModels()

    private lateinit var myPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityMainBinding.inflate(layoutInflater)

        myPreferences = requireContext().getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)
        checkIfAlreadyLoggedIn()

        viewModel.init(requireContext())

        val usernameEditText : EditText = binding.editTextusername
        val passwordEditText : EditText = binding.editTextpassword
        val loginButton : Button = binding.buttonlogin
        val registerButton : Button = binding.buttonRegister

        viewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.loginId.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_LONG).show()

            val editor = myPreferences.edit()
            editor.putLong("loginId", it)
            editor.apply()

            val action = MainFragmentDirections.actionMainFragmentToHomeFragment()
            requireView().findNavController().navigate(action)
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()

            if(username.isEmpty()){
                Toast.makeText(requireContext(), "Username is empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val password = passwordEditText.text.toString()

            if(password.isEmpty()){
                Toast.makeText(requireContext(), "Password is empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.login(username, password)
        }

        registerButton.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToRegisterFragment()
            requireView().findNavController().navigate(action)
        }

        return binding.root
    }

    private fun checkIfAlreadyLoggedIn(){
        val loginId = myPreferences.getLong("loginId", -1)

        if(loginId != -1L){
            val action = MainFragmentDirections.actionMainFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        viewModel.onDestroy()
    }
}