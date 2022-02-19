package com.example.kwordle.android.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StyleableRes
import androidx.core.content.ContextCompat
import com.example.kwordle.android.R
import com.example.kwordle.model.Guess


class GuessRow(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    @StyleableRes
    var index0 = 0
    @SuppressLint("ResourceType")
    @StyleableRes
    var index1 = 1

    var letterBoxes = mutableListOf<TextView>()
    var guess = Guess()
    private var attrs: AttributeSet = attrs

    init {
        init()
    }

    private fun init() {
        inflate(context, R.layout.guess_row, this)
        for(i in 1 until 6) {
            letterBoxes.add(findViewWithTag<TextView>("letter_$i"))
        }

        val sets = intArrayOf(R.attr.guessValue, R.attr.guessWord)
        val typedArray = context.obtainStyledAttributes(attrs, sets)
        val value = typedArray.getString(index0)
        val word = typedArray.getString(index1)
        typedArray.recycle()
        if(value != null && word != null) {
            setUIFromGuess(Guess(word, value))
        }
    }

    fun setUIFromGuess(newGuess: Guess) {
        this.guess = newGuess
        for(i in letterBoxes.indices) {
            letterBoxes[i].text = (guess.word[i].uppercase())
            letterBoxes[i].setBackgroundResource(R.drawable.back_full)
            when(guess.values[i]) {
                1 -> letterBoxes[i].setBackgroundColor(ContextCompat.getColor(context, R.color.colorCorrect))
                2 -> letterBoxes[i].setBackgroundColor(ContextCompat.getColor(context, R.color.colorPartial))
                3 -> letterBoxes[i].setBackgroundColor(ContextCompat.getColor(context, R.color.colorMiss))
                0 -> letterBoxes[i].setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
            letterBoxes[i].setTextColor(ContextCompat.getColor(context, if(guess.values[i] == 0) R.color.black else R.color.white))
        }
    }

    fun setUIFromPartialGuess(partial : String) {
        for (i in letterBoxes.indices) {
            letterBoxes[i].text = if(i < partial.length) partial[i].uppercase() else ""
            letterBoxes[i].setBackgroundResource(if(i < partial.length) R.drawable.back_full else R.drawable.back_empty)
        }
    }

    fun clear() {
        for(box in letterBoxes) {
            box.setBackgroundResource(R.drawable.back_empty)
            box.text = ""
            box.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

}