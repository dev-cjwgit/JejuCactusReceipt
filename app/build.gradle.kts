plugins {
    id("kotlin-kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")

}

android {
    namespace = "com.cjwgit.jejucactusreceipt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cjwgit.jejucactusreceipt"
        minSdk = 33
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    val naviKtxVersion = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$naviKtxVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$naviKtxVersion")

    val ktxMainVersion = "1.12.0"
    implementation("androidx.core:core-ktx:$ktxMainVersion")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    val ktxVersion = "2.7.0"
    runtimeOnly("androidx.lifecycle:lifecycle-viewmodel-ktx:$ktxVersion")
    runtimeOnly("androidx.lifecycle:lifecycle-runtime-ktx:$ktxVersion")
    runtimeOnly("androidx.lifecycle:lifecycle-livedata-ktx:$ktxVersion")

    runtimeOnly("androidx.activity:activity-ktx:1.8.2")
    runtimeOnly("androidx.fragment:fragment-ktx:1.6.2")


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.google.android.material:material:1.11.0")

    implementation("io.insert-koin:koin-androidx-viewmodel:2.2.2")
}