package com.example.companyproject.net

import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        fun getClient(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addInterceptor(
                        invoke {
                            val headerCode = it.request().newBuilder()
                                .addHeader("lang", Locale.getDefault().language)
                                .addHeader(
                                    "Authorization",
                                    "KakaoAK f999a41bcbdb050ae3e21a443b213537"
                                ).build()
                            it.proceed(headerCode)
                        })
                    .build()


            val retrofit = Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}