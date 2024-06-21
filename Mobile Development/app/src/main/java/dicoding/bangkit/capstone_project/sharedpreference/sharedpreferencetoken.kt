package com.dicoding.picodiploma.loginwithanimation.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences

class sharedpreferencetoken (context: Context) {
    private val sharedpreference :SharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
    private val editor :SharedPreferences.Editor = sharedpreference.edit()

    fun saveToken(Token : String, name : String, email : String){
        editor.putString("token", Token)
        editor.putString("username", name)
        editor.putString("email", email)
        editor.apply()
    }

    fun getToken() : String?{
        return sharedpreference.getString("token", null)
    }

    fun getUsername() : String?{
        return sharedpreference.getString("name", null)
    }

    fun clearData(){
        editor.remove("token")
        editor.remove("username")
        editor.remove("email")
        editor.apply()
    }
}
