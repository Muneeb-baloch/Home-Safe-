package com.muneeb.homesafe.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.muneeb.homesafe.domain.model.User
import com.muneeb.homesafe.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private const val USERS_COLLECTION = "users"

@Singleton
class FirestoreUserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    private fun userDoc(uid: String) = firestore.collection(USERS_COLLECTION).document(uid)

    override suspend fun ensureUserExists(uid: String) {
        userDoc(uid).set(User(uid = uid), SetOptions.mergeFields("uid")).await()
    }

    override suspend fun getUser(uid: String): User? {
        return userDoc(uid).get().await().toObject(User::class.java)
    }

    override fun observeUser(uid: String): Flow<User?> = callbackFlow {
        val registration = userDoc(uid).addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            trySend(snapshot?.toObject(User::class.java))
        }
        awaitClose { registration.remove() }
    }

    override suspend fun updateName(uid: String, name: String) {
        userDoc(uid).update("name", name).await()
    }

    override suspend fun addFcmToken(uid: String, token: String) {
        userDoc(uid).update("fcmTokens", FieldValue.arrayUnion(token)).await()
    }
}
