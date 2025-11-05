plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") // Aplicar el plugin
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

    implementation("androidx.compose.runtime:runtime-livedata")
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

    // Dependencias para consumo de API RESTful (backend Duoc Desk / API externas)
    // Retrofit & Gson Converter
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // O la versión más reciente
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // O la versión más reciente

    // ViewModel & LiveData (Lifecycle)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1") // O la versión más reciente
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1") // O la versión más reciente
    implementation("androidx.activity:activity-ktx:1.6.1") // Para 'viewModels()' delegate

    // Coroutines (para llamadas asíncronas)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4") // O la versión más reciente

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1") // O la versión más reciente

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1") // Para Coroutines
    ksp("androidx.room:room-compiler:2.6.1") // Procesador de anotaciones
}