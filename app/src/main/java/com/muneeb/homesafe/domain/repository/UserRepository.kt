package com.muneeb.homesafe.domain.repository

import com.muneeb.homesafe.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun ensureUserExists(uid: String)
    suspend fun getUser(uid: String): User?
    fun observeUser(uid: String): Flow<User?>
    suspend fun updateName(uid: String, name: String)
    suspend fun addFcmToken(uid: String, token: String)
}
