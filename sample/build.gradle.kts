buildscript {
    repositories.maven("https://dl.bintray.com/cherryperry/maven")
    dependencies.classpath("com.cherryperry.nostrings:gradle-plugin:1.0.0")
}

plugins {
    kotlin("jvm")
    application
}

apply(plugin = "com.cherryperry.nostrings")

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("junit:junit:4.13")
}

application {
    mainClassName = "com.cherryperry.nostrings.AppKt"
}

// Check local build during development
/*tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xplugin=${project(":kotlin-plugin").buildDir}/libs/kotlin-plugin-$version.jar"
    }
    dependsOn(project(":kotlin-plugin").tasks.named("jar"))
}*/
