package com.example.testzavod.utils

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import com.bumptech.glide.Glide

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(image: String) {
    Glide.with(this).load(image).into(this)
}

fun EditText.addTextChange(yesButton: Button, noButton: Button) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s.isNullOrEmpty()) {
                yesButton.visibility = View.INVISIBLE
                noButton.visibility = View.VISIBLE
            } else {
                yesButton.visibility = View.VISIBLE
                noButton.visibility = View.INVISIBLE
            }
        }
    })
}
