plugins {
    id("common")
    id("static-analysis")
    id("publishing")
    id("github-packages")
    
  alias(libs.plugins.version.catalog.update.plugin) apply false
}

apply(plugin = "nl.littlerobots.version-catalog-update")
