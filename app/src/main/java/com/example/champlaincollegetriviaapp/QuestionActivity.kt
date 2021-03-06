package com.example.champlaincollegetriviaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.ViewModelProviders

const val SCORE_KEY = "ScoreKey"

class QuestionActivity : AppCompatActivity() {

    lateinit var questionCounterTextView: TextView
    lateinit var questionTextView: TextView
    lateinit var choicesButtons: Array<Button>
    lateinit var choicesBorders: Array<LinearLayout>
    lateinit var cheatButton: Button
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        questionCounterTextView = findViewById(R.id.question_counter)
        questionTextView = findViewById(R.id.question_title)
        choicesButtons = arrayOf(findViewById(R.id.choice_0), findViewById(R.id.choice_1), findViewById(R.id.choice_2), findViewById(R.id.choice_3))
        choicesBorders = arrayOf(findViewById(R.id.choice_0_border), findViewById(R.id.choice_1_border), findViewById(R.id.choice_2_border), findViewById(R.id.choice_3_border))
        cheatButton = findViewById(R.id.cheat)
        nextButton = findViewById(R.id.nextQuestion)

        for (i: Int in choicesButtons.indices) {
            choicesButtons[i].setOnClickListener { handlePlayerSelection(i) }
        }

        cheatButton.setOnClickListener {
            cheat()
        }

        cheatButton.isEnabled = !MainActivity.quizViewModel.didCheat

        nextButton.setOnClickListener {
            handleNextButtonClick()
        }

        nextButton.isEnabled = false

        displayQuestion()
    }

    private fun handlePlayerSelection(index: Int) {

        val isCorrect:Boolean = MainActivity.quizViewModel.checkIfAnswerIsCorrect(index)
        for (button in choicesButtons) {
            button.isEnabled = false
        }
        nextButton.isEnabled = true

        if (isCorrect) {
            MainActivity.quizViewModel.increaseScore(10)
            choicesBorders[index].backgroundTintList = ContextCompat.getColorStateList(this, R.color.correct_answer)
        } else {
            MainActivity.quizViewModel.increaseScore(-5)
            choicesBorders[index].backgroundTintList = ContextCompat.getColorStateList(this, R.color.incorrect_answer)
            choicesBorders[MainActivity.quizViewModel.getCorrectAnswerID()].backgroundTintList = ContextCompat.getColorStateList(this, R.color.correct_answer)
        }
    }

    private fun cheat() {
        var index: Int = MainActivity.quizViewModel.cheatAndRemoveOneQuestion()
        choicesButtons[index].isEnabled = false
        cheatButton.isEnabled = false
        MainActivity.quizViewModel.increaseScore(-3)
    }

    private fun handleNextButtonClick() {

        for (i in choicesButtons.indices) {
            choicesButtons[i].isEnabled = true
            choicesBorders[i].backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
        }

        nextButton.isEnabled = false

        if (MainActivity.quizViewModel.isNextQuestion()) {
            MainActivity.quizViewModel.nextQuestion()
            displayQuestion()
        } else {
            val intent = Intent(this, ResultsActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun displayQuestion() {
        var question = MainActivity.quizViewModel.getCurrentQuestion()
        displayQuestion(question)
    }

    private fun displayQuestion(question: Question) {
        var counterText: String = getString(R.string.question_counter)
        counterText = counterText.replace("@a", (question.index + 1).toString())
        counterText = counterText.replace("@b", MainActivity.quizViewModel.getQuestionCount().toString())
        questionCounterTextView.text = counterText

        questionTextView.text = question.text;

        for (i in choicesButtons.indices) {
            choicesButtons[i].text = question.choices[i]
        }
    }
}