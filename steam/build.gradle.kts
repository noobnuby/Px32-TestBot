plugins {
	kotlin("jvm") version "2.0.20"
}

group = "com.noobnuby.plugin"
version = "1.0.0"

repositories {
	mavenCentral()
}

tasks {
	shadowJar {
		doLast {
			copy {
				from(archiveFile)
				into(File("C:\\Users\\user\\Desktop\\px32\\plugins"))
			}
		}
	}
}

kotlin {
	jvmToolchain(21)
}