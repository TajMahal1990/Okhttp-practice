package com.example.okhttppractice2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class NetworkClient {

    private val client = OkHttpClient()

    suspend fun fenchData(url: String): JSONObject? = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(url)
            .build()

        return@withContext try {
            client.newCall(request).execute().use { response ->
                if(!response.isSuccessful) {
                    null
                } else {
                    response.body?.string().let {
                        JSONObject(it)
                    }
                }
            }
        } catch (e: IOException) {
            null
        }

    }
}