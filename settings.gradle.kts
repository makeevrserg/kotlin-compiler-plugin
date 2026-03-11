pluginManagement {
    includeBuild("library")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    includeBuild("library")
    repositories {
        mavenCentral()
    }
}

rootProject.name = "root-library"

include("sample")
