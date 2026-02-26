import org.gradle.api.publish.maven.MavenPublication

plugins {
    `maven-publish`
}

publishing {
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

    publications.create<MavenPublication>("gpr") {
        from(components["java"])

        val gitCommitCount = "git rev-list --count HEAD".runCommand().trim().toInt()
        project.version = "0.1.$gitCommitCount"

        groupId = project.group.toString()
        artifactId = project.name
        version = project.version.toString()
    }
}

fun String.runCommand(): String {
    val parts = this.split("\\s".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

    proc.waitFor(1, java.util.concurrent.TimeUnit.MINUTES)
    return proc.inputStream.bufferedReader().readText()
}
