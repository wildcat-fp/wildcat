plugins {
  id("common")
  id("static-analysis")
  id("publishing")
}

dependencies {
    implementation(libs.assertj)
    implementation(libs.jqwik)
    implementation(libs.junit.jupiter)

    implementation(project(":wildcat-core"))
}
