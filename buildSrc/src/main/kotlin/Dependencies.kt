import org.gradle.api.artifacts.dsl.DependencyHandler

object Versions {
    const val kotlin = "1.7.0"
    const val buildGradle = "7.0.4"
    const val hilt = "2.42"
    const val navigation = "2.3.5"
    const val googleServices = "4.3.10"
    const val firebaseCrashlytics = "2.7.1"
    const val coreKtx = "1.7.0"
    const val fragmentKtx = "1.3.6"
    const val appcompat = "1.3.1"
    const val material = "1.4.0"
    const val constraintLayout = "2.1.0"
    const val firebaseBom = "28.3.1"
    const val glide = "4.12.0"
    const val lifecycleKtx = "2.3.1"
    const val coroutines = "1.5.0"
    const val retrofit = "2.9.0"
    const val okhttp = "4.9.1"
    const val swipeRefreshLayout = "1.1.0"
    const val recyclerView = "1.2.1"
    const val safeFragTransaction = "1.2.1"
    const val servicesAuth = "19.2.0"
    const val room = "2.4.2"

}

/**
 * Gradle and kotlin in project(Project: project)
 */
object BuildPlugins {
    const val androidBuildGradle = "com.android.tools.build:gradle:${Versions.buildGradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val googleServicesPlugin = "com.google.gms:google-services:${Versions.googleServices}"
    const val navigationPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"

    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinParcelize = "kotlin-parcelize"
    const val daggerHilt = "dagger.hilt.android.plugin"
    const val navigation = "androidx.navigation.safeargs"
    const val googleServices = "com.google.gms.google-services"
}

object AndroidSdk {
    const val compile = 30
    const val target = compile
}

object Dependencies {

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"

    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleKtx}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}"
    const val lifecycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleKtx}"
    const val roomKTX = "androidx.room:room-ktx:${Versions.room}"
    const val compileRoomKTX = "androidx.room:room-compiler:${Versions.room}"
    const val safeFragTransaction = "com.github.Zuluft:SafeFragmentTransaction:${Versions.safeFragTransaction}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUI= "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val converterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    const val okhttpBom = "com.squareup.okhttp3:okhttp-bom:${Versions.okhttp}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor"
    const val okhttp = "com.squareup.okhttp3:okhttp"
    const val serviceAuth = "com.google.android.gms:play-services-auth:${Versions.servicesAuth}"
}

fun DependencyHandler.implementLocalUnitTest() {
    add("testImplementation", "junit:junit:4.+")
    add("testImplementation", "androidx.test.ext:junit:1.1.2")
    add("testImplementation", "androidx.test.espresso:espresso-core:3.3.0")
    add("testImplementation", "org.mockito.kotlin:mockito-kotlin:3.2.0")
}
