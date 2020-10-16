package com.example.champlaincollegetriviaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    companion object QuizData {
        lateinit var quizViewModel: QuizViewModel
        lateinit var saveDataHandler: SaveDataHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)
        saveDataHandler = SaveDataHandler(this)

        findViewById<Button>(R.id.StartPlaying).setOnClickListener { goToMainMenu() }
        findViewById<Button>(R.id.ViewHighScores).setOnClickListener { goToHighScore() }
    }

    private fun goToMainMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    private fun goToHighScore() {
        val intent = Intent(this, HighScoresActivity::class.java)
        startActivity(intent);

//
    }
}

class SaveDataHandler(private var context: Context) {

    fun getRawScores(category: String): MutableList<String>? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(category, Context.MODE_PRIVATE)
        val scoreRawString: String? = sharedPreferences.getString("scores", null)
        if (scoreRawString == null || scoreRawString == "")
            return null

        return scoreRawString.split(",").toMutableList()
    }

    private fun convertRawScoresToPairs(rawScores: MutableList<String>): MutableList<Pair<String, Int>> {
        var pairs: MutableList<Pair<String, Int>> = mutableListOf()
        rawScores.forEach {
            val split = it.split(" ")
            pairs.add(Pair(split[0], split[1].toInt()))
        }

        return pairs
    }

    private fun convertPairsToRawScores(pairs: MutableList<Pair<String, Int>>): MutableList<String> {

        var rawScores: MutableList<String> = mutableListOf()
        pairs.forEach {
            val raw = "${it.first} ${it.second}"
            rawScores.add(raw)
        }

        return rawScores
    }

    private fun setRawScores(category: String, rawScores: MutableList<String>) {

        val sharedPreferences: SharedPreferences = context.getSharedPreferences(category, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()

        var stringSet: String = ""

        for(i: Int in rawScores.indices) {
            stringSet = stringSet.plus(rawScores[i])
            if (i != rawScores.size - 1) {
                stringSet = stringSet.plus(",")
            }
        }

        editor.putString("scores", stringSet)
        editor.apply()
    }

    fun getHighScore(category: String): Int {
        val scores: MutableList<String>? = getRawScores(category)
        if (scores != null && scores.size > 0) {
            val split: List<String> = scores.elementAt(0).split(" ")
            return split[1].toInt()
        }

        return 0
    }

    fun submitHighScore(category: String, name: String, score: Int) {

        val newPair: Pair<String, Int> = Pair(name, score)
        val rawScores: MutableList<String>? = getRawScores(category)
        var pairs: MutableList<Pair<String, Int>> = mutableListOf()
        if (rawScores != null && rawScores.size > 0) {
            pairs = convertRawScoresToPairs(rawScores)

            var addedPair: Boolean = false
            for (i: Int in rawScores.indices) {
                if (newPair.second > pairs.elementAt(i).second) {
                    pairs.add(i, newPair)
                    addedPair = true
                    break;
                }
            }

            if (!addedPair) {
                pairs.add(newPair)
            }


        } else {
            pairs.add(newPair)
        }

        while (pairs.size > 3) {
            pairs.removeAt(pairs.size - 1)
        }

        val rawSet = convertPairsToRawScores(pairs)

        setRawScores(category, rawSet)
    }

    fun clearHighScores(category: String) {
        setRawScores(category, mutableListOf())
    }
}