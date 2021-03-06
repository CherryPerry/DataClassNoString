package com.cherryperry.nostrings

import com.cherryperry.nostrings.DataClassNoStringOptions.KEY_ENABLED
import com.cherryperry.nostrings.DataClassNoStringOptions.KEY_REMOVE_ALL
import com.google.auto.service.AutoService
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

@AutoService(ComponentRegistrar::class)
class DataClassNoStringComponentRegistrar : ComponentRegistrar {

    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {
        if (!configuration.get(KEY_ENABLED, true)) {
            return
        }
        val removeAll = configuration.get(KEY_REMOVE_ALL, true)
        ClassBuilderInterceptorExtension.registerExtension(
            project = project,
            extension = DataClassNoStringClassGenerationInterceptor(removeAll)
        )
    }

}
