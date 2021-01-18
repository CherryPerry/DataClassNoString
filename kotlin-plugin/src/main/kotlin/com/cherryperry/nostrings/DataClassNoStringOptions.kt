package com.cherryperry.nostrings

import org.jetbrains.kotlin.config.CompilerConfigurationKey

object DataClassNoStringOptions {
    val KEY_ENABLED = CompilerConfigurationKey.create<Boolean>("enabled")
    val KEY_REMOVE_ALL = CompilerConfigurationKey.create<Boolean>("removeAll")
}
