import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("kr.entree.spigradle") version "2.3.4"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "fr.juanvalero"
version = "1.0-SNAPSHOT"
description = "A bukkit TntTag plugin"

repositories {
    mavenCentral()
}

dependencies {
    // Paper API
    compileOnly(group = "com.destroystokyo.paper", name = "paper-api", version = "1.16.5-R0.1-SNAPSHOT")

    // Cloud
    implementation(group = "cloud.commandframework", name = "cloud-paper", version = "1.6.2")
    implementation(group = "cloud.commandframework", name = "cloud-annotations", version = "1.6.2")

    // Guice
    implementation(group = "com.google.inject", name = "guice", version = "5.1.0")
    implementation(group = "com.google.inject.extensions", name = "guice-assistedinject", version = "5.1.0")
    implementation(group = "com.google.inject.extensions", name = "guice-throwingproviders", version = "5.1.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

tasks {
    jar {
        dependsOn("shadowJar")
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("${project.properties["plugin.name"]}.${archiveExtension.get()}")
    }
}

spigot {
    main = "fr.juanvalero.tnttag.TntTagPlugin"
    name = project.properties["plugin.name"].toString()
    authors = listOf(project.properties["plugin.author"].toString())
    apiVersion = project.properties["plugin.apiversion"].toString()
}
