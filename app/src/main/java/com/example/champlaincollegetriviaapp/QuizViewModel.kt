package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    val category: String = ""
    val questionIndex: Int = 0


}

data class Question(val text: String, val choices: Array<String>, val answer: Int, val questionType: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (text != other.text) return false
        if (!choices.contentEquals(other.choices)) return false
        if (answer != other.answer) return false
        if (questionType != other.questionType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + choices.contentHashCode()
        result = 31 * result + answer
        result = 31 * result + questionType.hashCode()
        return result
    }

}