package com.appkitchen.cornucopia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            startActivity(
                Intent(this, SwipeActivity::class.java)
            )
        }
    }
}