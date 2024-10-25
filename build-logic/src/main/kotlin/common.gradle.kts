import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  `java-library`
  id("static-analysis")
}

val libs = the<LibrariesForLibs>()

repositories {
    mavenCentral()
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
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

