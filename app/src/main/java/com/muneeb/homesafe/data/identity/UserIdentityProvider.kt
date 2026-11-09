package com.muneeb.homesafe.data.identity

interface UserIdentityProvider {
    suspend fun currentUid(): String
}
