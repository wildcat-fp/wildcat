plugins {
  `maven-publish`
}

// Configure publishing for all sub-modules
publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
            // Add extra metadata to the POM
            pom {
                url.set("https://github.com/wildcat-fp/wildcat")

                // Configure licensing information
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("SkittishSloth")
                        name.set("Matthew Cory")
                        email.set("matthewcory1@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/wildcat-fp/wildcat.git")
                    developerConnection.set("scm:git:ssh://github.com/wildcat-fp/wildcat.git")
                    url.set("https://github.com/wildcat-fp/wildcat")
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/wildcat-fp/wildcat/issues")
                }
            }
        }
    }
    repositories {
      // This is where you define where you publish your artifact
      maven {
          name = "MyRepo"
          url = uri("file://${project.rootDir}/localrepo")
      }
   }
}