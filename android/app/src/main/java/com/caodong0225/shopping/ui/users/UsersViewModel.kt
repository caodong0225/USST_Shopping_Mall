package com.caodong0225.shopping.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsersViewModel :ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is user Fragment"
    }
    val text: LiveData<String> = _text
}