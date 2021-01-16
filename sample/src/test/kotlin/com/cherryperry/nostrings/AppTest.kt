package com.cherryperry.nostrings

import org.junit.Assert.assertEquals
import org.junit.Test

class AppTest {

    @Test
    fun `toString method should return empty string`() {
        assertEquals("", Sample("test").toString())
    }

}
