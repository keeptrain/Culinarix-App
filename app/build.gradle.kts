import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.culinarix"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.culinarix"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val urlAuth: String = gradleLocalProperties(rootDir).getProperty("BASE_URL_LOGIN")
        buildConfigField("String", "BASE_URL_LOGIN", urlAuth)

        val urlCollab: String = gradleLocalProperties(rootDir).getProperty("BASE_URL_COLLAB")
        buildConfigField("String", "BASE_URL_COLLAB", urlCollab)

        val urlContent: String = gradleLocalProperties(rootDir).getProperty("BASE_URL_CONTENT")
        buildConfigField("String", "BASE_URL_CONTENT", urlContent)

    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Livedata
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //glide
    implementation("com.github.bumptech.glide:glide:4.14.2")

    //lottie
    implementation ("com.airbnb.android:lottie:6.2.0")
}