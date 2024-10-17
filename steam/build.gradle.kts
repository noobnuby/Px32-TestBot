plugins {
	kotlin("jvm") version "2.0.20"
}

group = "com.noobnuby.plugin"
version = "1.0.0"

repositories {
	mavenCentral()
}

//tasks {
//	shadowJar {
//		archiveBaseName.set(rootProject.name)
//		archiveClassifier.set("")
//		archiveVersion.set("")
//
//		manifest {
//			attributes["Main-Class"] = "com.noobnuby.plugin.MainKt"
//		}
//	}
//}

kotlin {
	jvmToolchain(21)
}