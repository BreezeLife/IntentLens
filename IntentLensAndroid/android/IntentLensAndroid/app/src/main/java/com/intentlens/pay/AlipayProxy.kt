package com.intentlens.pay

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlin.random.Random

object AlipayProxy {
  fun launchPayment(context: Context, merchantName: String, amountUsd: Double): String {
    val demoUrl = "https://example.com/alipay-proxy?m=${Uri.encode(merchantName)}&a=$amountUsd"
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(demoUrl)))
    return "ALIPAY_PROXY_${Random.nextInt(100000, 999999)}"
  }
}
