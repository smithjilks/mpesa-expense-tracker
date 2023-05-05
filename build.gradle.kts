// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        //classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
        classpath(Libs.BuildPlugins.androidGradlePlugin)
        classpath(Libs.BuildPlugins.kotlinGradlePlugin)
    }
}

plugins {
    id(Libs.BuildPlugins.androidApplication) version "8.0.0" apply false
    id(Libs.BuildPlugins.androidLibrary) version "8.0.0" apply false
    id(Libs.BuildPlugins.jetbrainsKotlinAndroid) version "1.8.0" apply false
}

tasks.register("clean").configure {
    delete(rootProject.buildDir)
}