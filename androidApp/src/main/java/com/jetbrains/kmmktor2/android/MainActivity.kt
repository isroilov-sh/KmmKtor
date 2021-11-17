package com.jetbrains.kmmktor2.android

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jetbrains.kmmktor2.Greeting
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    private val greeting = Greeting()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        val btnGetIP: Button = findViewById(R.id.btnGetIP)

        btnGetIP.setOnClickListener {
            tv.text = "Loading..."
            mainScope.launch {
                kotlin.runCatching {
                    greeting.greeting()
                }.onSuccess {
                    tv.text = it
                }.onFailure {
                    tv.text = it.localizedMessage
                }
            }
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

}
