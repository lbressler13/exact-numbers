plugins {
    `java-library`
    kotlin("jvm") version "1.5.10"
}

group = "org.example"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    val kotlinUtilsVersion = "0.0.2"

    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    implementation(files("libs/kotlin-utils-$kotlinUtilsVersion.jar"))
}
