plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.5.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0" // ktlint
}

group = "xyz.lbres"
version = "0.1.0"

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
    val kotlinUtilsVersion = "0.3.1"
    val mockkVersion = "1.12.4"

    implementation(kotlin("stdlib"))
    implementation("xyz.lbres:kotlin-utils:$kotlinUtilsVersion")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:$mockkVersion")
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/lbressler13/exact-numbers")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
