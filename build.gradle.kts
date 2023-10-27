// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
//        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "8.1.0" apply false
}