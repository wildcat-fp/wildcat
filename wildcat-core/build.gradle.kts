import org.checkerframework.gradle.plugin.CheckerFrameworkExtension

plugins {
    `java-library`
    id("org.checkerframework") version "0.6.45"
    id("com.diffplug.spotless") version "7.0.0.BETA3"
}

repositories {
    mavenCentral()
}

spotless {
    ratchetFrom("origin/main")
    
    val configDir = rootProject.layout.projectDirectory.dir("wildcat-core")
    val formatterConfigFile = configDir.file("wildcat-eclipse-formatter-settings.xml")

    java {
        eclipse().configFile(formatterConfigFile)

        removeUnusedImports()
    }
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
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

                implementation(project(":wildcat-laws"))
            }
        }
    }
}
