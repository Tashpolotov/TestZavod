package com.example.testzavod.utils

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


private val usernamePattern = Pattern.compile("^[A-Za-z0-9-_]+$")

fun validateUsername(usernameEditText: TextInputEditText, usernameLayout: TextInputLayout): Boolean {
    return when {
        usernameEditText.text.toString().trim().isEmpty() -> {
            usernameLayout.error = "Имя пользователя не должно быть пустым"
            false
        }
        !usernamePattern.matcher(usernameEditText.text.toString().trim()).matches() -> {
            usernameLayout.error = "Имя пользователя должно содержать только заглавные и строчные латинские буквы, цифры и символы -_"
            false
        }
        else -> {
            usernameLayout.error = null
            true
        }
    }
}

fun getTextWatcherForUsername(usernameEditText: TextInputEditText, usernameLayout: TextInputLayout): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            validateUsername(usernameEditText, usernameLayout)
        }
    }
}

