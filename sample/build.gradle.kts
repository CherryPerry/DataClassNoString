buildscript {
    repositories.maven("https://dl.bintray.com/cherryperry/maven")
    // Use published version
    // dependencies.classpath("com.cherryperry.nostrings:gradle-plugin:${parent?.version}")
}

plugins {
    kotlin("jvm")
    application
}

// Use published version
// apply(plugin = "com.cherryperry.nostrings")

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("junit:junit:4.13")
}

application {
    mainClassName = "com.cherryperry.nostrings.AppKt"
}

// Use local build
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xplugin=${project(":kotlin-plugin").buildDir}/libs/kotlin-plugin-$version.jar"
    }
    dependsOn(project(":kotlin-plugin").tasks.named("jar"))
}
