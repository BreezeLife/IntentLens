# IntentLensAndroid Pack

This pack includes:
- **Android (Kotlin + Jetpack Compose)** HUD mock for AI glasses
- **Node/Express backend**: mock LLM intent + ASR/voiceprint placeholder + access check

## File structure
```
IntentLensAndroid/
  android/IntentLensAndroid/        # Open this in Android Studio
    app/src/main/java/com/intentlens/
      MainActivity.kt
      ui/
      data/
      vm/
      voice/
      pay/
  backend/                          # Run node server here
```

## Run backend
```bash
cd backend
npm i
cp .env.example .env
npm start
```

## Run Android
1. Open `android/IntentLensAndroid` in Android Studio
2. Run on emulator (backend base url is `http://10.0.2.2:8787`)
3. Flow:
   - Select merchant -> AI proposes -> Start Voice Confirm -> Speak "confirm" -> Confirm & Execute
4. Access Check:
   - address ends with '1' -> Access Granted (demo trick)

## Notes
- Demo execution uses **Alipay proxy** (opens a link) for stability.
- Voice: local Android ASR captures transcript; server simulates 3rd-party ASR + optional voiceprint score.
- Blockchain: server logs; replace /api/log with Ethereum tx/event later.
