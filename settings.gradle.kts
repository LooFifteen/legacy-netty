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

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "legacy-netty"

setOf(
    "native-transport"
).forEach {
    val name = "legacy-netty-$it"
    include(name)
    project(":$name").projectDir = file(it)
}