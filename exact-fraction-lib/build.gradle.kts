plugins {
    `java-library`
    kotlin("jvm") version "1.5.10"
}

group = "org.example"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}
