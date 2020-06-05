package com.github.fj.android.awesomeapp.inject.module

import com.github.fj.android.awesomeapp.CoreApplication
import com.github.fj.android.awesomeapp.inject.SingletonFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import kotlin.reflect.KClass

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 05 - Jun - 2020
 */
internal object RetrofitApiServiceFactory : SingletonFactory<Any>() {
    private lateinit var retrofit: Retrofit

    fun init(coreApplication: CoreApplication) {
        super.init()

        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("Http")
                Timber.log(coreApplication.coreLoggingLevel, message)
            }
        }).apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        this.retrofit = Retrofit.Builder()
            .baseUrl(coreApplication.baseApiUrl)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    override fun createInstanceOf(klass: KClass<out Any>): Any? {
        return retrofit.create(klass.java)
    }
}
