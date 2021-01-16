plugins {
    kotlin("jvm")
    kotlin("kapt")
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.12.0"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin-api"))
    implementation("com.google.auto.service:auto-service:1.0-rc7")
    kapt("com.google.auto.service:auto-service:1.0-rc7")
}

pluginBundle {
    website = "https://github.com/CherryPerry/DataClassNoString/"
    vcsUrl = "https://github.com/CherryPerry/DataClassNoString.git"
    tags = listOf("kotlin", "data class", "toString")
}

gradlePlugin {
    plugins {
        create("plugin") {
            id = "com.cherryperry.nostrings"
            displayName = "DataClassNoString Gradle plugin"
            description = "Remove toString() implementation from Kotlin Data classes"
            implementationClass = "com.cherryperry.nostrings.DataClassNoStringPlugin"
        }
    }
}
