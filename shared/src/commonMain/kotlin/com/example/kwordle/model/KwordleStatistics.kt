package com.example.kwordle.model

class KwordleStatistics {
    var gameResults = IntArray(6) { 0 }

    fun addResult(guessNumber: Int) {
        gameResults[guessNumber - 1]++
    }

    fun getRatios() : DoubleArray {
        var ratios = DoubleArray(6) { 0.0 }
        val max = gameResults.maxOrNull() ?: 0
        for(i in gameResults.indices) {
            ratios[i] = gameResults[i] / max.toDouble()
        }
        return ratios;
    }
}