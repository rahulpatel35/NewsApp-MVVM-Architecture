// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false   // applies Android application plugin
    alias(libs.plugins.kotlin.android) apply false         // applies Kotlin support
    alias(libs.plugins.kotlin.ksp) apply false             // applies KSP for annotation processing
    alias(libs.plugins.dagger.hilt) apply false            // applies Hilt DI plugin.
    alias(libs.plugins.kotlin.compose) apply false        // applies compose plugin
}
