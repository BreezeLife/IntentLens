package com.intentlens.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.intentlens.data.UiStatus
import com.intentlens.vm.MainViewModel
import com.intentlens.voice.VoiceCapture

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HudScreen(vm: MainViewModel) {
  val ctx = LocalContext.current
  val status by vm.status.collectAsState()
  val voice = remember { VoiceCapture(ctx) }

  val micPerm = rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)

  Surface(color = HudColors.Bg) {
    Column(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text("IntentLens", color = HudColors.Cyan, style = MaterialTheme.typography.titleLarge)
      Text("AI Glasses HUD (Phone Mock) — Wearable-first AI × Blockchain", color = HudColors.Muted)

      MerchantPicker(vm)

      HudPanel(title = "GLASSES HUD") {
        when (status) {
          UiStatus.Idle -> {
            Text("Idle — choose a merchant", color = HudColors.Muted)
            Text("AI proposes. Humans decide. Blockchain remembers.", color = HudColors.Cyan)
          }
          UiStatus.Loading -> Text("Thinking… (LLM intent inference)", color = HudColors.Muted)
          is UiStatus.Proposed -> {
            val pi = (status as UiStatus.Proposed).pi
            HudKeyValue("Context", "Checkout / Merchant")
            HudKeyValue("Merchant", pi.merchant)
            HudKeyValue("Amount", "$${pi.amount_usd}")
            HudKeyValue("Confidence", "${(pi.confidence * 100).toInt()}%")
            Text("Status: PROPOSED — awaiting voice confirm", color = HudColors.Cyan)
          }
          is UiStatus.Confirming -> {
            val s = status as UiStatus.Confirming
            HudKeyValue("Confirm", "${s.pi.merchant} — $${s.pi.amount_usd}")
            HudKeyValue("Voice", s.voice?.transcript ?: "(tap Speak)")
            HudKeyValue("Voiceprint", s.voice?.voiceprintScore?.toString() ?: "(n/a)")
            HudKeyValue("Passed", (s.voice?.passed ?: false).toString(), if (s.voice?.passed == true) HudColors.Success else HudColors.Danger)
            Text("Rule: AI proposes. Human confirms.", color = HudColors.Cyan)
          }
          is UiStatus.Executing -> Text("Executing… (Alipay proxy)", color = HudColors.Muted)
          is UiStatus.Success -> {
            val s = status as UiStatus.Success
            Text("Paid ✓", color = HudColors.Success)
            Text("Ref: ${s.txRef}", color = HudColors.Muted)
          }
          is UiStatus.Error -> {
            val s = status as UiStatus.Error
            Text("Error: ${s.message}", color = HudColors.Danger)
          }
        }
      }

      when (status) {
        is UiStatus.Proposed -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Button(onClick = vm::startConfirm) { Text("Start Voice Confirm") }
          OutlinedButton(onClick = vm::reset) { Text("Reset") }
        }
        is UiStatus.Confirming -> {
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
              if (!micPerm.hasPermission) {
                micPerm.launchPermissionRequest()
              } else {
                voice.start(
                  onResult = { vm.submitVoiceTranscript(it) },
                  onError = { vm.submitVoiceTranscript("ASR_ERROR_$it") }
                )
              }
            }) { Text("Speak") }
            OutlinedButton(onClick = { voice.stop() }) { Text("Stop") }
            Button(onClick = { vm.execute(ctx) }) { Text("Confirm & Execute") }
          }
        }
        is UiStatus.Success, is UiStatus.Error -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Button(onClick = vm::reset) { Text("Reset") }
        }
        else -> {}
      }

      AccessPanel(vm)
    }
  }
}

@Composable
private fun MerchantPicker(vm: MainViewModel) {
  var expanded by remember { mutableStateOf(false) }
  var selectedName by remember { mutableStateOf("Select merchant") }

  Box {
    OutlinedButton(onClick = { expanded = true }) { Text(selectedName) }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
      vm.merchants.forEach { m ->
        DropdownMenuItem(
          text = { Text("${m.name}  ($${m.suggestedAmountUsd})") },
          onClick = {
            expanded = false
            selectedName = m.name
            vm.selectMerchant(m)
          }
        )
      }
    }
  }
}

@Composable
private fun AccessPanel(vm: MainViewModel) {
  var msg by remember { mutableStateOf("") }
  var err by remember { mutableStateOf("") }

  HudPanel(title = "ACCESS CHECK (NFT/DAO)") {
    OutlinedTextField(
      value = vm.demoNftContract.collectAsState().value,
      onValueChange = { vm.demoNftContract.value = it },
      label = { Text("ERC-721 contract (Sepolia)") },
      singleLine = true
    )
    OutlinedTextField(
      value = vm.demoAddress.collectAsState().value,
      onValueChange = { vm.demoAddress.value = it },
      label = { Text("Wallet address") },
      singleLine = true
    )
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      Button(onClick = {
        msg = ""; err = ""
        vm.checkAccess(onResult = { msg = it }, onError = { err = it })
      }) { Text("Check") }
    }
    if (msg.isNotBlank()) Text(msg, color = HudColors.Success)
    if (err.isNotBlank()) Text(err, color = HudColors.Danger)
  }
}
