package com.muneeb.homesafe.di

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.userIdentityDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_identity")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideUserIdentityDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.userIdentityDataStore
}
