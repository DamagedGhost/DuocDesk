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
        // La versión de la aplicación en formato mayor.menor.parche
        versionName = "0.1"

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
    implementation(libs.androidx.compose.material3)
    // UI base en Compose
    implementation(libs.androidx.compose.ui)
    // Vista previa de UI en Android Studio
    implementation(libs.androidx.compose.ui.tooling.preview)
    // ViewModel en Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Iconos de Material
    implementation(libs.androidx.compose.material.icons.extended)

    // Herramientas para debug UI
    debugImplementation(libs.androidx.compose.ui.tooling)
    // Testing
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Splash Screen API: para pantallas de bienvenida animadas
    implementation(libs.androidx.core.splashscreen)
}