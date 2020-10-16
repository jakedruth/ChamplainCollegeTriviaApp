package com.example.champlaincollegetriviaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders

public const val QUIZ_FILE_PATH_KEY = "QuizFilePathKey"

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<Button>(R.id.button_buildings).setOnClickListener {
            MainActivity.quizViewModel.currentCategory = getString(R.string.category_buildings)
            MainActivity.quizViewModel.loadQuizFromXMLFileStream(assets.open("questions_buildings.xml"))
            MainActivity.quizViewModel.start()

            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_faculty).setOnClickListener {
            MainActivity.quizViewModel.currentCategory = getString(R.string.category_faculty)
            MainActivity.quizViewModel.loadQuizFromXMLFileStream(assets.open("questions_faculty.xml"))
            MainActivity.quizViewModel.start()

            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_burlington).setOnClickListener {
            MainActivity.quizViewModel.currentCategory = getString(R.string.category_burlington)
            MainActivity.quizViewModel.loadQuizFromXMLFileStream(assets.open("questions_burlington.xml"))
            MainActivity.quizViewModel.start()

            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}