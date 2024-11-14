plugins {
  alias(libs.plugins.pitest.plugin) apply false
}

apply(plugin = "info.solidsoft.pitest.aggregator")

repositories {
    mavenCentral()
}