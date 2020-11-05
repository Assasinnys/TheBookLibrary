package com.example.thebooklibrary.util

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun setErrorText(inputLayout: TextInputLayout, errorId: Int) {
    inputLayout.apply {
        error = context.resources.getString(errorId)
    }
}