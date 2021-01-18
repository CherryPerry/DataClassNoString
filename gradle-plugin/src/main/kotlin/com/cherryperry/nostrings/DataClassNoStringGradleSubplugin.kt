package com.cherryperry.nostrings

import com.google.auto.service.AutoService
import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@AutoService(KotlinGradleSubplugin::class)
class DataClassNoStringGradleSubplugin : KotlinGradleSubplugin<AbstractCompile> {

    private val version: String by lazy {
        this::class.java.`package`.implementationVersion
    }

    override fun isApplicable(project: Project, task: AbstractCompile): Boolean =
        project.plugins.hasPlugin(DataClassNoStringPlugin::class.java)

    override fun apply(
        project: Project,
        kotlinCompile: AbstractCompile,
        javaCompile: AbstractCompile?,
        variantData: Any?,
        androidProjectHandler: Any?,
        kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?
    ): List<SubpluginOption> {
        val extension =
            project
                .extensions
                .findByType(DataClassNoStringExtension::class.java)
                ?: DataClassNoStringExtension()

        val enabled = SubpluginOption("enabled", extension.enabled.toString())
        val removeAll = SubpluginOption("removeAll", extension.removeAll.toString())

        return listOf(enabled, removeAll)
    }

    override fun getCompilerPluginId(): String = "data-class-no-string"

    override fun getPluginArtifact(): SubpluginArtifact =
        SubpluginArtifact("com.cherryperry.nostrings", "kotlin-plugin", version)

}
