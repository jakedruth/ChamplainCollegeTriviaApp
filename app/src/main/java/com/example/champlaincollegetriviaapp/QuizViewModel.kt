package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    val buildingQuiz: Quiz = Quiz("Buildings")
}

class Quiz {
    var category: String = ""
    var questions: ArrayList<Question> = ArrayList()
    var currentQuestionIndex: Int = 0

    constructor(category: String) {
        this.category = category
    }

    public fun start() {
        currentQuestionIndex = 0
    }

    public fun getCurrentQuestion(): Question {
        return questions[currentQuestionIndex]
    }

    public fun nextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex >= questions.size)
            currentQuestionIndex = questions.size - 1
    }
}

class Question {
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