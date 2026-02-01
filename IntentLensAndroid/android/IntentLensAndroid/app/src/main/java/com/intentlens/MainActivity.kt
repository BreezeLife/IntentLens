package com.intentlens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.intentlens.data.IntentApi
import com.intentlens.data.Repository
import com.intentlens.ui.HudScreen
import com.intentlens.ui.IntentLensTheme
import com.intentlens.vm.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val log = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    val client = OkHttpClient.Builder().addInterceptor(log).build()

    val retrofit = Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_BASE_URL)
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()

    val api = retrofit.create(IntentApi::class.java)
    val repo = Repository(api)
    val vm = MainViewModel(repo)

    setContent {
      IntentLensTheme {
        HudScreen(vm = vm)
      }
    }
  }
}
