package com.cherryperry.nostrings

import org.jetbrains.kotlin.config.CompilerConfigurationKey

object DataClassNoStringOptions {
    val KEY_ENABLED = CompilerConfigurationKey.create<Boolean>("enabled")
}
