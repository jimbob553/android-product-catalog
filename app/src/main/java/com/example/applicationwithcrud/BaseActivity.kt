package com.example.applicationwithcrud

import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class BaseActivity : AppCompatActivity() {

    protected fun ensureRepoLoaded() {

        ProductRepository.load(this)
    }

    protected fun formatDate(ms: Long): String {
        val sdf = SimpleDateFormat("M/d/yyyy h:mm a", Locale.US)
        return sdf.format(Date(ms))
    }
}
