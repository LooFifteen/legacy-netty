pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://repo.legacyfabric.net/repository/legacyfabric/")
    }
    plugins {
        val loom_version: String by settings
        id("fabric-loom") version loom_version
        id("legacy-looming") version loom_version
    }
}

rootProject.name = "legacy-netty"