package com.example.thebooklibrary.util

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thebooklibrary.App

fun Fragment.daggerAppComponent() = (requireActivity().application as App).appComponent

/*fun Fragment.setEditTextError(textInputLayout: TextInputLayout, errorCode: Int) {
    textInputLayout.apply {
        when (errorCode) {
            NO_ERROR -> {
                applyError("")
            }
            ERR_EMPTY_FIELD -> {
                applyError(getString(R.string.error_empty_field))
            }
            ERR_AGE_TOO_HIGH -> {
//                applyError(getString(R.string.error_age_too_high))
            }
            ERR_AGE_ZERO -> {
//                applyError(getString(R.string.error_age_positive))
            }
            ERR_PASS_NOT_EQ -> {
//                applyError(getString(R.string.error_pass_not_eq))
            }
            ERR_REG -> {
//                applyError(getString(R.string.error_reg))
            }
            ERR_USER_NOT_EXIST -> {
//                applyError(getString(R.string.error_user_not_exist))
            }
        }
    }
}

fun TextInputLayout.applyError(errorText: String) {
    if (errorText.isNotEmpty()) {
        isErrorEnabled = true
        error = errorText
    } else {
        isErrorEnabled = false
    }
}*/

fun Fragment.toast(text: String, isLong: Boolean = false) {
    Toast.makeText(context, text, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}