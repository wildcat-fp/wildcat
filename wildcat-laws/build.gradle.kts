plugins {
  id("common")
  id("static-analysis")
}

dependencies {
    implementation(libs.assertj)
    implementation(libs.jqwik)
    implementation(libs.junit.jupiter)

    implementation(project(":wildcat-core"))
}
