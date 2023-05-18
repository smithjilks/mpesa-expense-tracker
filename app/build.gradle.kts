import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id(Libs.BuildPlugins.androidApplication)
    kotlin("android")
    kotlin("kapt")
    id(Libs.BuildPlugins.hiltAndroidPlugin)
}

android {
    namespace = ConfigurationData.namespace
    compileSdk = ConfigurationData.compileSdk

    defaultConfig {
        applicationId = ConfigurationData.applicationId
        minSdk = ConfigurationData.minSdk
        targetSdk = ConfigurationData.targetSdk
        versionCode = ConfigurationData.versionCode
        versionName = ConfigurationData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packagingOptions {
        resources {
            exclude("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(Libs.AndroidX.ktxCore)
    implementation(Libs.AndroidX.lifecycleRuntimeKtx)
    implementation(Libs.AndroidX.Compose.activity)

    implementation(platform(Libs.AndroidX.Compose.bom))
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.uiGraphics)
    implementation(Libs.AndroidX.Compose.uiToolingPreview)
    implementation(Libs.AndroidX.Compose.material3)
    implementation(Libs.AndroidX.Compose.navigation)


    implementation(Libs.hilt)
    kapt(Libs.hiltCompiler)
    kapt(Libs.hiltAndroidCompiler)

    implementation(project(":core"))
    implementation(project(":feature"))

    testImplementation(Libs.TestLibraries.junit4)
    androidTestImplementation(Libs.TestLibraries.junitExt)
    androidTestImplementation(Libs.TestLibraries.espressoCore)
    androidTestImplementation(platform(Libs.TestLibraries.composeBom))
    androidTestImplementation(Libs.TestLibraries.junit4ComposeUI)
    debugImplementation(Libs.TestLibraries.composeUiTooling)
    debugImplementation(Libs.TestLibraries.composeUiTestManifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}