package com.app.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showCustomSnackBar(view: View) {
        CustomSnackbar.make(
            clContainer,
            R.string.title1,
            R.string.action1,
            Snackbar.LENGTH_LONG
        ).show()
    }

}
