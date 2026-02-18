plugins {
  alias(libs.plugins.nexus.publish.plugin)
  // alias(libs.plugins.pitest.plugin) apply false
  alias(libs.plugins.version.catalog.update.plugin) apply false
}

// apply(plugin = "info.solidsoft.pitest.aggregator")
apply(plugin = "nl.littlerobots.version-catalog-update")

nexusPublishing {
    repositories {
        sonatype()
    }
}

repositories {
    mavenCentral()
}
