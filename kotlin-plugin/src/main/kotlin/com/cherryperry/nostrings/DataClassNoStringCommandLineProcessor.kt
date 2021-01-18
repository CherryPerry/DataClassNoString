package com.cherryperry.nostrings

import com.cherryperry.nostrings.DataClassNoStringOptions.KEY_ENABLED
import com.cherryperry.nostrings.DataClassNoStringOptions.KEY_REMOVE_ALL
import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(CommandLineProcessor::class)
class DataClassNoStringCommandLineProcessor : CommandLineProcessor {

    override val pluginId: String = "data-class-no-string"

    override val pluginOptions: Collection<AbstractCliOption> =
        listOf(
            CliOption(
                KEY_ENABLED.toString(),
                "<true|false>",
                "Whether plugin is enabled",
                required = false
            ),
            CliOption(
                KEY_REMOVE_ALL.toString(),
                "<true|false>",
                "Whether plugin should remove also equals() and hashCode() implementations",
                required = false
            )
        )

    override fun processOption(
        option: AbstractCliOption,
        value: String,
        configuration: CompilerConfiguration
    ) {
        when (option.optionName) {
            KEY_ENABLED.toString() -> configuration.put(KEY_ENABLED, value.toBoolean())
            KEY_REMOVE_ALL.toString() -> configuration.put(KEY_REMOVE_ALL, value.toBoolean())
            else -> error("Unexpected config option ${option.optionName}")
        }
    }

}
