package com.jetbrains.kmmktor2

import com.github.aakira.napier.Napier
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class IPResponse(
    val ip: String,
    val xForwardedFor: String
)

class Greeting {
    private val httpClient = httpClient() {
        install(Logging) {
            level = LogLevel.HEADERS
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(tag = "HTTP Client", message = message)
                }
            }
        }
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }.also { initLogger() }

    @Throws(Throwable::class)
    suspend fun greeting(): String {
        return getHello().ip
    }

    private suspend fun getHello(): IPResponse {
        return httpClient.get("http://awstest-balancer-1233234915.us-east-2.elb.amazonaws.com/awstest-service/")
    }
}