package com.muneeb.homesafe.data.identity

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.muneeb.homesafe.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private val STUB_UID_KEY = stringPreferencesKey("stub_uid")

@Singleton
class DataStoreUserIdentityProvider @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val userRepository: UserRepository
) : UserIdentityProvider {

    private val mutex = Mutex()

    override suspend fun currentUid(): String {
        dataStore.data.first()[STUB_UID_KEY]?.let { return it }

        return mutex.withLock {
            dataStore.data.first()[STUB_UID_KEY]?.let { return it }

            val uid = UUID.randomUUID().toString()
            dataStore.edit { it[STUB_UID_KEY] = uid }
            userRepository.ensureUserExists(uid)
            uid
        }
    }
}
