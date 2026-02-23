
plugins {
  id("com.vanniktech.maven.publish")
  `java-library`
}

java {
  withJavadocJar()
  withSourcesJar()
}

mavenPublishing {
  val pomName = project.name
  val pomDescription = project.description ?: "A functional programming library for Java"

  coordinates("io.github.wildcat.fp", project.name, project.version.toString())

  pom {
    name.set(pomName)
    description.set(pomDescription)
    inceptionYear.set("2024")
    url.set("https://github.com/wildcat-fp/wildcat")

    licenses {
      license {
        name.set("MIT License")
        url.set("https://opensource.org/licenses/MIT")
        distribution.set("repo")
      }
    }

    developers {
      developer {
        id.set("wildcat-fp")
        name.set("Wildcat FP")
        email.set("wildcat.fp@example.com")
      }
    }

    scm {
      url.set("https://github.com/wildcat-fp/wildcat")
      connection.set("scm:git:git://github.com/wildcat-fp/wildcat.git")
      developerConnection.set("scm:git:ssh://git@github.com/wildcat-fp/wildcat.git")
    }
  }
}
