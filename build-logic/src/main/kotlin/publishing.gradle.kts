
plugins {
  `maven-publish`
  signing
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
                name.set("Wildcat")
                description.set("A functional programming library for Java")
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
                        url.set("https://github.com/SkittishSloth")
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
}

// Configure signing for all publications
signing {
    // Signing is required for release builds, but not for snapshots
    isRequired = !(project.version as String).endsWith("-SNAPSHOT")

    // If the GPG key is present, configure it for signing.
    // The build will fail if isRequired=true and the key is not present.
    val gpgKey = System.getenv("GPG_SIGNING_KEY")
    val gpgPassword = System.getenv("GPG_SIGNING_PASSWORD")
    if (gpgKey != null) {
        useInMemoryPgpKeys(gpgKey, gpgPassword)
    }

    sign(publishing.publications)
}
