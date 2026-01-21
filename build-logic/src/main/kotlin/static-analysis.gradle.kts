import org.checkerframework.gradle.plugin.CheckerFrameworkExtension
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("java")
    
    id("org.checkerframework")
    id("net.ltgt.errorprone")
    id("com.github.spotbugs")
}

val libs = the<LibrariesForLibs>()

configure<CheckerFrameworkExtension> {
    checkers = listOf(
        "org.checkerframework.checker.nullness.NullnessChecker"
    )
}

dependencies {
    compileOnly(libs.spotbugs.annotations)

    errorprone(libs.errorprone.core)
    checkerFramework(libs.checkerframework.checker)
}

spotbugs {
  excludeFilter = rootProject.file("config/spotbugs-exclude-filter.xml")
}