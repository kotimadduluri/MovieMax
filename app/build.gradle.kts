plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.moviemax"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moviemax"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
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

    flavorDimensions += listOf("test")
    productFlavors {
        create("dev") {
            dimension = "test"
            versionNameSuffix = "-Dev"
            applicationIdSuffix = ".dev"
            buildConfigField("String", "APP_DOMAIN", "\"www.episodate.com\"")
        }
        create("prod") {
            dimension = "test"
            buildConfigField("String", "APP_DOMAIN", "\"www.episodate.com\"")
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

    val androidLifecycle = "2.6.2"

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$androidLifecycle")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:$androidLifecycle")

    //navigation
    val navVersion = "2.7.2"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    //retrofit
    val retrofit = "2.9.0"
    //implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")

    //koin
    val koinVersion = "3.4.0"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")

    //custom modules
    implementation(project(mapOf("path" to ":network")))
    implementation(project(mapOf("path" to ":common")))

    //coil
    implementation("io.coil-kt:coil-compose:2.4.0")


    //testing related

    //for debugging compose
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //coroutines
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    //livedata
    testImplementation("androidx.lifecycle:lifecycle-livedata-ktx:$androidLifecycle")

    //testing requirements
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    //Mocking
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("io.mockk:mockk:1.13.1")

    //Assertion
    testImplementation("com.google.truth:truth:1.1.3")

    //turbine not required for now
    //testImplementation("app.cash.turbine:turbine:1.0.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}