import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  `java-library`
  id("static-analysis")
  id("testing")
}

val libs = the<LibrariesForLibs>()

repositories {
    mavenCentral()
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

