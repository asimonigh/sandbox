package com.simonigh.cutekitten.data.remote

import com.simonigh.cutekitten.data.remote.dto.CatDto
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber

interface CatsApi {
    
    @GET("images/search")
    fun getCats(@Query("limit") nbCats: Int, @Query("page") page: Int): Single<List<CatDto>>
    
    companion object {
        
        const val BASE_URL = "https://api.thecatapi.com/v1/"
        private const val API_KEY = "d48196ea-7e15-4d0f-8c86-8d57415eab03"
        
        fun create(): CatsApi {
            val httpClient = OkHttpClient().newBuilder()
                .addInterceptor { chain ->
                    Timber.d("Call API: ${chain.request().url()}")
                    val newRequest = chain.request().newBuilder()
                        .addHeader("x-api-key", API_KEY).build()
                    chain.proceed(newRequest)
                }.build()
            
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build().create(CatsApi::class.java)
            
        }
    }
}