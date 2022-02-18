package com.example.kwordle.model

import kotlin.test.Test
import kotlin.test.assertEquals


class KwordleTest {

    @Test
    fun testCorrectGuess() {
        val kwordle = Kwordle("guess")
        assertEquals(Guess(arrayOf(1,1,1,1,1), "guess"), kwordle.guessWord("guess"))
        for(c in "guess") {
            assertEquals(1, kwordle.getValueForLetter(c))
        }
    }

    @Test
    fun testIncorrectGuess() {
        val kwordle = Kwordle("guess")
        assertEquals( Guess(arrayOf(1,3,2,3,1), "gouts"), kwordle.guessWord("gouts"))
    }


    @Test
    fun testMultiLetterGuess() {
        val kwordle = Kwordle("skill")
        assertEquals( Guess(arrayOf(1,2,2,1,3), "silly"), kwordle.guessWord("silly"))
        assertEquals(1, kwordle.getValueForLetter('l'))
    }

    @Test
    fun testMultiLetterGuessWithCompleteMissAndPartialMiss() {
        val kwordle = Kwordle("sniff")
        assertEquals( Guess(arrayOf(2,2,3,3,3), "fffck"), kwordle.guessWord("fffck"))
        assertEquals(2, kwordle.getValueForLetter('f'))
    }

    @Test
    fun testMultiLetterGuessWithCompleteMissAndHit() {
        val kwordle = Kwordle("sniff")
        assertEquals( Guess(arrayOf(2,3,3,3,1), "ffckf"), kwordle.guessWord("ffckf"))
        assertEquals(1, kwordle.getValueForLetter('f'))
    }

    @Test
    fun testMultipleGuesses() {
        val kwordle = Kwordle("skill")
        assertEquals(Guess(arrayOf(3,3,3,2,3), "crust"), kwordle.guessWord("crust"))
        assertEquals(Guess(arrayOf(3,3,3,3,2), "moans"), kwordle.guessWord("moans"))
        assertEquals(Guess(arrayOf(1,2,2,1,3), "silly"), kwordle.guessWord("silly"))
        assertEquals(Guess(arrayOf(1,3,1,1,1), "spill"), kwordle.guessWord("spill"))
        assertEquals(Guess(arrayOf(1,1,1,1,1), "skill"), kwordle.guessWord("skill"))
        for(c in "skill") {
            assertEquals(1, kwordle.getValueForLetter(c))
        }
    }

    // Past this point are tests to test my guesses in the actual wordle that day

    @Test
    fun testElder() {
        val kwordle = Kwordle("elder")
        assertEquals(Guess(arrayOf(3,2,3,3,2), "crane"), kwordle.guessWord("crane"));
        assertEquals(Guess(arrayOf(2,2,2,2,3), "reeds"), kwordle.guessWord("reeds"));
        assertEquals(Guess(arrayOf(3,2,2,1,2), "breed"), kwordle.guessWord("breed"));
        assertEquals(Guess(arrayOf(2,2,3,1,1), "deter"), kwordle.guessWord("deter"));
        assertEquals(Guess(arrayOf(1,1,1,1,1), "elder"), kwordle.guessWord("elder"));
    }

    @Test
    fun testHumor() {
        val kwordle = Kwordle("humor")
        assertEquals(Guess(arrayOf(3,2,3,3,3), "crane"), kwordle.guessWord("crane"));
        assertEquals(Guess(arrayOf(3,3,3,3,1), "riper"), kwordle.guessWord("riper"));
        assertEquals(Guess(arrayOf(3,3,2,2,1), "stour"), kwordle.guessWord("stour"));
        assertEquals(Guess(arrayOf(3,3,2,2,1), "flour"), kwordle.guessWord("flour"));
        assertEquals(Guess(arrayOf(2,1,3,2,3), "ought"), kwordle.guessWord("ought"));
        assertEquals(Guess(arrayOf(1,1,1,1,1), "humor"), kwordle.guessWord("humor"));
        for(i in 0 until kwordle.letters.size) {
            val v = kwordle.letters[i]
            when(i) {
                'r' - 'a' -> assertEquals(1, v)
                'u' - 'a' -> assertEquals(1, v)
                'h' - 'a' -> assertEquals(1, v)
                'm' - 'a' -> assertEquals(1, v)
                'o' - 'a' -> assertEquals(1, v)
            }
        }
    }

    @Test
    fun testMashy() {
        val kwordle = Kwordle("mashy")
        assertEquals(Guess(arrayOf(3,3,2,3,3), "crane"), kwordle.guessWord("crane"));
        assertEquals(Guess(arrayOf(3,1,2,3,2), "lamps"), kwordle.guessWord("lamps"));
        assertEquals(Guess(arrayOf(3,1,1,3,3), "pasts"), kwordle.guessWord("pasts"));
        assertEquals(Guess(arrayOf(1,1,1,3,3), "masks"), kwordle.guessWord("masks"));
        assertEquals(Guess(arrayOf(1,3,1,3,1), "musty"), kwordle.guessWord("musty"));
        assertEquals(Guess(arrayOf(1,1,1,1,1), "mashy"), kwordle.guessWord("mashy"));
    }
}