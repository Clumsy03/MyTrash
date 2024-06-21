package com.dicoding.picodiploma.loginwithanimation.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class myedittext @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {
    var isEmailValid = false
    var isPasswordValid = false
    var isUsernameValid = false

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                when (inputType) {
                    password -> {
                        isPasswordValid = input.length >= 8
                        (parent.parent as? TextInputLayout)?.error = if (!isPasswordValid) {
                            "Password tidak boleh kurang dari 8 karakter"
                        } else {
                            null
                        }
                    }

                    email -> {
                        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(input).matches()
                        (parent.parent as? TextInputLayout)?.error = if (!isEmailValid) {
                            "Masukkan email dengan benar"
                        } else {
                            null
                        }
                    }

                    username -> {
                        isUsernameValid = input.length >= 5
                        (parent.parent as? TextInputLayout)?.error = if (!isUsernameValid) {
                            "Username tidak boleh kurang dari 5 karakter"
                        } else {
                            null
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    companion object {
        const val password = 0x00000081
        const val email = 0x00000021
        const val username = 0x00000001
    }
}