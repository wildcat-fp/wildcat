import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  `java`
  id("info.solidsoft.pitest")
}

val libs = the<LibrariesForLibs>()

testing {
    suites {
        withType<JvmTestSuite> {
            useJUnitJupiter()

            dependencies {
                implementation(libs.assertj)
                implementation(libs.jqwik)
            }
        }
    }
}

pitest {
  pitestVersion = "1.15.2"
  outputFormats = setOf("XML", "HTML")
  timestampedReports = false
  exportLineCoverage = true
  targetClasses = setOf("wildcat.*")

  junit5PluginVersion = "1.2.1"
}

tasks.withType<Test> {
    testLogging {
        // Log standard output and error streams
        showStandardStreams = true

        // Log events for each test method
        // events("started", "passed", "skipped", "failed", "standardOut", "standardError")
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