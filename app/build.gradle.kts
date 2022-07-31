plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.daggerHilt)
    id(BuildPlugins.navigation)
//    id(BuildPlugins.googleServices)
}
apply(from = "../autodimension.gradle.kts")

android {
    flavorDimensions.add("default")
    defaultConfig {
        compileSdkVersion = "31"
        applicationId = "kr.enjoyworks.room"
        minSdk = 21
        targetSdk = 32
        compileSdk = 32
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    productFlavors {
        create("dev") {
            applicationId = "kr.enjoyworks.room"
            versionCode = 1
            versionName = "0.0.1"
            buildConfigField("String", "END_POINT", "\"https://mocki.io/v1/\"")
            resValue("string", "app_name", "Room Dev")
        }

        create("staging") {
            applicationId = "kr.enjoyworks.room"
            versionCode = 1
            versionName = "0.0.1"
            buildConfigField("String", "END_POINT", "\"http://mockintercept/\"")
            resValue("string", "app_name", "Room Staging")
        }
        create("production") {
            applicationId = "kr.enjoyworks.room.production"
            versionCode = 1
            versionName = "0.0.1"
            buildConfigField("String", "END_POINT", "\"http://mockintercept/\"")
            resValue("string", "app_name", "Room Production")
        }
    }

    buildTypes {
        getByName("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUI)
    implementation(Dependencies.recyclerView)
    implementation(Dependencies.swipeRefreshLayout)
    implementation(Dependencies.safeFragTransaction)
    implementation(Dependencies.roomKTX)
    implementation(Dependencies.glide)
    kapt(Dependencies.glideCompiler)

    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleViewModelKtx)
    implementation(Dependencies.lifecycleCommonJava8)

    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.hiltCompiler)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.converterGson)
    implementation(Dependencies.converterScalars)
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpBom)
    implementation(Dependencies.loggingInterceptor)


    kapt(Dependencies.compileRoomKTX)
    kapt(Dependencies.hiltCompiler)

    implementLocalUnitTest()
}

