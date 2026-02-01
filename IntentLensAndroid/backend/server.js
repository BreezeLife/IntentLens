import express from "express";
import cors from "cors";
import morgan from "morgan";
import dotenv from "dotenv";

dotenv.config();

const app = express();
app.use(cors());
app.use(express.json({ limit: "2mb" }));
app.use(morgan("dev"));

const PORT = process.env.PORT || 8787;

function pickAmount(name) {
  const map = { "7-Eleven": 3.5, "Local Cafe": 5.0, "Starbucks": 6.5 };
  return map[name] ?? 4.2;
}

app.get("/", (req, res) => res.send("IntentLens backend is running."));

app.post("/api/intent", async (req, res) => {
  const { merchantName, contextHint } = req.body || {};
  const amount = pickAmount(merchantName);
  return res.json({
    intent: "pay",
    merchant: merchantName || "Unknown Merchant",
    amount_usd: amount,
    confidence: 0.86,
    reason: contextHint || "User is at checkout wearing AI glasses"
  });
});

app.post("/api/voice/confirm", async (req, res) => {
  const { transcript, mode } = req.body || {};
  const t = String(transcript || "").toLowerCase();

  // --- 3rd-party ASR integration placeholder ---
  // In production, call your ASR provider here and optionally run voiceprint matching.
  const passed = /(confirm|yes|pay|ok|okay|sure)/.test(t) && !/(cancel|no|stop)/.test(t);

  // Placeholder voiceprint score (0..1)
  const voiceprintScore = mode === "asr_plus_voiceprint" ? 0.82 : null;

  return res.json({
    transcript: transcript || "",
    voiceprintScore,
    passed
  });
});

app.post("/api/log", async (req, res) => {
  console.log("[LOG]", JSON.stringify(req.body || {}, null, 2));
  return res.json({ ok: "true", ref: String((req.body || {}).txRef || "") });
});

app.post("/api/access/check", async (req, res) => {
  const { contract, address } = req.body || {};
  const addr = String(address || "").toLowerCase();
  const hasAccess = addr.endsWith("1"); // demo trick
  return res.json({
    hasAccess,
    balance: hasAccess ? "1" : "0",
    contract: contract || "",
    address: address || ""
  });
});

app.listen(PORT, () => {
  console.log(`IntentLens backend listening on http://localhost:${PORT}`);
});
