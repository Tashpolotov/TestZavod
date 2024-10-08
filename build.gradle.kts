// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21"
    alias(libs.plugins.jetBrains.dokka)

}

buildscript {
    dependencies {
        classpath(libs.jetBrains.dokka.gradle)
        classpath(libs.jetBrains.dokka.android.documentation)
        classpath(libs.jetBrains.dokka.android.gradle)
    }
}
