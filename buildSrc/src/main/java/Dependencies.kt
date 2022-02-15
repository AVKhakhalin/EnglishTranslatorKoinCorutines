import org.gradle.api.JavaVersion

object Config {
    const val application_id = "ru.geekbrains.popular.libraries.less_1_homework"
//    const val application_id = "ru.geekbrains.popular.libraries.englishtranslatorkoincorutines"
    const val compile_sdk = 31
    const val min_sdk = 21
    const val target_sdk = 31
    val java_version = JavaVersion.VERSION_1_8
}

object Release {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val model = ":model"
    const val utils = ":utils"
    // TODO: Добавить новые модули
}

object Versions {
    //AndroidX
    const val androidX = "1.3.1"
    //Kotlin
    const val kotlinKTX = "1.3.2"
    const val kotlinJDK7 = "1.5.20"
    const val kotlinCoroutinescore = "1.5.1"
    const val kotlinCoroutineandroid = "1.5.0"
    //Design
    const val designMaterial = "1.4.0"
    const val designConstrlayout = "2.1.1"
    // Test
    const val testJunit = "4.12"
    const val testRunner = "1.2.0"
    const val testEspressocore = "3.2.0"
    // Room
    const val room = "2.4.0"
    const val roomJDBC = "3.34.0"
    // Dagger
    const val dagger = "2.39.1"
    const val daggerAddSupport = "2.12"
    // Koin
    const val koin = "3.1.2"
    const val koinRetrofitcoroutines = "0.9.2"
    const val koinTestsandroidx = "1.0.0"
    // Coroutines
    const val coroutines = "1.3.9"
    // Retrofit
    const val retrofit = "2.9.0"
    const val retrofitGson = "2.7.1"
    const val retrofitRxjava2 = "2.4.0"
    const val retrofitRxjava3 = "2.9.0"
    const val retrofitInterceptor = "3.12.1"
    // OkHTTP
    const val okHTTP = "4.6.0"
    // Glide
    const val glide = "4.11.0"
    // Picasso
    const val picasso = "2.71828"
    // RxJava
    //const val rxJava = "3.0.0"
    const val rxJavaAndroid = "2.1.1"
    const val rxJavaRxJava = "3.0.0-RC3"
    // Cicerony
    const val cicerony = "6.6"
    // Moxy
    const val moxy = "2.2.2"
    // Lifecycle
    const val lifecycle = "2.2.0"
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidX}"
}

object Kotlin {
    const val ktx = "androidx.core:core-ktx:${Versions.kotlinKTX}"
    const val jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinJDK7}"
    const val coroutinescore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinescore}"
    const val coroutinesandroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutineandroid}"
}

object Design {
    //You should not use the com.android.support and com.google.android.material dependencies in your app at the same time
    const val material = "com.google.android.material:material:${Versions.designMaterial}"
    const val constrlayout = "androidx.constraintlayout:constraintlayout:${Versions.designConstrlayout}"
}

object Tests {
    const val junit = "junit:junit:${Versions.testJunit}"
    const val runner = "androidx.test:runner:${Versions.testRunner}"
    const val espressocore = "androidx.test.espresso:espresso-core:${Versions.testEspressocore}"
    const val filetreedir = "'libs'"
    const val filetreeinclude = "'*.jar'"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val m1Support = "org.xerial:sqlite-jdbc:${Versions.roomJDBC}"
    const val rxJava3 = "androidx.room:room-rxjava3:${Versions.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.room}"
}

object Dagger {
    const val daggger = "com.google.dagger:dagger:${Versions.dagger}"
    const val runtime = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val addsupport = "com.google.dagger:dagger-android-support:${Versions.daggerAddSupport}"
}

object Koin {
    //Koin core features
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    //Koin main features for Android (Scope,ViewModel ...)
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    //Koin Java Compatibility
    const val compat = "io.insert-koin:koin-android-compat:${Versions.koin}"
    // Koin for Retrofit 2 coroutines (needs for CoroutineCallAdapterFactory())
    const val retrofitcoroutines = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.koinRetrofitcoroutines}"
    // Koin for Tests
    const val testsandroidx = "androidx.test:core:${Versions.koinTestsandroidx}"
    const val testsio = "io.insert-koin:koin-test:${Versions.koin}"
}

object Coroutines {
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object Retrofit {
    const val runtime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}"
    const val rxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitRxjava2}"
    const val rxJava3 = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofitRxjava3}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.retrofitInterceptor}"
}

object OkHTTP {
    const val runtime = "com.squareup.okhttp3:okhttp:${Versions.okHTTP}"
}

object Glide {
    const val runtime = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object Picasso {
    const val runtime = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object RxJava {
//    const val android = "io.reactivex.rxjava3:rxandroid:${Versions.rxJava}"
//    const val reactive = "io.reactivex.rxjava3:rxjava:${Versions.rxJava}"
    const val android = "io.reactivex.rxjava2:rxandroid:${Versions.rxJavaAndroid}"
    const val reactive = "io.reactivex.rxjava3:rxjava:${Versions.rxJavaRxJava}"
}

object Cicerony {
    const val runtime = "com.github.terrakok:cicerone:${Versions.cicerony}"
}

object Moxy {
    const val community = "com.github.moxy-community:moxy:${Versions.moxy}"
    const val compiler = "com.github.moxy-community:moxy-compiler:${Versions.moxy}"
    const val ktx = "com.github.moxy-community:moxy-ktx:${Versions.moxy}"
    const val androidx = "com.github.moxy-community:moxy-androidx:${Versions.moxy}"
}

object Lifecycle {
    const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    // LiveData
    const val ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    // Lifecycles only (without ViewModel or LiveData)
    const val runtimektx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val process = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
    const val java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
}