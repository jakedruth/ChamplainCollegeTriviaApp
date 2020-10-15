package com.example.champlaincollegetriviaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)

        val score = intent.getStringExtra(SCORE_KEY)
        if (score != null) {
            findViewById<TextView>(R.id.displayScoreTextView).text = "${getString(R.string.your_score)} $score"
        }
    }
}