package com.cherryperry.nostrings

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class AppTest {

    @Test
    fun `toString method should return default string`() {
        val sample = Sample("test")
        assertEquals(
            "${sample.javaClass.name}@${Integer.toHexString(System.identityHashCode(sample))}",
            sample.toString()
        )
    }

    @Test
    fun `hashCode method should return identityHashCode`() {
        val sample = Sample("test")
        assertEquals(System.identityHashCode(sample), sample.hashCode())
    }

    @Test
    fun `equals method should return true only for itself`() {
        val sample = Sample("test")
        assertEquals(sample, sample)
        assertNotEquals(Sample("test"), sample)
    }

}
