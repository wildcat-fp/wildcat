import org.checkerframework.gradle.plugin.CheckerFrameworkExtension

plugins {
    `java-library`
    id("org.checkerframework") version "0.6.39"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configure<CheckerFrameworkExtension> {
    checkers = listOf(
        "org.checkerframework.checker.nullness.NullnessChecker"
    )
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
