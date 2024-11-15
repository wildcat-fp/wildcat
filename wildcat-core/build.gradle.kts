plugins {
  id("common")
  id("static-analysis")
}

testing {
    suites {
        withType<JvmTestSuite> {
            dependencies {
                implementation(project(":wildcat-laws"))
            }
        }
    }
}

pitest {
  excludedTestClasses = setOf("**.*LawsTest")
}