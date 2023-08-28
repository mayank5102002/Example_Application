package com.example.exampleapplication.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exampleapplication.Dao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterVM : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val error : MutableLiveData<String> = MutableLiveData<String>()

    fun registerUser(firstname: String, lastname: String, email: String, password: String) {
        coroutineScope.launch {

            if(Dao.userExists(email)) {
                //Error
                error.postValue("Email already exists")
            } else {
                Dao.registerUser(firstname, lastname, email, password)
            }

        }
    }

}