plugins {
    id("common")
    id("static-analysis")
    
  alias(libs.plugins.version.catalog.update.plugin) apply false
}

apply(plugin = "nl.littlerobots.version-catalog-update")
