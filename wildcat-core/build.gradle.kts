plugins {
  id("common")
  id("static-analysis")
  id("release")
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

publishing {
  publications {
      named<MavenPublication>("mavenJava") {
          pom {
              name.set("Wildcat Core")
              description.set("Core data structures and type classes for functional programming in Java.")
          }
      }
  }
}
