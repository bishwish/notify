package com.interview.whoptest.di

import com.interview.whoptest.data.repository.NotificationsRepositoryImpl
import com.interview.whoptest.domain.repository.NotificationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideNotificationsRepository(impl: NotificationsRepositoryImpl): NotificationsRepository
}