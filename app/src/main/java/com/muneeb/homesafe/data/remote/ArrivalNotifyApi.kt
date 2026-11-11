package com.muneeb.homesafe.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArrivalNotifyApi @Inject constructor() {

    suspend fun notifyArrival(journeyId: String): Boolean = withContext(Dispatchers.IO) {
        val connection = (URL("${WorkerConfig.BASE_URL}/notify-arrival").openConnection() as HttpURLConnection)
        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.connectTimeout = 10_000
            connection.readTimeout = 10_000

            val body = JSONObject().put("journeyId", journeyId).toString()
            connection.outputStream.use { it.write(body.toByteArray()) }

            connection.responseCode in 200..299
        } catch (e: Exception) {
            false
        } finally {
            connection.disconnect()
        }
    }
}
