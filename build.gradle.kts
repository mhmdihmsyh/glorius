// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        // Plugin untuk Firebase (Google Services)
        classpath("com.google.gms:google-services:4.4.2")
        // Plugin untuk Firebase Crashlytics (opsional)
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.1")
    }
}

plugins {
    // Plugin Android dan Kotlin
    id("com.android.application") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false

    // Plugin Google Services (Firebase)
    id("com.google.gms.google-services") version "4.4.2" apply false

    // Plugin Crashlytics (opsional)
    id("com.google.firebase.crashlytics") version "3.0.1" apply false
}
