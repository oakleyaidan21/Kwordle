package com.example.kwordle.model

class Kwordle() {

    var word: String = ""
    var letters = Array(26) { 0 }
    private var wordMap: MutableMap<Char, Int> = mutableMapOf()

    constructor (word: String) : this() {
        this.word = word
    }

    /**
     * Returns how a guess matches the word and modifies the letters array
     * based on the guess. Can probably be optimised but having to modify
     * the letters array as well adds some complexity
     *
     * @param guess the word to guess
     * @modifies letters, wordMap
     * @return an array that represents how the guess went
     */
    fun guessWord(guess: String): Guess {
        var result = Array(word.length) { 3 }
        setupWordMap()
        // first pass, get perfect hits
        for(i in word.indices) {
            val letterIndex = guess[i] - 'a'
            val wordMapAmount = wordMap[guess[i]]
            if(guess[i] === word[i]) {
                letters[letterIndex] = 1
                if(wordMapAmount !== null) wordMap[guess[i]] = wordMapAmount - 1
                result[i] = 1
            } else {
                result[i] = 3
                if(letters[letterIndex] !== 1) letters[letterIndex] = 3
            }
        }

        // second pass, get partial hits
        for(i in word.indices) {
            val letterIndex = guess[i] - 'a'
            val wordMapAmount = wordMap[guess[i]]
            if(wordMapAmount !== null && wordMapAmount !== 0 && guess[i] !== word[i]) {
                result[i] = 2
                if(letters[letterIndex] !== 1) letters[letterIndex] = 2
                wordMap[guess[i]] = wordMapAmount - 1
            }
        }
        return Guess(result, guess);
    }

    fun getValueForLetter(letter: Char): Int {
        return letters[letter - 'a']
    }

    private fun setupWordMap() {
        wordMap.clear()
        for(c in word) {
            var amount = wordMap[c]
            if(amount === null) {
                wordMap[c] = 1
            } else {
                wordMap[c] = amount + 1
            }
        }
    }

    private fun printWordMap() {
        for(c in word) {
            var amount = wordMap[c]
            println("$c: $amount")
        }
    }
}