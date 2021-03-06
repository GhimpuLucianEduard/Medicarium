package com.medicarium.Data.ApiServices.RefitServices

import com.andreacioccarelli.cryptoprefs.CryptoPrefs
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.medicarium.MedicorumApp
import com.medicarium.Utilities.ApiConstants.Companion.BASE_URL
import com.medicarium.Utilities.SharedPreferences
import com.medicarium.Utilities.SharedPreferences.Companion.TOKEN
import empty
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceFactory(private val connectivityInterceptor: ConnectivityInterceptor) {

        fun <T> create(service: Class<T>): T {
            val requestInterceptor = Interceptor { chain ->

                val preferences = CryptoPrefs(MedicorumApp.applicationContext(), SharedPreferences.FILE_NAME, SharedPreferences.SECRET_KEY)
                val token = preferences.get(TOKEN, String.empty())

                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(service)
        }

}