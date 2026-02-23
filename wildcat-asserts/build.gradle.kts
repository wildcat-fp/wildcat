plugins {
  id("common")
  id("publishing")
}

dependencies {
    implementation(libs.assertj)

    api(project(":wildcat-core"))
}
