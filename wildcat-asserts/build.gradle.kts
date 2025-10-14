plugins {
  id("common")
}

dependencies {
    implementation(libs.assertj)

    api(project(":wildcat-core"))
}
