package com.muneeb.homesafe.domain.repository

import com.muneeb.homesafe.domain.model.Outing
import com.muneeb.homesafe.domain.model.OutingStatus
import kotlinx.coroutines.flow.Flow

interface OutingRepository {
    suspend fun createOuting(createdBy: String): Outing
    suspend fun findActiveOutingByCode(code: String): Outing?
    suspend fun getOuting(outingId: String): Outing?
    fun observeOuting(outingId: String): Flow<Outing?>
    suspend fun addMember(outingId: String, uid: String)
    suspend fun updateStatus(outingId: String, status: OutingStatus)
}
