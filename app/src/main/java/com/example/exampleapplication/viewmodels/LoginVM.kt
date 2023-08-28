package com.example.exampleapplication.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exampleapplication.Dao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginVM : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val loginId : MutableLiveData<Long> = MutableLiveData<Long>()

    val error : MutableLiveData<String> = MutableLiveData<String>()

    fun init(context : Context){
        Dao.init(context)
    }

    fun login(username : String, password : String){
        coroutineScope.launch {
            val userId = Dao.login(username, password)
            if(userId != null){
                loginId.postValue(userId!!)
            } else {
                error.postValue("Invalid username or password")
            }
        }
    }

    fun onDestroy(){
        Dao.closeDb()
    }

}