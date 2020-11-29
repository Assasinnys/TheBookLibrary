package com.example.thebooklibrary.util

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.thebooklibrary.App
import kotlinx.coroutines.Dispatchers

fun Fragment.daggerAppComponent() = (requireActivity().application as App).appComponent

fun Fragment.toast(text: String, isLong: Boolean = false) {
    if (text.isEmpty()) return

    Toast.makeText(context, text, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(textRes: Int, isLong: Boolean = false) {
    if (textRes <= 0) return

    Toast.makeText(context, textRes, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun <T, L> responseLiveData(
    roomQuery: () -> LiveData<T>,
    networkRequest: suspend () -> ResultData<L>,
    roomSaveQuery: suspend (L) -> Unit
) = liveData(Dispatchers.IO) {

    val source = roomQuery().map { ResultData.success(it) }
    emitSource(source)

    when (val response = networkRequest()) {
        is ResultData.Success -> {
            roomSaveQuery(response.value)
        }

        is ResultData.Failure -> {
            emit(ResultData.failure(response.message))
            emitSource(source)
        }
    }
}