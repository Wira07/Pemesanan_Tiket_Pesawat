pluginManagement {
    repositories {
        google() {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()  // Pastikan mavenCentral ada di sini
        maven { url = uri("https://jitpack.io") }  // Tambahkan repositori JitPack
    }
}

rootProject.name = "Pemesanan Tiket Pesawat"
include(":app")
