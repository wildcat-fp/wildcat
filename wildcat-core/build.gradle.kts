plugins {
  id("common")
  id("static-analysis")
  id("publishing")
  id("github-packages")
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
