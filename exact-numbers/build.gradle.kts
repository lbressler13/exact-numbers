plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.5.10"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0" // ktlint
}

group = "xyz.lbres"
version = "0.1.1" // TODO major version change, due to change in visibility of num/denom

val githubUsername: String? = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
val githubPassword: String? = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")

repositories {
    mavenCentral()

    // kotlin-utils
    maven {
        url = uri("https://maven.pkg.github.com/lbressler13/kotlin-utils")
        credentials {
            username = githubUsername
            password = githubPassword
        }
    }
}

dependencies {
    val kotlinUtilsVersion = "1.3.0"
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
                username = githubUsername
                password = githubPassword
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
