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
  pitestVersion.set("1.22.0")
  outputFormats.addAll("XML", "HTML")
  timestampedReports.set(false)
  exportLineCoverage.set(true)
  targetClasses.set(setOf("io.github.wildcat.*"))

  threads.set(Runtime.getRuntime().availableProcessors())

  junit5PluginVersion.set("1.2.3")

  if (project.name == "wildcat-assert") {
    failWhenNoMutations.set(false)
  }
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