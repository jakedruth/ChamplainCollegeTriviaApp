package com.example.champlaincollegetriviaapp

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.Question

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val question: Question = Question("The correct answer is A", arrayOf<String> ("A", "B", "C", "D"), 0, "multipleChoice")

        var counterText = getString(R.string.question_counter)
        counterText = counterText.replace("@a", 0.toString())
        counterText = counterText.replace("@b", 10.toString())
        findViewById<TextView>(R.id.question_counter).text = counterText

        findViewById<TextView>(R.id.question_title).text = question.text;

        findViewById<Button>(R.id.choice_0).text = question.choices[0];
        findViewById<Button>(R.id.choice_1).text = question.choices[1];
        findViewById<Button>(R.id.choice_2).text = question.choices[2];
        findViewById<Button>(R.id.choice_3).text = question.choices[3];
    }
}