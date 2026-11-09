package com.muneeb.homesafe.data.remote

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.muneeb.homesafe.domain.model.Outing
import com.muneeb.homesafe.domain.model.OutingStatus
import com.muneeb.homesafe.domain.repository.OutingRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

private const val OUTINGS_COLLECTION = "outings"
private const val CODE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
private const val CODE_LENGTH = 4
private const val MAX_CODE_ATTEMPTS = 5
private val OUTING_LIFETIME_MILLIS = TimeUnit.HOURS.toMillis(12)

@Singleton
class FirestoreOutingRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : OutingRepository {

    private fun outingDoc(outingId: String) = firestore.collection(OUTINGS_COLLECTION).document(outingId)

    override suspend fun createOuting(createdBy: String): Outing {
        var code = generateCode()
        var attempts = 1
        while (attempts < MAX_CODE_ATTEMPTS && findActiveOutingByCode(code) != null) {
            code = generateCode()
            attempts++
        }

        val now = Date()
        val outing = Outing(
            code = code,
            createdBy = createdBy,
            members = listOf(createdBy),
            createdAt = now,
            expiresAt = Date(now.time + OUTING_LIFETIME_MILLIS),
            status = OutingStatus.ACTIVE
        )

        val docRef = firestore.collection(OUTINGS_COLLECTION).document()
        docRef.set(outing).await()
        return outing.copy(id = docRef.id)
    }

    override suspend fun findActiveOutingByCode(code: String): Outing? {
        val snapshot = firestore.collection(OUTINGS_COLLECTION)
            .whereEqualTo("code", code)
            .whereEqualTo("status", OutingStatus.ACTIVE.name)
            .limit(1)
            .get()
            .await()
        val document = snapshot.documents.firstOrNull() ?: return null
        return document.toObject(Outing::class.java)?.copy(id = document.id)
    }

    override suspend fun getOuting(outingId: String): Outing? {
        val snapshot = outingDoc(outingId).get().await()
        return snapshot.toObject(Outing::class.java)?.copy(id = snapshot.id)
    }

    override fun observeOuting(outingId: String): Flow<Outing?> = callbackFlow {
        val registration = outingDoc(outingId).addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            trySend(snapshot?.toObject(Outing::class.java)?.copy(id = snapshot.id))
        }
        awaitClose { registration.remove() }
    }

    override suspend fun addMember(outingId: String, uid: String) {
        outingDoc(outingId).update("members", FieldValue.arrayUnion(uid)).await()
    }

    override suspend fun updateStatus(outingId: String, status: OutingStatus) {
        outingDoc(outingId).update("status", status.name).await()
    }

    private fun generateCode(): String = buildString {
        repeat(CODE_LENGTH) { append(CODE_CHARS[Random.nextInt(CODE_CHARS.length)]) }
    }
}
