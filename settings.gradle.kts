pluginManagement {
    repositories {
        // Plugins DSL (incluye plugins de Gradle Portal, Android, Google)
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.application")      version "8.10.1"
        id("org.jetbrains.kotlin.android") version "1.9.23"
        id("com.google.gms.google-services") version "4.4.0"

    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Dependencias de implementación
        google()
        mavenCentral()
    }
}
rootProject.name = "actividades"
include(":app")