import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.jpenilla.runpaper.task.RunServerTask

plugins {
    java
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.github.namiuni"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()

    // Paper
    maven("https://papermc.io/repo/repository/maven-public/")

    // API
    maven("https://jitpack.io")

    // Utils
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    // Paper
    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.5-R0.1-SNAPSHOT")

    // API
    compileOnly("com.github.MilkBowl", "VaultAPI", "1.7")

    // Command
    implementation("cloud.commandframework", "cloud-paper", "1.6.1")
    implementation("cloud.commandframework", "cloud-annotations", "1.6.1")

    // Util
    implementation("co.aikar", "taskchain-bukkit", "3.7.2")
}

val mainPackage = "$group.maezawa"
val mainClass = "$mainPackage.${rootProject.name}"

bukkit {
    name = rootProject.name
    version = project.version as String
    main = mainClass
    apiVersion = "1.16"
    softDepend = listOf("Vault")
    description = "オフラインプレイヤーにお金を配布するプラグイン"
    author = "Unitarou"
    website = "https://github.com/NamiUni"
}

tasks {
    withType<ShadowJar> {
        this.archiveClassifier.set(null as String?)
        relocate("io.papermc.lib", "$mainPackage.paperlib")
        relocate("co.aikar.taskchain", "$mainPackage.taskchain")
    }

    withType<RunServerTask> {
        this.minecraftVersion("1.16.5")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}
