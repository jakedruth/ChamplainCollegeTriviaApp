package com.bignerdranch.android.geoquiz

import androidx.lifecycle.ViewModel
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    private var currentQuiz: Quiz = Quiz()
    private var currentQuestionIndex: Int = 0
    var score: Int = 0
    var didCheat: Boolean = false

    fun start() {
        currentQuestionIndex = 0
        score = 0
    }

    fun getQuestionCount(): Int {
        return currentQuiz.questions.size
    }

    fun getCurrentQuestion(): Question {
        return currentQuiz.questions[currentQuestionIndex]
    }

    fun getCorrectAnswerID(): Int {
        return getCurrentQuestion().correctAnswer
    }

    fun isNextQuestion(): Boolean {
        return currentQuestionIndex < getQuestionCount() - 1
    }

    fun nextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex >= currentQuiz.questions.size)
            currentQuestionIndex = currentQuiz.questions.size - 1
    }

    fun checkIfAnswerIsCorrect(index: Int): Boolean {
        val correct: Boolean = getCurrentQuestion().correctAnswer == index;
        return (getCurrentQuestion().correctAnswer == index)
    }

    fun increaseScore(value: Int) {
        score += value
    }

    fun cheatAndRemoveOneQuestion(): Int {
        val wrongAnswers: ArrayList<Int> = ArrayList()
        var current: Question = getCurrentQuestion()
        for (i: Int in current.choices.indices) {
            if (i != current.correctAnswer) {
                wrongAnswers.add(i)
            }
        }

        didCheat = true;

        return wrongAnswers.random()
    }

    fun loadQuizFromXMLFileStream(stream: InputStream) {
        // XML Parser citation: https://www.javatpoint.com/kotlin-android-xmlpullparser-tutorial
        currentQuiz = Quiz()
        var question: Question? = null
        var text: String? = null

        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(stream, null)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (tagName.equals("question", ignoreCase = true)) {
                        // Create New Question
                        question = Question()
                        question.correctAnswer = parser.getAttributeValue(null, "correctID").toInt()
                        question.index = currentQuiz.questions.size
                    }
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if (tagName.equals("question", ignoreCase = true)) {
                        question?.let { currentQuiz.questions.add(question) }
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
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

class Quiz {
    var questions: ArrayList<Question> = ArrayList()
}

class Question {
    var index: Int = 0
    var text: String = ""
    var correctAnswer: Int = 0
    var choices: ArrayList<String> = ArrayList()
}

//data class Question(val text: String, val choices: Array<String>, val answer: Int, val questionType: String) {
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as Question
//
//        if (text != other.text) return false
//        if (!choices.contentEquals(other.choices)) return false
//        if (answer != other.answer) return false
//        if (questionType != other.questionType) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = text.hashCode()
//        result = 31 * result + choices.contentHashCode()
//        result = 31 * result + answer
//        result = 31 * result + questionType.hashCode()
//        return result
//    }
//
//}