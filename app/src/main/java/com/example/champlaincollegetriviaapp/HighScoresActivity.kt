package com.example.champlaincollegetriviaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class HighScoresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)

        findViewById<Button>(R.id.highscore_ok_button).setOnClickListener { finish() }

        var displayString: String = ""

        displayString = displayString.plus(addCategory(getString(R.string.category_buildings)))
        displayString = displayString.plus(addCategory(getString(R.string.category_faculty)))
        displayString = displayString.plus(addCategory(getString(R.string.category_burlington)))

        findViewById<TextView>(R.id.highscores_textView).text = displayString
    }

    fun addCategory(category: String): String {
        var result: String = "\n$category:\n"
        val scores: MutableList<String>? = MainActivity.saveDataHandler.getRawScores(category)
        if (scores != null) {
            scores.forEach {
                result = result.plus("\t\t$it\n")
            }
        } else {
            result = result.plus("\t\tNo Entries\n")
        }
        return result
    }
}