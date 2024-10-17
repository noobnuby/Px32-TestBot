plugins {
    kotlin("jvm") version "2.0.20"
	id("com.gradleup.shadow") version "8.3.3"
	kotlin("plugin.serialization") version "2.0.20"
}

group = property("group")!!
version = property("version")!!

val jda_version: String by project
val px32_version: String by project
val ktor_version: String by project
val gson_version: String by project

repositories {
	mavenCentral()
	maven("https://repo.wh64.net/repository/maven-releases/")
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "com.gradleup.shadow")
	apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

	repositories {
		mavenCentral()
		maven("https://repo.wh64.net/repository/maven-releases/")
	}

	dependencies {
		implementation(kotlin("stdlib"))
		implementation(kotlin("reflect"))
		compileOnly("net.dv8tion:JDA:$jda_version")
		compileOnly("net.projecttl:px32-bot-api:$px32_version")
		implementation("io.ktor:ktor-client-core:$ktor_version")
		implementation("io.ktor:ktor-client-cio:$ktor_version")
		implementation("io.ktor:ktor-serialization-gson:$ktor_version")
		implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
		implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")
	}

	java {
		toolchain.languageVersion.set(JavaLanguageVersion.of(21))

		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}

	tasks {
		processResources {
			filesMatching("plugin.json") {
				expand(project.properties)
			}
		}
	}
}
