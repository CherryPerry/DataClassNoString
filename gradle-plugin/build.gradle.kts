plugins {
    kotlin("jvm")
    kotlin("kapt")
    `java-gradle-plugin`
    `maven-publish`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("gradle-plugin-api"))
    implementation("com.google.auto.service:auto-service:1.0-rc7")
    kapt("com.google.auto.service:auto-service:1.0-rc7")
}

gradlePlugin {
    plugins {
        register("plugin") {
            id = "com.cherryperry.nostrings"
            implementationClass = "com.cherryperry.nostrings.DataClassNoStringPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "bintray"
            url = uri("https://api.bintray.com/maven/cherryperry/maven/DataClassNoString/;publish=1")
            credentials {
                username = project.findProperty("publish.user") as String?
                password = project.findProperty("publish.key") as String?
            }
        }
    }
}

tasks.named("jar", org.gradle.jvm.tasks.Jar::class.java) {
    manifest.attributes("Implementation-Version" to archiveVersion)
}
