# ArthaOS

> **Your money, understood.**

ArthaOS is an offline-first personal finance app for Android that automatically ingests transactions from bank SMS messages, categorizes spending, tracks budgets, and surfaces financial insights — with no account required, no cloud dependency, and no data leaving your device.

Built as a portfolio project targeting senior Android development roles, ArthaOS demonstrates production-grade architecture across the full modern Android stack.

---

## Screenshots

> _Coming soon — app in active development_

---

## Features

### Automatic Transaction Ingestion
- Reads bank SMS messages and parses transactions automatically
- Supports HDFC Bank (UPI, card, net banking, ATM)
- Manual transaction entry as a fallback or supplement
- Receipt scanning via CameraX + ML Kit OCR
- Duplicate detection across import sessions

### Smart Categorization
- Auto-categorizes transactions by merchant and keyword matching
- Local merchant intelligence — learns your corrections
- Categories: Food, Travel, Shopping, Bills, Entertainment, Health, Education, Salary, Investments

### Dashboard
- Current balance, monthly income, and expense summary
- Budget overview with progress indicators
- Recent transactions feed
- Top category and top merchant at a glance

### Budgets
- Per-category monthly budgets
- Visual progress tracking
- Warning thresholds and exceeded states

### Analytics
- Category distribution (pie chart)
- Monthly income vs expense trends (bar chart)
- Daily spending history timeline
- Merchant breakdown — most used, highest spending

### Insights Engine
- Rule-based financial insights: spending spikes, budget breaches, savings improvements
- Trend detection across rolling periods

### Security
- Biometric authentication (fingerprint + face unlock)
- Session timeout

### Developer Tools
- **Parser Playground** — paste any bank SMS and inspect the parsed output in real time. Built for testing, demonstration, and interviews.

### Optional Backup
- Google Sign-In + Firebase backup (fully optional)
- App works completely offline without it
- User-controlled backup and restore

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture — Data / Domain / Presentation |
| DI | Hilt |
| Database | Room (WAL mode) |
| Async | Coroutines + Flow |
| Navigation | Navigation Component |
| Camera | CameraX |
| OCR | ML Kit Text Recognition v2 |
| Charts | Vico |
| Widget | Jetpack Glance |
| Background | WorkManager |
| Auth | BiometricPrompt API |
| Settings | Jetpack DataStore |
| Backup | Firebase + Google Sign-In (optional) |

---

## Architecture

ArthaOS follows Clean Architecture with strict layer separation:

```
app/
├── data/
│   ├── local/          # Room database, DAOs, entities
│   ├── sms/            # SMS ContentProvider reader
│   ├── ocr/            # CameraX + ML Kit pipeline
│   └── repository/     # Repository implementations
├── domain/
│   ├── model/          # Domain models
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Business logic use cases
└── presentation/
    ├── dashboard/
    ├── transactions/
    ├── budgets/
    ├── analytics/
    ├── insights/
    └── settings/
```

The SMS parser is implemented as a **classifier-first pipeline**: messages are first identified by template family (UPI debit, card swipe, net banking, ATM), then processed by a type-specific extractor. This makes the parser extensible to new banks without touching existing rules.

---

## SMS Parser

The parser operates in two stages:

1. **Classification** — identify which message template family a given SMS belongs to based on bank sender ID and keyword patterns
2. **Extraction** — run the appropriate regex extractor for that family to pull amount, merchant, account, transaction type, and timestamp

```
Raw SMS
   │
   ▼
Classifier (sender + keyword match)
   │
   ├── UPI Debit    → UpiDebitExtractor
   ├── UPI Credit   → UpiCreditExtractor
   ├── Card Debit   → CardDebitExtractor
   ├── Net Banking  → NetBankingExtractor
   └── ATM          → AtmExtractor
                          │
                          ▼
                   ParsedTransaction
```

The Parser Playground screen exposes this pipeline interactively — paste any SMS and see the parsed result, the matched template, and all extracted fields.

---

## Privacy

- All data is stored locally on device using Room
- No analytics, no tracking, no ads
- SMS is read only with explicit user permission and never transmitted
- Firebase backup is opt-in and user-initiated only
- No mandatory account or registration

---

## Roadmap

| Phase | Weeks | Focus |
|---|---|---|
| 0 | 1 | Planning — schema, wireframes, navigation map |
| 1 | 2–4 | Foundation — Compose skeleton, Room, transaction CRUD |
| 2 | 5–7 | SMS engine — reader, parser, categorization |
| 3 | 8–9 | Dashboard + budgets |
| 4 | 10–11 | Analytics + merchant intelligence |
| 5 | 12 | Insights rule engine |
| 6 | 13–15 | Receipt scanner, biometrics, widget, notifications |
| 7 | 16 | Testing, polish, Play Store release |

---

## What's Not in V1

To keep scope disciplined, the following are explicitly deferred:

- AI chat or LLM integration
- Real banking API integration
- UPI payments
- Investment tracking
- Subscription billing
- Multi-device sync
- Backend server of any kind

---

## Getting Started

> _Setup instructions will be added once the project reaches a runnable state (end of Phase 1)._

```bash
# Clone the repo
git clone https://github.com/yourusername/ArthaOS.git

# Open in Android Studio Hedgehog or later
# Run on a physical device for SMS functionality
# Minimum SDK: 26 (Android 8.0)
```

---

## Why ArthaOS

*Artha* (अर्थ) is a Sanskrit word meaning wealth, purpose, and meaning — one of the four classical goals of human life. Most finance apps treat money as a number. ArthaOS treats it as information: something to understand, not just record.

The offline-first, no-account design is a deliberate architectural decision. Your financial data is yours. It lives on your device, it works without internet, and it doesn't require you to trust a server you've never seen.

---

## License

MIT License — see [LICENSE](LICENSE) for details.

---

## Author

**Johnny** — Android developer and musician based in Delhi, India.
Building ArthaOS as a portfolio piece and a real tool for real use.

_Open to senior Android developer roles — [LinkedIn](#) · [Email](#)_
