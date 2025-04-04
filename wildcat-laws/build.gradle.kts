plugins {
  id("common")
  id("static-analysis")
}

dependencies {
    implementation(libs.assertj)
    implementation(libs.jqwik)
    implementation(libs.junit.jupiter)

    api(project(":wildcat-core"))
}

// publishing {
//   publications {
//       named<MavenPublication>("mavenJava") {
//           pom {
//               name.set("Wildcat Laws")
//               description.set("Laws for validating Wildcat type class instances.")
//           }
//       }
//   }
// }

tasks.withType<Test> {
    testLogging {
        // Log standard output and error streams
        showStandardStreams = true 

        // Log events for each test method
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
