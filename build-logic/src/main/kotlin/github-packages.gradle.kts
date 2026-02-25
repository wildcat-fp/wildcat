import org.gradle.api.publish.maven.MavenPublication

plugins {
  `maven-publish`
}

publishing {
  publications {
    create<MavenPublication>("snapshotPublication") {
      from(components["java"])

      groupId = "io.github.wildcat-fp"
      artifactId = project.name
      version = properties["snapshotVersion"].toString()

      pom {
        name.set("Wildcat FP")
        description.set("A functional programming library for Java")
        url.set("https://github.com/wildcat-fp/wildcat")

        licenses {
          license {
            name.set("MIT")
            url.set("https://opensource.org/licenses/MIT/")
            distribution.set("repo")
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
          url.set("https://github.com/wildcat-fp/wildcat")
          connection.set("scm:git:git://github.com/wildcat-fp/wildcat.git")
          developerConnection.set("scm:git:ssh://git@github.com/wildcat-fp/wildcat.git")
        }
      }
    }
  }

  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/${System.getenv("GITHUB_REPOSITORY") ?: "wildcat-fp/wildcat"}")
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }
}
