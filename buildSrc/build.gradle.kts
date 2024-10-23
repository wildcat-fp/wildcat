plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    /*
    spotless = { id = "com.diffplug.spotless", version.ref = "spotless" }
checkerframework = { id = "org.checkerframework", version.ref = "checkerframework" }
spotbugs = { id = "com.github.spotbugs", version.ref = "spotbugs-plugin" }
errorprone-plugin = { id = "net.ltgt.errorprone", version.ref = "errorprone-plugin" }
 */
    implementation("org.checkerframework:org.checkerframework.gradle.plugin:${libs.versions.checkerframework.get()}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${libs.versions.spotless.get()}")
    implementation("com.github.spotbugs:com.github.spotbugs.gradle.plugin:${libs.versions.spotbugs.plugin.get()}")
    implementation("net.ltgt.errorprone:net.ltgt.errorprone.gradle.plugin:${libs.versions.errorprone.plugin.get()}")

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    // implementation(libs.plugins.spotless)
    // implementation(libs.plugins.spotbugs)
    // implementation(libs.plugins.errorprone.plugin)
}