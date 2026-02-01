# IntentLens - Wearable AI Wallet
IntentLens is a wearable-first AI wallet designed for AI glasses, enabling safe, hands-free blockchain interactions in the physical world.

It uses AI to understand real-world context, voice to confirm human intent, and blockchain to record immutable outcomes—while keeping sensitive data off-chain.
Instead of navigating complex wallet apps, users interact naturally through their glasses: AI proposes actions, humans decide, and blockchain remembers.

IntentLens makes payments, access verification, and on-chain actions safer, simpler, and more human-centered for always-on wearable environments.

## Problem

Blockchain systems are cryptographically secure, but **they are not designed for how people act in the real world — especially when using wearable devices like AI glasses**.

Most wallets and dApps assume users are:
- Sitting still
- Looking at a phone or computer screen
- Carefully reading and clicking through transactions

However, many real-world actions happen **while users are moving, interacting with physical spaces, or using AI glasses as their primary interface**.  
This mismatch creates several problems:

1. **Unsafe interactions in wearable scenarios**  
   Traditional wallet UX relies on visual-heavy, multi-step confirmations that are unsuitable for glasses-based, eyes-up interactions.

2. **High cognitive load**  
   Switching between the physical world and complex wallet screens increases the risk of mistakes, especially during payments or access checks.

3. **Poor alignment between cryptographic security and human intent**  
   Blockchain secures execution, but it does not protect the *human decision moment* — which becomes even more critical when actions are triggered through AI glasses.

---

### What people can use it for

This project enables **safe, human-centered blockchain interactions using AI glasses as the primary interface**.

- **Safer real-world payments with AI glasses**  
  The user looks at a merchant or checkout context → the AI glasses understand the situation → AI proposes a payment → the user confirms via voice → the action is executed or recorded immutably.

- **On-chain access and identity verification in physical spaces**  
  AI glasses can verify NFT- or DAO-based permissions (e.g. event access, membership) without requiring phones or exposing private data.

- **Hands-free, eyes-up blockchain interaction**  
  Designed specifically for wearable use cases where traditional screen-based wallets are unsafe or impractical.

---

### How it makes existing tasks easier and safer

- **Intent-based AI assistance on glasses**  
  AI running off-chain interprets what the user sees and proposes actions instead of forcing manual navigation through wallet apps.

- **Human-in-the-loop confirmation**  
  Even though the interface is wearable, **no action is executed without explicit user confirmation**.

- **Voiceprint-based confirmation for wearables**  
  Voice is used as a natural confirmation method for AI glasses, adding a security layer that fits hands-free usage.

- **Privacy-preserving architecture**  
  Visual context, AI reasoning, and voice data remain off-chain.  
  Only confirmed outcomes are recorded on Ethereum as immutable proofs.

---

### Impact

By using **AI glasses as the primary interface**, this project transforms blockchain from a screen-based system into a **context-aware, wearable-first experience**.

It improves:
- Safety during real-world interactions
- Usability for wearable computing
- Alignment between AI assistance, blockchain trust, and human intent

The human remains the **subject of every on-chain action**, even in an always-on, AI-powered environment.

##Challenge
### 1. Combining blockchain with AI glasses as a beginner

**Challenge:**  
I am relatively new to blockchain development, and the hackathon timeline was very short.  
Integrating blockchain meaningfully into an **AI glasses–first experience** was challenging, especially without falling back to traditional phone-based wallet patterns.

**How I addressed it:**  
- I treated blockchain as a **trust and immutability layer**, not as UI logic  
- I limited on-chain functionality to a minimal but core feature (transaction or event proof)  
- Most intelligence and interaction logic stays off-chain and wearable-focused  

This allowed blockchain to support AI glasses use cases without overengineering.

---

### 2. Securing confirmations in AI glasses scenarios

**Challenge:**  
AI glasses require hands-free, eyes-up interaction, which makes traditional security methods (PINs, repeated screen confirmations) unsuitable and unsafe.

**How I addressed it:**  
- I introduced **voiceprint-based confirmation** as a natural security layer for AI glasses  
- Voice confirmation is used only at the decision moment, not as a replacement for wallet ownership  
- All voice processing is handled off-chain

This aligns security with how people naturally interact with AI glasses.

---

### 3. Integrating ASR while preserving privacy

**Challenge:**  
Using voice as a biometric signal raises privacy concerns, especially when combined with blockchain.

**How I addressed it:**  
- I integrated a **third-party ASR (Automatic Speech Recognition) API** instead of building voice recognition from scratch  
- No raw audio or biometric data is stored on-chain  
- Blockchain only records the confirmed outcome, not the voice data itself  

This preserves user privacy while still enabling secure, wearable-friendly confirmation.

---

### 4. Demonstrating blockchain execution in a live AI glasses demo

**Challenge:**  
Implementing a full on-chain payment flow during a live demo was risky and could fail due to network or testnet instability.

**How I addressed it:**  
- For the live pitch, I used **Alipay payment as a real-world execution proxy**  
- The AI glasses interaction, intent inference, and confirmation flow remain unchanged  
- Blockchain is still presented as the system of record for immutability in the architecture  

This ensured a stable demo while clearly showing how AI glasses would interact with blockchain in production.

---

### 5. Maintaining human control in an always-on wearable system

**Challenge:**  
AI glasses are always-on by nature, which risks over-automation and loss of user agency.

**How I addressed it:**  
I enforced strict design rules:
- AI can propose actions, but never execute them
- Every blockchain-related action requires explicit human confirmation
- Voice confirmation reinforces intentional action

This keeps the system aligned with a **human-in-the-loop, wearable-first security model**.