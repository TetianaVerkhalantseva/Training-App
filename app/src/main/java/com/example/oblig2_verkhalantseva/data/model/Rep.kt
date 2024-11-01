package com.example.oblig2_verkhalantseva.data.model

class Rep(
    val number: Int,
    var wasFinished: Boolean = false
) {
    override fun toString(): String =
        "wasFinished = $wasFinished, number = $number"
}