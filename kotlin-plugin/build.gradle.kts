plugins {
    kotlin("jvm")
    kotlin("kapt")
    `maven-publish`
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("compiler-embeddable"))
    implementation("com.google.auto.service:auto-service:1.0-rc7")
    kapt("com.google.auto.service:auto-service:1.0-rc7")
}

publishing {
    publications {
        register("maven", MavenPublication::class.java) {
            from(components["java"])
        }
    }
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
