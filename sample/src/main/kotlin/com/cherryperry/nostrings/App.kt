package com.cherryperry.nostrings

data class Sample(val text: String)

fun main() {
    println("Sample.toString() should return class name and hash code: ${Sample("text")}")
}
