package com.example.champlaincollegetriviaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.geoquiz.QuizViewModel

public const val QUIZ_FILE_PATH_KEY = "QuizFilePathKey"

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<Button>(R.id.button_buildings).setOnClickListener {
            //quizViewModel.loadQuizFromXMLFileStream(assets.open("questions_buildings.xml"))

            MainActivity.quizViewModel.currentCategory = "Buildings"
            MainActivity.quizViewModel.loadQuizFromXMLFileStream(assets.open("questions_buildings.xml"))
            MainActivity.quizViewModel.start()

            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}