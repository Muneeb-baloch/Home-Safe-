package com.muneeb.homesafe.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.muneeb.homesafe.domain.model.HomeSafeNotification
import com.muneeb.homesafe.domain.repository.NotificationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

private const val NOTIFICATIONS_COLLECTION = "notifications"

@Singleton
class FirestoreNotificationRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : NotificationRepository {

    override fun observeNotificationsForOuting(outingId: String): Flow<List<HomeSafeNotification>> = callbackFlow {
        val registration = firestore.collection(NOTIFICATIONS_COLLECTION)
            .whereEqualTo("outingId", outingId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val notifications = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(HomeSafeNotification::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(notifications)
            }
        awaitClose { registration.remove() }
    }

    override fun observeNotificationsForRecipient(recipientId: String): Flow<List<HomeSafeNotification>> = callbackFlow {
        val registration = firestore.collection(NOTIFICATIONS_COLLECTION)
            .whereEqualTo("recipientId", recipientId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val notifications = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(HomeSafeNotification::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(notifications)
            }
        awaitClose { registration.remove() }
    }
}
