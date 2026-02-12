
plugins {
  `maven-publish`
  signing
  id("io.github.gradle-nexus.publish-plugin")
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
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
val gpgKey = System.getenv("GPG_SIGNING_KEY")
val gpgPassword = System.getenv("GPG_SIGNING_PASSWORD")

if (!gpgKey.isNullOrBlank()) {
    signing {
        useInMemoryPgpKeys(gpgKey, gpgPassword)
        sign(publishing.publications["mavenJava"])
    }
}
