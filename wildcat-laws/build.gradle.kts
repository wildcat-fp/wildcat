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

    api(project(":wildcat-core"))
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

tasks.withType<Test> {
    testLogging {
        // Log standard output and error streams
        showStandardStreams = true 

        // Log events for each test method
        events("passed", "skipped", "failed") 

        // Log exceptions and their stack traces
        showExceptions = true
        showStackTraces = true

        // Log the cause of exceptions
        showCauses = true

        // Set the minimum granularity of events to log (higher = more detailed)
        minGranularity = 2 
    }
}
