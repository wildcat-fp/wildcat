import org.checkerframework.gradle.plugin.CheckerFrameworkExtension

plugins {
    `java-library`
    id("org.checkerframework") version "0.6.45"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    implementation(libs.assertj)
    implementation(libs.jqwik)
    implementation(libs.junit.jupiter)
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

testing {
    suites {
        withType<JvmTestSuite> {
            useJUnitJupiter()

            dependencies {
                compileOnly(libs.lombok)
                annotationProcessor(libs.lombok)

                implementation(libs.assertj)
                implementation(libs.jqwik)
            }
        }
    }
}
