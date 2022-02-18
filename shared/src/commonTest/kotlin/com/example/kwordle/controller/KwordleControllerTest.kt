package com.example.kwordle.controller

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertEquals

class KwordleControllerTest {

    @Test
    fun testAddingLetters() {
        val cont = KwordleController("smell")
        assertTrue{cont.addLetterToGuess('s')}
        assertTrue{cont.addLetterToGuess('m')}
        assertTrue{cont.addLetterToGuess('e')}
        assertTrue{cont.addLetterToGuess('l')}
        assertTrue{cont.addLetterToGuess('l')}
        assertFalse{cont.addLetterToGuess('r')}
    }

    @Test
    fun testRemovingLetters() {
        val cont = KwordleController("smell")
        assertTrue(cont.addLetterToGuess('s'))
        assertTrue(cont.addLetterToGuess('m'))
        assertTrue(cont.addLetterToGuess('e'))
        assertTrue(cont.addLetterToGuess('l'))
        assertTrue(cont.addLetterToGuess('l'))
        assertTrue(cont.removeLetterFromGuess())
        assertEquals(cont.currentGuess, "smel")
        assertTrue(cont.removeLetterFromGuess())
        assertTrue(cont.removeLetterFromGuess())
        assertTrue(cont.removeLetterFromGuess())
        assertTrue(cont.removeLetterFromGuess())
        assertFalse(cont.removeLetterFromGuess())
    }

    @Test
    fun testSubmitCorrectGuess() {
        val cont = KwordleController("smell")
        assertTrue(cont.addLetterToGuess('s'))
        assertTrue(cont.addLetterToGuess('m'))
        assertTrue(cont.addLetterToGuess('e'))
        assertTrue(cont.addLetterToGuess('l'))
        assertTrue(cont.addLetterToGuess('l'))
        assertTrue(cont.submitGuess())
        assertTrue(cont.currentGuess.isEmpty())
        assertTrue(cont.win)
        assertTrue(cont.gameOver)
    }

    @Test
    fun testSubmitIncorrectGuess() {
        val cont = KwordleController("smell")
        assertTrue(cont.addLetterToGuess('s'))
        assertTrue(cont.addLetterToGuess('m'))
        assertTrue(cont.addLetterToGuess('i'))
        assertTrue(cont.addLetterToGuess('t'))
        assertTrue(cont.addLetterToGuess('e'))
        assertTrue(cont.submitGuess())
        assertTrue(cont.currentGuess.length === 0)
        assertFalse(cont.win)
        assertFalse(cont.gameOver)
    }

    @Test
    fun testSubmitWordNotInDictionary() {
        val cont = KwordleController("smell")
        assertTrue(cont.addLetterToGuess('s'))
        assertTrue(cont.addLetterToGuess('m'))
        assertTrue(cont.addLetterToGuess('o'))
        assertTrue(cont.addLetterToGuess('t'))
        assertTrue(cont.addLetterToGuess('e'))
        assertFalse(cont.submitGuess())
        assertTrue(cont.currentGuess.length === 5)
        assertFalse(cont.win)
        assertFalse(cont.gameOver)
    }

    @Test
    fun testGameOver() {
        val cont = KwordleController("smell")
        submitGuessHelper("smite", cont);
        submitGuessHelper("kills", cont);
        submitGuessHelper("horse", cont);
        submitGuessHelper("spies", cont);
        submitGuessHelper("stink", cont);
        submitGuessHelper("lords", cont);
        assertTrue(cont.gameOver)
        assertFalse(cont.win)
    }

    private fun submitGuessHelper(guess: String, cont: KwordleController) {
        for(c in guess) {
            cont.addLetterToGuess(c)
        }
        cont.submitGuess()
    }
}