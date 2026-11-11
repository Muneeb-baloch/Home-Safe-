package com.muneeb.homesafe.di

import com.muneeb.homesafe.data.identity.DataStoreUserIdentityProvider
import com.muneeb.homesafe.data.identity.UserIdentityProvider
import com.muneeb.homesafe.data.remote.FirestoreJourneyRepository
import com.muneeb.homesafe.data.remote.FirestoreNotificationRepository
import com.muneeb.homesafe.data.remote.FirestoreOutingRepository
import com.muneeb.homesafe.data.remote.FirestoreUserRepository
import com.muneeb.homesafe.domain.repository.JourneyRepository
import com.muneeb.homesafe.domain.repository.NotificationRepository
import com.muneeb.homesafe.domain.repository.OutingRepository
import com.muneeb.homesafe.domain.repository.UserRepository
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
    abstract fun bindUserRepository(impl: FirestoreUserRepository): UserRepository

    @Binds
    @Singleton
    abstract fun bindOutingRepository(impl: FirestoreOutingRepository): OutingRepository

    @Binds
    @Singleton
    abstract fun bindJourneyRepository(impl: FirestoreJourneyRepository): JourneyRepository

    @Binds
    @Singleton
    abstract fun bindUserIdentityProvider(impl: DataStoreUserIdentityProvider): UserIdentityProvider

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(impl: FirestoreNotificationRepository): NotificationRepository
}
