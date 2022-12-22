plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.5.10"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0" // ktlint
}

group = "xyz.lbres"
version = "1.0.0"

repositories {
    mavenCentral()

    // kotlin-utils
    maven {
        url = uri("https://maven.pkg.github.com/lbressler13/kotlin-utils")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    val kotlinUtilsVersion = "0.4.0"
    val mockkVersion = "1.12.4"

    implementation(kotlin("stdlib"))
    // TODO fix gh import
    implementation(files("libs/kotlin-utils-0.4.0-local.jar"))
    // implementation("xyz.lbres:kotlin-utils:$kotlinUtilsVersion")

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
