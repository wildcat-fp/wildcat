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
                compileOnly(libs.lombok)
                annotationProcessor(libs.lombok)

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