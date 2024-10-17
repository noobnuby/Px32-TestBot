plugins {
	kotlin("jvm") version "2.0.20"
	id("io.ktor.plugin") version "3.0.0-rc-1"
}

group = "com.noobnuby.plugin"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}