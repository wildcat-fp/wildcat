plugins {
  id("common")
  id("publishing")
  id("github-packages")
}

dependencies {
    implementation(libs.assertj)

    api(project(":wildcat-core"))
}
