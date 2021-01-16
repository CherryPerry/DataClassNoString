package com.cherryperry.nostrings

data class Sample(val text: String)

fun main() {
    println("Sample.toString() should return empty string: ${Sample("text")}")
}
