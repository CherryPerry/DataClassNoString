import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("junit:junit:4.13")
}

application {
    mainClassName = "com.cherryperry.nostrings.AppKt"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xplugin=${project(":kotlin-plugin").buildDir}/libs/kotlin-plugin-$version.jar"
    }
    dependsOn(project(":kotlin-plugin").tasks.named("jar"))
}
