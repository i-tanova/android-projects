plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)

//    kotlin("multiplatform") version "1.9.20" apply false
//    id("com.android.application") version "8.1.4" apply false
//    id("org.jetbrains.compose") version "1.5.11" apply false
}
