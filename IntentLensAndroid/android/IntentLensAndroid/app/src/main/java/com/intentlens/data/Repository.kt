package com.intentlens.data

class Repository(private val api: IntentApi) {

  suspend fun propose(merchant: Merchant): PaymentIntent {
    return api.generateIntent(
      IntentRequest(
        merchantId = merchant.id,
        merchantName = merchant.name,
        contextHint = "user is at checkout wearing AI glasses"
      )
    )
  }

  suspend fun voiceConfirm(transcript: String): VoiceConfirmResult {
    return api.confirmVoice(
      mapOf(
        "transcript" to transcript,
        "mode" to "asr_only"
      )
    )
  }

  suspend fun log(txRef: String, merchant: String, amount: Double) {
    runCatching {
      api.logOutcome(
        mapOf(
          "txRef" to txRef,
          "merchant" to merchant,
          "amount_usd" to amount,
          "device" to "ai_glasses_hud_mock"
        )
      )
    }
  }

  suspend fun checkAccess(contract: String, address: String): AccessCheckResult {
    return api.checkAccess(AccessCheckRequest(contract = contract, address = address))
  }
}
