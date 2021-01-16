package com.cherryperry.nostrings

import org.gradle.api.Plugin
import org.gradle.api.Project

class DataClassNoStringPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(EXTENSION_NAME, DataClassNoStringExtension::class.java)
    }

    companion object {
        const val EXTENSION_NAME = "dataClassNoString"
    }

}
