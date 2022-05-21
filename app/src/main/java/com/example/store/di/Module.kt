package com.example.store.di

import com.example.store.data.Repository
import com.example.store.data.source.DataSource
import com.example.store.data.source.IStoreService
import com.example.store.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteProductDataSource

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideClient() = OkHttpClient.Builder()
        .addInterceptor(provideInterceptor())
        .build()

    @Singleton
    @Provides
    fun provideIStoreService(): IStoreService = provideRetrofit().create(IStoreService::class.java)

    @Singleton
    @RemoteProductDataSource
    @Provides
    fun provideRemoteDataSource(): DataSource = RemoteDataSource(provideIStoreService())
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Provides
    @Singleton
    fun provideRepository(
        @NetworkModule.RemoteProductDataSource remoteDataSource: RemoteDataSource
    ) = Repository(remoteDataSource)
}