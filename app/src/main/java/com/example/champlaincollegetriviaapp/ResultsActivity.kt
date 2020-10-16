package com.example.champlaincollegetriviaapp

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class ResultsActivity : AppCompatActivity() {

    //@RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)

        val name: String = "My Name"

        val scoreText =
            "${getString(R.string.your_score)}: ${MainActivity.quizViewModel.score.toString()}\n" +
                    "Submitting scores with name $name"
        findViewById<TextView>(R.id.displayScoreTextView).text = scoreText

        MainActivity.saveDataHandler.submitHighScore(MainActivity.quizViewModel.currentCategory, name, MainActivity.quizViewModel.score)
    }
}