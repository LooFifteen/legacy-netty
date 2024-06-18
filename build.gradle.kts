plugins {
    id("fabric-loom")
    id("legacy-looming")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
}

group = property("maven_group")!!
version = property("mod_version")!!

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    withSourcesJar()
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
    maven("https://repo.legacyfabric.net/repository/legacyfabric/")
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1/")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.legacyfabric:yarn:${property("yarn_mappings")}")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    shadow(implementation("io.netty:netty-all:4.1.111.Final")!!)

    // dev env
    modLocalRuntime("me.djtheredstoner:DevAuth-fabric:1.2.1")
    localRuntime("org.apache.logging.log4j:log4j-core:2.23.1")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        configurations = listOf(project.configurations.shadow.get())

        dependencies {

        }
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.get().archiveFile)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = property("archives_base_name").toString()
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "hypera"
            url = uri("https://repo.hypera.dev/releases/")
            credentials(PasswordCredentials::class)
        }
    }
}