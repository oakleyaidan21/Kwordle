package com.example.kwordle.util

/**
 * The observer pattern because I've yet to
 * learn how to use Observer delegates yet
 */
interface KotlinObservable {

    val observers: ArrayList<KotlinObserver>

    fun addObserver(observer: KotlinObserver) {
        observers.add(observer)
    }

    fun remove(observer: KotlinObserver) {
        observers.remove(observer)
    }

    fun notifyObservers() {
        observers.forEach { it.update() }
    }
}