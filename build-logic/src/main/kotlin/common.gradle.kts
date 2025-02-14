import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  `java-library`
  id("static-analysis")
  id("testing")
  id("publishing")
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

// Configure Javadoc generation
tasks.withType<Javadoc> {
    // Specify the encoding to avoid warnings
    options.encoding = "UTF-8"
}

// Configure source jar generation
val sourcesJar = tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(project.the<SourceSetContainer>()["main"].allSource)
}

// Configure javadoc jar generation
val javadocJar = tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

// Assemble depends on sourcesJar and javadocJar.
tasks.assemble.get().dependsOn(sourcesJar, javadocJar)

// Publish depends on sourcesJar and javadocJar
tasks.getByName("publish").dependsOn(sourcesJar, javadocJar)