package ru.pakarpichev.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pakarpichev.weatherapp.data.AppStatusImpl
import ru.pakarpichev.weatherapp.domain.repository.AppStatus
import ru.pakarpichev.weatherapp.domain.repository.RetrofitRepository
import ru.pakarpichev.weatherapp.presentation.useCase.AppStatusUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): RetrofitRepository =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitRepository::class.java)

    @Singleton
    @Provides
    fun provideRepository(): AppStatus = AppStatusImpl()

    @Singleton
    @Provides
    fun provideUseCase(repository: AppStatus) = AppStatusUseCase(repository)
}