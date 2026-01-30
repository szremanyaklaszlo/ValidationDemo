plugins {
    kotlin("jvm") version "2.3.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.mockk:mockk:1.14.9")
}

kotlin {
    jvmToolchain(24)
}

tasks.test {
    useJUnitPlatform()
}