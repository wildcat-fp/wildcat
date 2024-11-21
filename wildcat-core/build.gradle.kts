plugins {
  id("common")
  id("static-analysis")
}

testing {
    suites {
        withType<JvmTestSuite> {
            dependencies {
                implementation(project(":wildcat-laws"))
                implementation(project(":wildcat-asserts"))
            }
        }
    }
}

tasks.withType<Test> {
  useJUnitPlatform {
    // excludeTags("laws")
  }
}

pitest {
  // excludedGroups = setOf("laws")
}