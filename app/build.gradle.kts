

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

//java.sourceSets["test"].java {
//    srcDir("src/sharedTest/java")
//}
//
//java.sourceSets["androidTest"].java {
//    srcDir("src/sharedTest/java")
//}

android {
    namespace = "br.eng.joaovictor.ghproject"
    compileSdk = 33
    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "br.eng.joaovictor.ghproject"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "br.eng.joaovictor.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    sourceSets["test"].java{
        srcDir("src/sharedTest/java")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildTypes.forEach {
        it.buildConfigField("String", "GITHUB_API_URL", extra["GitHubApiURL"].toString())
        it.buildConfigField("String", "GITHUB_API_KEY", extra["GitHubApiKey"].toString())
    }

    kotlinOptions {
        jvmTarget = "17"

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.hilt.android)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.androidx.window)
    androidTestImplementation(platform(libs.compose.bom))
    kapt(libs.hilt.compiler)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.windowssize)
    implementation(libs.compose.viewmodels)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.hilt.navigation.compose)
    androidTestImplementation(libs.compose.ui.test)
    testImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.test.manifest)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp.interceptor)
    implementation(libs.coil.compose)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.mockWebServer)
}