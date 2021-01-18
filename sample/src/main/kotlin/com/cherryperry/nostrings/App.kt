package com.cherryperry.nostrings

data class Sample(val text: String) : SampleInterface {
    override fun someMethod() = Unit
}

sealed class SampleSealed {
    data class Data(val text: String) : SampleSealed(), SampleInterface {
        override fun someMethod() = Unit
    }

    object Obj : SampleSealed()
}

interface SampleInterface {
    fun someMethod()
}

fun main() {
    println("Sample.toString() should return empty string: ${Sample("text")}")
}
