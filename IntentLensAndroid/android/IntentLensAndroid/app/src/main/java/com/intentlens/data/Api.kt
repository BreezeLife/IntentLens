package com.intentlens.data

import retrofit2.http.Body
import retrofit2.http.POST

interface IntentApi {
  @POST("/api/intent")
  suspend fun generateIntent(@Body req: IntentRequest): PaymentIntent

  @POST("/api/log")
  suspend fun logOutcome(@Body body: Map<String, Any>): Map<String, String>

  @POST("/api/voice/confirm")
  suspend fun confirmVoice(@Body body: Map<String, Any>): VoiceConfirmResult

  @POST("/api/access/check")
  suspend fun checkAccess(@Body req: AccessCheckRequest): AccessCheckResult
}
