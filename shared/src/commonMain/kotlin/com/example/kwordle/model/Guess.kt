package com.example.kwordle.model

class Guess() {

    var values = ArrayList<Int>()
    var word: String = ""

    constructor(values: Array<Int>, word: String) : this() {
        this.values = values.toList() as ArrayList<Int>
        this.word = word
    }

    constructor(word: String, values: String) : this() {
        setFromStrings(word, values)
    }

    fun setFromStrings(word: String, values: String) {
        this.word = word
        for(s in values.chunked(1)) {
            this.values.add(s.toInt())
        }
    }

    override fun equals(other: Any?): Boolean {
        if(other is Guess) {
            if(other.word !== this.word) return false
            for(i in other.values.indices) {
                if(other.values[i] !== this.values[i]) {
                    return false
                }
            }
        } else {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun isCorrect(): Boolean {
        for(i in values) {
            if(i !== 1) {
                return false
            }
        }
        return true
    }

    override fun toString(): String {
        return "[$word,${values.joinToString()}]"
    }
}