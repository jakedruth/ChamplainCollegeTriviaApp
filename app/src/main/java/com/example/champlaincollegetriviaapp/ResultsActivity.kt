package com.example.champlaincollegetriviaapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)

        val scoreText = getString(R.string.your_score) + " " + MainActivity.quizViewModel.score.toString()
        findViewById<TextView>(R.id.displayScoreTextView).text = scoreText
    }
}