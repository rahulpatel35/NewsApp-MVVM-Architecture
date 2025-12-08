plugins {
    //android-application → lets you build & run Android apps.
    alias(libs.plugins.android.application)

    //kotlin-android → adds Kotlin support for Android development.
    alias(libs.plugins.kotlin.android)

    //ksp → processes annotations faster, used for Room, Hilt, Moshi, etc.
    alias(libs.plugins.kotlin.ksp)

    //dagger-hilt → enables Hilt dependency injection in the project.
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.rahulpatel.newsapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.rahulpatel.newsapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    /*kotlinOptions {
        jvmTarget = "11"
    }*/

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //Retrofit2
    //retrofit → makes network calls simple.
    implementation(libs.retrofit)
    //converter-gson → turns JSON into Kotlin objects.
    implementation(libs.converter.gson)
    //logging-interceptor → helps you see what’s going on during requests.
    implementation(libs.logging.interceptor)

    //ViewModel
    //lifecycle-viewmodel-ktx → For ViewModel + coroutine support (viewModelScope).
    implementation(libs.androidx.lifecycle.runtime)
    //lifecycle-runtime-ktx → For lifecycle-aware coroutine support (lifecycleScope, repeatOnLifecycle).
    implementation(libs.androidx.lifecycle.viewmodel)

    // Recycler View
    // RecyclerView → Efficient, flexible list/grid component.
    //Needs an Adapter + ViewHolder to bind your data.
    //Works with different LayoutManagers (LinearLayoutManager, GridLayoutManager, etc.).
    implementation(libs.androidx.recyclerview)

    //Glide
    //Glide → Loads & caches images.
    //Super useful in apps with avatars, product images, feeds, etc.
    //Easy integration with RecyclerView.
    //Can extend with kapt to generate custom API (GlideApp).
    implementation(libs.glide)

    //Dagger2
    //dagger → Provides the DI framework.

    //implementation(libs.dagger)

    //dagger-compiler → Generates code (DaggerAppComponent) behind the scenes.
    //Makes dependency management much easier and more testable.
    // Difference between kapt and ksp
    //kapt = runs Java-style annotation processors → slower, generates stubs.
    //ksp = designed for Kotlin → much faster, better incremental builds.
    //Many modern Jetpack + popular libraries (Room, Moshi, Hilt, Glide, etc.) now provide KSP-compatible artifacts.

    //ksp(libs.dagger.compiler)

    //hilt-android → Core runtime DI support for Android.
    implementation(libs.hilt.android)

    //hilt-compiler → Generates code for @Inject, @Module, @HiltViewModel, etc.
    ksp(libs.hilt.android.compiler)

    // Browser
    //androidx.browser:browser → gives you Chrome Custom Tabs and better web integration.
    //Use it when you want to show web pages inside your app but still rely on the user’s browser engine.
    implementation(libs.androidx.browser)

    //Room
    //room-runtime → core database support.
    implementation(libs.androidx.room.runtime)
    //room-ktx → coroutine + Flow support.
    implementation(libs.androidx.room.ktx)
    //room-compiler → generates the database + DAO code.
    //Together → a type-safe SQLite wrapper that plays nicely with ViewModel + LiveData/Flow.
    //Note: Kapt is now in maintenance mode, and we recommend that you migrate from kapt to KSP for all processors that support it.
    // In most cases, this migration only requires changes to your project's build configuration.
    ksp(libs.androidx.room.compiler)

    //Paging
    //paging-runtime → handles endless scrolling, pagination, and memory-efficient lists.
    //Works with Room, Retrofit, and RecyclerView.
    //Perfect for feeds, search results, chat history, etc.
    implementation(libs.androidx.paging.runtime)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}