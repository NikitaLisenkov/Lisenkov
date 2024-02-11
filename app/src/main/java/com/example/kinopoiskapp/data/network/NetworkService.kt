package com.example.kinopoiskapp.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object NetworkService {

    private const val BASE_URL: String = "https://kinopoiskapiunofficial.tech/api/v2.2/films/"
    private const val API_KEY: String = "X-API-KEY"
    private const val API_VALUE: String = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    private const val TIME_OUT: Long = 15

    private val httpLoggingInterceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    private val interceptor: Interceptor =
        Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .header(API_KEY, API_VALUE)
                    .build()
            )
        }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .build()

    private val converterFactory: Converter.Factory = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }.asConverterFactory("application/json".toMediaType())

    private val retrofitClient: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .build()

    val api: FilmsApi = retrofitClient.create(FilmsApi::class.java)

}