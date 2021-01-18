import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.20" apply false
}

allprojects {

    group = "com.cherryperry.nostrings"
    version = "1.3.0"

    repositories {
        jcenter()
        maven("https://dl.bintray.com/cherryperry/maven")
    }

}

subprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            apiVersion = "1.3"
            languageVersion = "1.3"
        }
    }
}
