pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://raw.github.com/edaakyil/android-maven-repo/main")
        }
        maven {
            url = uri("https://raw.github.com/oguzkaran/android-aug-2024-maven-repo/main")
        }
        maven {
            url = uri("https://raw.github.com/oguzkaran/android-aug-2024-karandev-maven-repo/main")
        }
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://raw.github.com/edaakyil/android-maven-repo/main")
        }
        maven {
            url = uri("https://raw.github.com/oguzkaran/android-aug-2024-maven-repo/main")
        }
        maven {
            url = uri("https://raw.github.com/oguzkaran/android-aug-2024-karandev-maven-repo/main")
        }
    }
}

rootProject.name = "DemoLibraryUsageApp"
include(":app")
include(":DataServiceLibrary")
