package com.muneeb.homesafe.domain.repository

import com.muneeb.homesafe.domain.model.Journey
import com.muneeb.homesafe.domain.model.JourneyStatus
import kotlinx.coroutines.flow.Flow

interface JourneyRepository {
    suspend fun createJourney(outingId: String, userId: String): Journey
    fun observeJourneysForOuting(outingId: String): Flow<List<Journey>>
    fun observeJourney(journeyId: String): Flow<Journey?>
    suspend fun findJourney(outingId: String, userId: String): Journey?
    suspend fun updateStatus(journeyId: String, status: JourneyStatus)
    suspend fun markArrived(journeyId: String)
}
