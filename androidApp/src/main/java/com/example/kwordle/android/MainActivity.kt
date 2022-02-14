package com.example.kwordle.android

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.kwordle.android.components.GuessRow
import com.example.kwordle.controller.KwordleController
import com.example.kwordle.android.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener, Observer {
    var controller: KwordleController = KwordleController()
    var keyboardLetterButtons = mutableMapOf<Char, Button>()
    var guessRows = mutableListOf<GuessRow>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDictionary()
        controller.addObserver(this)
        Log.d("DICT:", controller.kwordle.word)
        setupKeyboard()
        setupRows()
        showTutorial()
    }

    private fun showTutorial() {

        val dialogBuilder = AlertDialog.Builder(this)
        var inflater = this.layoutInflater
        dialogBuilder.setView(inflater.inflate(R.layout.tutorial_dialog, null))
        dialogBuilder.create().show()
    }

    private fun setupDictionary() {
        val wordSet = mutableSetOf<String>()
        val inputStream = resources.openRawResource(R.raw.words)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line = bufferedReader.readLine()
        while(line !== null) {
            wordSet.add(line)
            line = bufferedReader.readLine()
        }
        controller = KwordleController(wordSet)
    }

    private fun setupKeyboard() {
        for(l in 'a'..'z') {
            if(findViewById<LinearLayout>(R.id.keyboard_row_1).findViewWithTag<Button>("keyboard_$l") != null) {
                val b = findViewById<LinearLayout>(R.id.keyboard_row_1).findViewWithTag<Button>("keyboard_$l")
                b.setOnClickListener(this)
                keyboardLetterButtons[l] = b
            }
            if(findViewById<LinearLayout>(R.id.keyboard_row_2).findViewWithTag<Button>("keyboard_$l") != null) {
                val b = findViewById<LinearLayout>(R.id.keyboard_row_2).findViewWithTag<Button>("keyboard_$l")
                b.setOnClickListener(this)
                keyboardLetterButtons[l] = b
            }
            if(findViewById<LinearLayout>(R.id.keyboard_row_3).findViewWithTag<Button>("keyboard_$l") != null) {
                val b = findViewById<LinearLayout>(R.id.keyboard_row_3).findViewWithTag<Button>("keyboard_$l")
                b.setOnClickListener(this)
                keyboardLetterButtons[l] = b
            }
        }
        findViewById<Button>(R.id.keyboard_enter).setOnClickListener(this)
        findViewById<ImageButton>(R.id.keyboard_back).setOnClickListener(this)
    }

    private fun setupRows() {
        guessRows.add(findViewById<GuessRow>(R.id.guess_row_1))
        guessRows.add(findViewById<GuessRow>(R.id.guess_row_2))
        guessRows.add(findViewById<GuessRow>(R.id.guess_row_3))
        guessRows.add(findViewById<GuessRow>(R.id.guess_row_4))
        guessRows.add(findViewById<GuessRow>(R.id.guess_row_5))
        guessRows.add(findViewById<GuessRow>(R.id.guess_row_6))
    }

    override fun onClick(v: View?) {
        val letterRegex = """keyboard_.""".toRegex()

        val tag: String = v?.getTag() as String
        if(letterRegex.matches(tag)) {
            controller.addLetterToGuess(tag.last())
        } else if(v.id === R.id.keyboard_enter) {
            controller.submitGuess()
        } else if(v.id === R.id.keyboard_back) {
            controller.removeLetterFromGuess()
        }
    }

    override fun update(p0: Observable?, p1: Any?) {

        val letters = controller.kwordle.letters
        for(pair in keyboardLetterButtons) {
            val b = pair.value
            b.setTextColor(ContextCompat.getColor(this, R.color.white))
            when(letters[pair.key - 'a']) {
                1 -> b.background.setTint(ContextCompat.getColor(this, R.color.colorCorrect))
                2 -> b.background.setTint(ContextCompat.getColor(this, R.color.colorPartial))
                3 -> b.background.setTint(ContextCompat.getColor(this, R.color.colorMiss))
                0 -> b.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }

        // fill in guesses
        for(i in controller.guesses.indices) {
            val guessRow = guessRows[i]
            guessRow.setUIFromGuess(controller.guesses[i])
        }

        // fill in current guess
        if(controller.guesses.size < 6) {
            guessRows[controller.guesses.size].setUIFromPartialGuess(controller.currentGuess)
        }

        if(controller.gameOver) {
            Log.d("DICT:", controller.kwordle.word)
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder
                .setMessage(if(controller.win) "You won! Play again?" else "Too bad! The word was '${controller.kwordle.word}'. Play again?")
                .setPositiveButton("Yes!", DialogInterface.OnClickListener {
                        _, _ -> reset()
                })
                .setNegativeButton("Nah", DialogInterface.OnClickListener {
                        dialog, _ -> dialog.cancel()
                })

            val alert = dialogBuilder.create()
            alert.setTitle("")
            alert.show()
        }
    }

    private fun reset() {
        controller.reset()
        // reset guess board
        for(row in guessRows) {
            row.clear()
        }

        // reset keyboard
        for(pair in keyboardLetterButtons) {
            val b = pair.value
            b.setTextColor(ContextCompat.getColor(this, R.color.black))
            b.background.setTint(ContextCompat.getColor(this, R.color.buttonDefault))
        }
    }
}