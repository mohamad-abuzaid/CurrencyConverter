package com.challenge.data.di.network

import com.challenge.domain.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  private const val INTERCEPTOR_LOGGING_NAME = "INTERCEPTOR_LOGGING"

  @Provides
  @Named(INTERCEPTOR_LOGGING_NAME)
  fun provideHttpLoggingInterceptor(): Interceptor {
    return if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      }
    } else {
      noOpInterceptor()
    }
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(
    @Named(INTERCEPTOR_LOGGING_NAME) loggingInterceptor: Interceptor
  ): OkHttpClient {
    return OkHttpClient
      .Builder()
      .apply {
        addNetworkInterceptor(loggingInterceptor)
      }
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(
    okHttpClient: OkHttpClient
  ): Retrofit {
    val gsonParser = Gson()

    return Retrofit
      .Builder()
      .addConverterFactory(GsonConverterFactory.create(gsonParser))
      .baseUrl(BuildConfig.BASE_URL)
      .client(okHttpClient)
      .build()
  }

  private fun noOpInterceptor(): Interceptor {
    return Interceptor { chain ->
      chain.proceed(chain.request())
    }
  }
}
