package com.intentlens.data

data class Merchant(
  val id: String,
  val name: String,
  val suggestedAmountUsd: Double
)

data class IntentRequest(
  val merchantId: String,
  val merchantName: String,
  val contextHint: String? = "checkout"
)

data class PaymentIntent(
  val intent: String,
  val merchant: String,
  val amount_usd: Double,
  val confidence: Double,
  val reason: String
)

data class VoiceConfirmResult(
  val transcript: String,
  val voiceprintScore: Double?,
  val passed: Boolean
)

sealed class UiStatus {
  data object Idle : UiStatus()
  data object Loading : UiStatus()
  data class Proposed(val pi: PaymentIntent) : UiStatus()
  data class Confirming(val pi: PaymentIntent, val voice: VoiceConfirmResult? = null) : UiStatus()
  data class Executing(val pi: PaymentIntent) : UiStatus()
  data class Success(val txRef: String) : UiStatus()
  data class Error(val message: String) : UiStatus()
}

data class AccessCheckRequest(
  val chain: String = "sepolia",
  val contract: String,
  val address: String
)

data class AccessCheckResult(
  val hasAccess: Boolean,
  val balance: String,
  val contract: String,
  val address: String
)
