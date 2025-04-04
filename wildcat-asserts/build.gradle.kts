plugins {
  id("common")
}

dependencies {
    implementation(libs.assertj)

    api(project(":wildcat-core"))
}

// publishing {
//   publications {
//       named<MavenPublication>("mavenJava") {
//           pom {
//               name.set("Wildcat Assert")
//               description.set("Custom AssertJ assertions for Wildcat types.")
//           }
//       }
//   }
// }