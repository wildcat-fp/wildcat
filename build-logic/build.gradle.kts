plugins {
    `kotlin-dsl`
}

dependencies {
  implementation("org.checkerframework:org.checkerframework.gradle.plugin:${libs.versions.checkerframework.plugin.get()}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${libs.versions.spotless.get()}")
    implementation("com.github.spotbugs:com.github.spotbugs.gradle.plugin:${libs.versions.spotbugs.plugin.get()}")
    implementation("net.ltgt.errorprone:net.ltgt.errorprone.gradle.plugin:${libs.versions.errorprone.plugin.get()}")
    // implementation("info.solidsoft.pitest:info.solidsoft.pitest.gradle.plugin:${libs.versions.pitest.plugin.get()}")

    implementation("nl.littlerobots.version-catalog-update:nl.littlerobots.version-catalog-update.gradle.plugin:${libs.versions.version.catalog.update.plugin.get()}")

    implementation("org.openrewrite.rewrite:org.openrewrite.rewrite.gradle.plugin:${libs.versions.openrewrite.plugin.get()}")

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}