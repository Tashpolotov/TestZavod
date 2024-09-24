package com.example.testzavod.data.di

import com.example.testzavod.data.remote.AuthApiService
import com.example.testzavod.data.remote.ProfileApiService
import com.example.testzavod.data.remote.RegisterApiService
import com.example.testzavod.data.repository.AuthRepositoryImpl
import com.example.testzavod.data.repository.ProfileRepositoryImpl
import com.example.testzavod.data.repository.RegisterRepoImpl
import com.example.testzavod.domain.repository.AuthRepository
import com.example.testzavod.domain.repository.ProfileRepository
import com.example.testzavod.domain.repository.RegisterRepository
import com.example.testzavod.domain.repository.Repository
import com.example.testzavod.domain.usecase.AuthUseCase
import com.example.testzavod.domain.usecase.ProfileUseCase
import com.example.testzavod.domain.usecase.RegisterUseCase
import com.example.testzavod.domain.usecase.UseCase
import com.example.testzavod.utils.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit):RegisterApiService{
        return retrofit.create(RegisterApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterRepositoryImpl(
        apiService: RegisterApiService, sharedPref: SharedPref):RegisterRepository{
        return RegisterRepoImpl(apiService, sharedPref)
    }

    @Provides
    @Singleton
    fun providesAuthApiService(retrofit: Retrofit):AuthApiService{
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(
        apiService: AuthApiService, sharedPref: SharedPref):AuthRepository{
        return AuthRepositoryImpl(apiService, sharedPref)
    }


    @Provides
    @Singleton
    fun provideProfileApiService(retrofit: Retrofit):ProfileApiService{
        return retrofit.create(ProfileApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepositoryImpl(
        apiService: ProfileApiService):ProfileRepository{
        return ProfileRepositoryImpl(apiService)
    }


    @Provides
    @Singleton
    fun provideUseCase(repository: Repository):UseCase{
        return UseCase(
            authUseCase = AuthUseCase(repository.authRepository),
            registerUseCase = RegisterUseCase(repository.registerRepository),
            profileUseCase = ProfileUseCase(repository.profileRepository)
        )
    }
}