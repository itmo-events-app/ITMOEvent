plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.openapi.generator") version "7.4.0"
    kotlin("plugin.serialization") version "1.9.22"

    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "org.itmo.itmoevent"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.itmo.itmoevent"
        minSdk = 26
        targetSdk = 34
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
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    //RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //Image loader
    implementation ("com.squareup.picasso:picasso:2.8")
}

kapt {
    correctErrorTypes = true
}

val basePackageName = "${android.namespace}.network"
openApiGenerate {
    generatorName.set("kotlin")
    inputSpec.set("$rootDir/api-docs.json")
    outputDir.set("$rootDir/openapi")
    packageName.set(basePackageName)
    apiPackage.set("${basePackageName}.api")
    invokerPackage.set("${basePackageName}.invoker")
    modelPackage.set("${basePackageName}.model")
    configOptions.set(mapOf(
        "library" to "jvm-retrofit2",
        "serializationLibrary" to "kotlinx_serialization",
        "useCoroutines" to "true",
    ))
}