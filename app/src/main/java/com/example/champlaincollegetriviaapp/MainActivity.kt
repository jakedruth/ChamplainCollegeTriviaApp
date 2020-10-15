package com.example.champlaincollegetriviaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.StartPlaying)
        button.setOnClickListener { goToMainMenu() }
    }

    private fun goToMainMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    private fun goToHighScore() {
//        val intent = Intent(this, HighScoreActivity::class.java)
//        startActivity(intent);
    }
}