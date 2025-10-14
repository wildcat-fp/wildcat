plugins {
  id("common")
  id("static-analysis")
  id("publishing")
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
