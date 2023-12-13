package com.moetaz.mazaady.di


import com.moetaz.mazaady.data.remote.MainService
import com.moetaz.mazaady.data.repository.MainRepositoryImpl
import com.moetaz.mazaady.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        api: MainService,
    ): MainRepository = MainRepositoryImpl(api)


}