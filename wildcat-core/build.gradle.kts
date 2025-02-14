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

pitest {
  excludedGroups = setOf("laws")
}

publishing {
  publications {
      named<MavenPublication>("mavenJava") {
          pom {
              name.set("Wildcat Core")
              description.set("Core functional programming utilities for Java.")
          }
      }
  }
}