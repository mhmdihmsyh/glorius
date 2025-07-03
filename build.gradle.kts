// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{

    dependencies{
        classpath ("com.google.gms:google-services:4.4.2")
    }
}



plugins {
    id("com.android.application") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}
