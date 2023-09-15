plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    implementation("com.google.dagger:hilt-core:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}