plugins {
    `java-library`
    kotlin("jvm") version "1.5.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0" // ktlint
}

group = "org.example"
version = "0.0.8"

repositories {
    mavenCentral()

    maven {
        url = uri("https://maven.pkg.github.com/lbressler13/kotlin-utils")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("xyz.lbres:kotlin-utils:0.1.0")
    testImplementation(kotlin("test"))
}
