package org.itmo.itmoevent

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.itmo.itmoevent.config.NetworkSettings
import org.itmo.itmoevent.network.api.AuthControllerApi
import org.itmo.itmoevent.network.api.EventControllerApi
import org.itmo.itmoevent.network.api.NotificationControllerApi
import org.itmo.itmoevent.network.api.PlaceControllerApi
import org.itmo.itmoevent.network.api.ProfileControllerApi
import org.itmo.itmoevent.network.api.RoleControllerApi
import org.itmo.itmoevent.network.api.TaskControllerApi
import org.itmo.itmoevent.network.auth.AuthAuthenticator
import org.itmo.itmoevent.network.auth.HttpBearerAuth
import org.itmo.itmoevent.network.infrastructure.Serializer.kotlinxSerializationJson
import org.itmo.itmoevent.network.util.TokenManager
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: HttpBearerAuth,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): HttpBearerAuth {
        return HttpBearerAuth(tokenManager)
    }

    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator =
        AuthAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        val converterFactories: List<Converter.Factory> = listOf(
            ScalarsConverterFactory.create(),
            kotlinxSerializationJson.asConverterFactory("application/json".toMediaType()),
        )
        return Retrofit.Builder()
            .baseUrl(NetworkSettings.BASE_URL)
            .apply {
                converterFactories.forEach {
                    addConverterFactory(it)
                }
            }
    }

    @Singleton
    @Provides
    fun provideAuthAPIService(retrofit: Retrofit.Builder): AuthControllerApi =
        retrofit
            .build()
            .create(AuthControllerApi::class.java)

    @Singleton
    @Provides
    fun provideProfileAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): ProfileControllerApi =
        retrofit
            .client(okHttpClient)
            .build()
            .create(ProfileControllerApi::class.java)


    @Singleton
    @Provides
    fun providePlaceAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): PlaceControllerApi =
        retrofit
            .client(okHttpClient)
            .build()
            .create(PlaceControllerApi::class.java)

    @Singleton
    @Provides
    fun provideNotificationAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): NotificationControllerApi =
        retrofit
            .client(okHttpClient)
            .build()
            .create(NotificationControllerApi::class.java)

    @Singleton
    @Provides
    fun provideEventAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): EventControllerApi =
        retrofit
            .client(okHttpClient)
            .build()
            .create(EventControllerApi::class.java)

    @Singleton
    @Provides
    fun provideRoleAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): RoleControllerApi =
        retrofit
            .client(okHttpClient)
            .build()
            .create(RoleControllerApi::class.java)

    @Singleton
    @Provides
    fun provideTaskAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): TaskControllerApi =
        retrofit
            .client(okHttpClient)
            .build()
            .create(TaskControllerApi::class.java)
}