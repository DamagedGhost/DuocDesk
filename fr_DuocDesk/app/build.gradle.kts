plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.duocdesk"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.duocdesk"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Funciones básicas de Kotlin para Android
    // Para componentes lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Material Design 3 en Compose
    implementation("androidx.compose.material3:material3:1.4.0")
    // UI base en Compose
    implementation("androidx.compose.ui:ui:1.9.3")
    // Vista previa de UI en Android Studio
    implementation("androidx.compose.ui:ui-tooling-preview:1.9.3")
    // ViewModel en Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
    // Iconos de Material
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    // Herramientas para debug UI
    debugImplementation("androidx.compose.ui:ui-tooling:1.9.3")
    // Testing
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.9.3")
}