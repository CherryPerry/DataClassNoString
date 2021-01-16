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
        create("maven", MavenPublication::class.java) {
            from(components["java"])
            artifactId = "kotlin-plugin"
        }
    }
}
