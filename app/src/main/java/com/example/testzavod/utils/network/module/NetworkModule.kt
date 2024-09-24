package com.example.testzavod.utils.network.module

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.testzavod.data.remote.RegisterApiService
import com.example.testzavod.domain.model.token.RefreshToken
import com.example.testzavod.utils.Constants.TOKEN_REFRESH_FAILED_ACTION
import com.example.testzavod.utils.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Singleton
    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext context: Context,
        sharedPref: SharedPref,
        apiServiceProvider: Provider<RegisterApiService>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()

                val accessToken = sharedPref.accessToken
                val requestWithToken = if (!accessToken.isNullOrEmpty()) {
                    originalRequest.newBuilder()
                        .header("Authorization", "Bearer $accessToken")
                        .build()
                } else {
                    originalRequest
                }

                var response: Response = chain.proceed(requestWithToken)

                if (response.code == 401) {
                    val newAccessToken = refreshAccessToken(apiServiceProvider, sharedPref)
                    if (newAccessToken != null) {
                        val newRequest = originalRequest.newBuilder()
                            .header("Authorization", "Bearer $newAccessToken")
                            .build()
                        response.close()
                        response = chain.proceed(newRequest)
                    } else {
                        // Отправляем сообщение, если обновление токена не удалось
                        LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(Intent(TOKEN_REFRESH_FAILED_ACTION))
                    }
                }

                response
            }
            .build()
    }


    private fun refreshAccessToken(apiServiceProvider: Provider<RegisterApiService>, sharedPref: SharedPref): String? {
        val apiService = apiServiceProvider.get()
        val refreshToken = sharedPref.refreshToken
        return if (refreshToken != null) {
            val refreshResponse = apiService.sendRefreshToken(RefreshToken(
                refreshToken = refreshToken
            )).execute()
            if (refreshResponse.isSuccessful) {
                val newAccessToken = refreshResponse.body()?.accessToken
                sharedPref.accessToken = newAccessToken
                newAccessToken
            } else {
                null
            }
        } else {
            null
        }
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://plannerok.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }

}
