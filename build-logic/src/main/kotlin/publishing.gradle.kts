plugins {
  `maven-publish`
}

// Configure publishing for all sub-modules
publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])

            artifact(tasks.getByName("sourcesJar"))

            artifact(tasks.getByName("javadocJar")) {
                classifier = "javadoc"
            }


            // Add extra metadata to the POM
            pom {
                url.set("https://github.com/wildcat-fp/wildcat")

                // Configure licensing information
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
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
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/wildcat-fp/wildcat")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}