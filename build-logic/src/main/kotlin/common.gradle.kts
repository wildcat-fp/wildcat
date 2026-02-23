import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  `java-library`
  id("static-analysis")
  id("testing")
}

val libs = the<LibrariesForLibs>()

group = providers.gradleProperty("GROUP").getOrElse("io.github.wildcat-fp")
version = providers.gradleProperty("VERSION_NAME").getOrElse("0.1.0-SNAPSHOT")

repositories {
    mavenCentral()
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// Configure Javadoc generation
tasks.withType<Javadoc> {
    // Specify the encoding to avoid warnings
    options.encoding = "UTF-8"

    (options as StandardJavadocDocletOptions).tags(
        "implSpec:a:Implementation Specification",
        "implNote:a:Implementation Note",
        "apiSpec:a:API Specification",
        "apiNote:a:API Note"
    )
}
