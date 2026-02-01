package com.intentlens.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intentlens.data.Merchant
import com.intentlens.data.Repository
import com.intentlens.data.UiStatus
import com.intentlens.pay.AlipayProxy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository) : ViewModel() {

  val merchants = listOf(
    Merchant("m_711", "7-Eleven", 3.50),
    Merchant("m_cafe", "Local Cafe", 5.00),
    Merchant("m_starbucks", "Starbucks", 6.50)
  )

  val demoNftContract = MutableStateFlow("0x0000000000000000000000000000000000000000")
  val demoAddress = MutableStateFlow("0x0000000000000000000000000000000000000000")

  private val _status = MutableStateFlow<UiStatus>(UiStatus.Idle)
  val status: StateFlow<UiStatus> = _status

  fun selectMerchant(m: Merchant) {
    _status.value = UiStatus.Loading
    viewModelScope.launch {
      runCatching { repo.propose(m) }
        .onSuccess { _status.value = UiStatus.Proposed(it) }
        .onFailure { _status.value = UiStatus.Error("Intent API failed: ${it.message}") }
    }
  }

  fun startConfirm() {
    val s = _status.value
    if (s is UiStatus.Proposed) _status.value = UiStatus.Confirming(s.pi)
  }

  fun submitVoiceTranscript(transcript: String) {
    val s = _status.value
    if (s is UiStatus.Confirming) {
      viewModelScope.launch {
        runCatching { repo.voiceConfirm(transcript) }
          .onSuccess { _status.value = UiStatus.Confirming(s.pi, voice = it) }
          .onFailure { _status.value = UiStatus.Error("Voice confirm failed: ${it.message}") }
      }
    }
  }

  fun execute(context: Context) {
    val s = _status.value
    if (s is UiStatus.Confirming) {
      val passed = s.voice?.passed ?: false
      if (!passed) {
        _status.value = UiStatus.Error("Voice confirmation not passed.")
        return
      }
      _status.value = UiStatus.Executing(s.pi)
      val txRef = AlipayProxy.launchPayment(context, s.pi.merchant, s.pi.amount_usd)
      viewModelScope.launch {
        repo.log(txRef, s.pi.merchant, s.pi.amount_usd)
        _status.value = UiStatus.Success(txRef)
      }
    }
  }

  fun reset() { _status.value = UiStatus.Idle }

  fun checkAccess(onResult: (String) -> Unit, onError: (String) -> Unit) {
    val contract = demoNftContract.value.trim()
    val addr = demoAddress.value.trim()
    if (contract.isBlank() || addr.isBlank()) {
      onError("Please input contract + address.")
      return
    }
    viewModelScope.launch {
      runCatching { repo.checkAccess(contract, addr) }
        .onSuccess { r ->
          onResult(if (r.hasAccess) "Access Granted (balance=${r.balance})" else "Access Denied (balance=${r.balance})")
        }
        .onFailure { onError("Access check failed: ${it.message}") }
    }
  }
}
