plugins {
  // alias(libs.plugins.pitest.plugin) apply false
  alias(libs.plugins.version.catalog.update.plugin) apply false
}

// apply(plugin = "info.solidsoft.pitest.aggregator")
apply(plugin = "nl.littlerobots.version-catalog-update")

repositories {
    mavenCentral()
}