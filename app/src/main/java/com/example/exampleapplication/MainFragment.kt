package com.example.exampleapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.exampleapplication.databinding.ActivityMainBinding
import com.example.exampleapplication.viewmodels.LoginVM

class MainFragment : Fragment() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel : LoginVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityMainBinding.inflate(layoutInflater)

        val usernameEditText : EditText = binding.editTextusername
        val passwordEditText : EditText = binding.editTextpassword
        val loginButton : Button = binding.buttonlogin
        val registerButton : Button = binding.buttonRegister

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

            val toast : Toast = Toast.makeText(requireContext(), "Username: $username, Password: $password", Toast.LENGTH_LONG)
            toast.show()
        }

        registerButton.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToRegisterFragment()
            requireView().findNavController().navigate(action)
        }

        return binding.root
    }
}