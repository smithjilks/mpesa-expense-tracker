// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven( "https://jitpack.io")

    }

    dependencies {
        classpath(Libs.BuildPlugins.androidGradlePlugin)
        classpath(Libs.BuildPlugins.kotlinGradlePlugin)
    }
}

plugins {
    id(Libs.BuildPlugins.androidApplication) version "8.0.0" apply false
    id(Libs.BuildPlugins.androidLibrary) version "8.0.0" apply false
    id(Libs.BuildPlugins.jetbrainsKotlinAndroid) version "1.8.21" apply false
    id(Libs.BuildPlugins.hiltAndroidPlugin) version "2.44" apply false
}

tasks.register("clean").configure {
    delete(rootProject.buildDir)
}