package com.example.champlaincollegetriviaapp

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.Question
import com.bignerdranch.android.geoquiz.QuizViewModel
import java.io.File
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class QuestionActivity : AppCompatActivity() {

    lateinit var questionCounterTextView: TextView
    lateinit var questionTextView: TextView
    lateinit var choicesButtons: Array<Button>

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        questionCounterTextView = findViewById<TextView>(R.id.question_counter)
        questionTextView = findViewById<TextView>(R.id.question_title)
        choicesButtons = arrayOf<Button>(findViewById<Button>(R.id.choice_0), findViewById<Button>(R.id.choice_1), findViewById<Button>(R.id.choice_2), findViewById<Button>(R.id.choice_3))
        for (i in choicesButtons.indices) {
            choicesButtons[i].setOnClickListener { handleButtonClick(i) }
        }

        parseXMLQuestions("questions_buildings.xml")

        displayQuestion(quizViewModel.buildingQuiz.getCurrentQuestion())

        //val question: Question = Question("The correct answer is A", arrayOf<String> ("A", "B", "C", "D"), 0, "multipleChoice")

        //displayQuestion(question)
    }

    private fun parseXMLQuestions(filePath: String) {
        // XML Parser citation: https://www.javatpoint.com/kotlin-android-xmlpullparser-tutorial
        var question: Question? = null
        var text: String? = null

        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(assets.open(filePath), null)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (tagName.equals("question", ignoreCase = true)) {
                        // Create New Question
                        question = Question()
                        question.correctAnswer = parser.getAttributeValue(null, "correctID").toInt()
                    }
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if (tagName.equals("question", ignoreCase = true)) {
                        question?.let { quizViewModel.buildingQuiz.questions.add(question) }
                    } else if (tagName.equals("text", ignoreCase = true)) {
                        question!!.text = text!!
                    } else if (tagName.equals("choice", ignoreCase = true)) {
                        question!!.choices.add(text!!)
                    }
                    else -> {
                    }
                }

                eventType = parser.next()
            }
        } catch (e:XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun handleButtonClick(index: Int) {

    }

    private fun displayQuestion(question: Question) {
        var counterText: String = getString(R.string.question_counter)
        counterText = counterText.replace("@a", 0.toString())
        counterText = counterText.replace("@b", 10.toString())
        questionCounterTextView.text = counterText

        questionTextView.text = question.text;

        for (i in choicesButtons.indices) {
            choicesButtons[i].text = question.choices[i]
        }
    }
}