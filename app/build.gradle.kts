plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ger.memo"
        minSdk = 24
        targetSdk = 35
        versionCode = 150
        versionName = "1.5.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "app_name", "Memo") // Release app name
        }
        debug {
            applicationIdSuffix = ".debug"
            resValue("string", "app_name", "Memo (debug)") // Release app name
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.ger.memo"
}

dependencies {
    implementation ("nl.dionsegijn:konfetti-compose:2.0.4")
    implementation ("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.compose.ui:ui-test-junit4-android:1.7.6")
    implementation(platform("androidx.compose:compose-bom:2024.11.00"))


    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.runtime:runtime-livedata")

    implementation("androidx.activity:activity-compose:1.9.3")

    implementation ("com.touchlane:gridpad:1.1.2")

    implementation ("com.github.SmartToolFactory:Compose-RatingBar:2.1.1")

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.test.espresso:espresso-device:1.0.1")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation(platform("androidx.compose:compose-bom:2024.11.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.6")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    debugImplementation("androidx.compose.ui:ui-test-manifest")
}