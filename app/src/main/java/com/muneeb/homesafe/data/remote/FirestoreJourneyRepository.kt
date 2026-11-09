package com.muneeb.homesafe.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.muneeb.homesafe.domain.model.Journey
import com.muneeb.homesafe.domain.model.JourneyStatus
import com.muneeb.homesafe.domain.repository.JourneyRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

private const val JOURNEYS_COLLECTION = "journeys"

@Singleton
class FirestoreJourneyRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : JourneyRepository {

    private fun journeyDoc(journeyId: String) = firestore.collection(JOURNEYS_COLLECTION).document(journeyId)

    override suspend fun createJourney(outingId: String, userId: String): Journey {
        val journey = Journey(
            outingId = outingId,
            userId = userId,
            status = JourneyStatus.OUT,
            startedAt = Date()
        )
        val docRef = firestore.collection(JOURNEYS_COLLECTION).document()
        docRef.set(journey).await()
        return journey.copy(id = docRef.id)
    }

    override fun observeJourneysForOuting(outingId: String): Flow<List<Journey>> = callbackFlow {
        val registration = firestore.collection(JOURNEYS_COLLECTION)
            .whereEqualTo("outingId", outingId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val journeys = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Journey::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(journeys)
            }
        awaitClose { registration.remove() }
    }

    override fun observeJourney(journeyId: String): Flow<Journey?> = callbackFlow {
        val registration = journeyDoc(journeyId).addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            trySend(snapshot?.toObject(Journey::class.java)?.copy(id = snapshot.id))
        }
        awaitClose { registration.remove() }
    }

    override suspend fun findJourney(outingId: String, userId: String): Journey? {
        val snapshot = firestore.collection(JOURNEYS_COLLECTION)
            .whereEqualTo("outingId", outingId)
            .whereEqualTo("userId", userId)
            .limit(1)
            .get()
            .await()
        val document = snapshot.documents.firstOrNull() ?: return null
        return document.toObject(Journey::class.java)?.copy(id = document.id)
    }

    override suspend fun updateStatus(journeyId: String, status: JourneyStatus) {
        journeyDoc(journeyId).update("status", status.name).await()
    }

    override suspend fun markArrived(journeyId: String) {
        journeyDoc(journeyId).update(
            mapOf(
                "status" to JourneyStatus.ARRIVED.name,
                "arrivedAt" to Date()
            )
        ).await()
    }
}
