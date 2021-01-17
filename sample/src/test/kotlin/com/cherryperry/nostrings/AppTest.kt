package com.cherryperry.nostrings

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class AppTest {

    @Test
    fun `toString method should return empty string`() {
        assertEquals("", Sample("test").toString())
    }

    @Test
    fun `hashCode method should return 0`() {
        assertEquals(0, Sample("test").hashCode())
    }

    @Test
    fun `equals method should return always false`() {
        val sample = Sample("test")
        assertFalse(sample == sample)
    }

}
