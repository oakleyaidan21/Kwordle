package com.example.kwordle.controller

import com.example.kwordle.model.Guess
import com.example.kwordle.model.Kwordle
import com.example.kwordle.util.KotlinObservable
import com.example.kwordle.util.KotlinObserver
import kotlin.collections.ArrayList


class KwordleController : KotlinObservable {

    override val observers: ArrayList<KotlinObserver> = ArrayList()

    private var allWords = setOf("smell", "stink", "spies", "smite", "kills", "cling", "horse", "lords")
    var kwordle = Kwordle(allWords.random())
    var currentGuess = ""
    var guesses = ArrayList<Guess>()
    var gameOver = false
    var win = false

    constructor()

    constructor(dictionary: Set<String>, word: String) {
        allWords = dictionary
        kwordle = Kwordle(word)
    }

    constructor (word: String) {
        kwordle = Kwordle(word)
    }

    constructor(dictionary: Set<String>) {
        allWords = dictionary
        kwordle = Kwordle(allWords.random())
    }


    /**
     * Adds a letter to the current guess
     *
     * @param letter the letter to add
     * @modifies currentGuess
     * @returns whether the letter was added
     */
    fun addLetterToGuess(letter: Char) : Boolean {
        if(!actionCanBePerformed() || currentGuess.length === 5) {
            return false
        }
        currentGuess += letter
        notifyObservers()
        return true
    }

    /**
     * Removes a letter from the current guess
     *
     * @modifies currentGuess
     * @returns whether the letter was removed
     */
    fun removeLetterFromGuess() : Boolean {
        if(!actionCanBePerformed() || currentGuess.length === 0) {
            return false
        }
        currentGuess = currentGuess.dropLast(1)
        notifyObservers()
        return true
    }

    /**
     * Submits the current guess
     *
     * @returns true if the guess was submitted, false if it wasn't
     */
    fun submitGuess(): Boolean {
        if(!actionCanBePerformed()) {
            return false
        } else if(currentGuess in allWords) {
            guesses.add(kwordle.guessWord(currentGuess))
            currentGuess = ""
            checkForWinOrGameOver()
            notifyObservers()
            return true;
        }
        return false
    }

    /**
     * Get the current state of the guessed letters
     *
     * @returns the letter map
     */
    fun getLetterMap() : Array<Int> {
        return kwordle.letters
    }

    /**
     * Resets the game with a new word
     *
     * @modifies kwordle
     * @modifies currentGuess
     * @modifies guesses
     * @modifies gameOver
     * @modifies win
     */
    fun reset() {
        kwordle = Kwordle(allWords.random())
        currentGuess = ""
        guesses = ArrayList<Guess>()
        gameOver = false
        win = false
        notifyObservers()
    }

    fun getResultsString() : String {
        var ret = "Kwordle ${guesses.size}/6\n"
        for(g in guesses) {
            ret += g.toEmojiString() + "\n"
        }
        return ret;
    }

    /**
     * Checks whether an action can be performed in the UI
     *
     * @returns true if an action can be performed, false otherwise
     */
    private fun actionCanBePerformed(): Boolean {
        if(win || gameOver || guesses.size === 6) {
            return false
        }
        return true
    }

    /**
     * Modifies the state if the player has won or
     * if the player has lost
     *
     * @modifies win, gameOver
     */
    private fun checkForWinOrGameOver() {
        if(guesses.last().isCorrect()) {
            win = true
            gameOver = true
        } else if(guesses.size === 6) {
            win = false
            gameOver = true
        }
    }




}