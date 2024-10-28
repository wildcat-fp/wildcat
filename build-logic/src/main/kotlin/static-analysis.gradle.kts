import org.checkerframework.gradle.plugin.CheckerFrameworkExtension
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("java")
    
    id("org.checkerframework")
    id("net.ltgt.errorprone")
    id("com.github.spotbugs")
    id("com.diffplug.spotless")
}

val libs = the<LibrariesForLibs>()

configure<CheckerFrameworkExtension> {
    checkers = listOf(
        "org.checkerframework.checker.nullness.NullnessChecker"
    )
}

spotless {
    ratchetFrom("origin/main")
    
    val formatterConfigFile = rootProject.file("config/wildcat-eclipse-formatter-settings.xml")

    java {
        importOrder()
        removeUnusedImports()

        eclipse().configFile(formatterConfigFile)

        formatAnnotations()
    }
}

dependencies {
    compileOnly(libs.spotbugs.annotations)

    errorprone(libs.errorprone.core)
    checkerFramework(libs.checkerframework.checker)
}

spotbugs {
  excludeFilter = rootProject.file("config/spotbugs-exclude-filter.xml")
}