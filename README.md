# Quantfolio — Investment Portfolio Simulator

> JavaFX desktop application for simulating investment portfolio management across stocks, cryptocurrencies, and commodities.  
> Developed as a final project for the **Modeling and Analysis of Information Systems (MAS)** course at PJATK, Semester 6.

---

## Overview

Quantfolio is a local investment simulator that lets users manage multiple portfolios, buy and sell assets, and observe how portfolio value changes over time through daily market simulations — all without real financial risk or internet connection.

State is fully persistent between sessions via Java object serialization.

---

## Features

### Market View
- Browse available assets with name, symbol, current price, and risk level
- **Simulate Day** button — triggers price changes across all assets based on asset-type volatility:
  - Stocks (Akcja): ±3% max change
  - Cryptocurrencies (Kryptowaluta): ±10% max change
  - Commodities (Surowiec): ±1% max change
- Aggressive assets have 2× higher volatility than safe assets
- Prices include a small upward bias (+0.5%) to prevent drift toward zero

### Portfolio Management
- Create and manage multiple named portfolios with individual cash balances
- View portfolio list with current total value
- Double-click any portfolio to open detailed view

### Portfolio Detail
- See all open positions: asset name, average purchase price, current price, quantity, total value
- Real-time portfolio value calculation
- Buy assets from within the portfolio view

### Buying Assets
- Select asset from market table, enter quantity
- Live cost calculation as you type
- Balance validation — transaction blocked if insufficient funds
- On purchase: transaction recorded, position created or updated (average price recalculated), cash balance deducted

### User System
- Default user type: **Amateur** (portfolio balance capped at 1,000,000 PLN)
- Upgrade to **Professional** by entering a license number — removes balance limit
- Dynamic inheritance: `Amator` instance is replaced by `Profesjonalista` at runtime, portfolios transferred automatically

### Persistence
- Full state saved to `quantfolio.ser` on app close via `ObjectPlus` extent management
- State restored on next launch — portfolios, positions, transaction history all preserved
- `resetSystemu = true` flag in `Main.java` to wipe state and start fresh

---

## Architecture
```
src/
├── app/
│   └── Main.java                    # JavaFX entry point, data initialization
├── controller/
│   ├── RynekController.java         # Market view
│   ├── ListaPortfeliController.java # Portfolio list
│   ├── SzczegolyPortfelaController.java  # Portfolio detail
│   └── KupAktywoController.java     # Buy asset form
└── model/
    ├── ObjectPlus.java              # Extent management + serialization
    ├── Aktywo.java                  # Abstract base class for assets
    ├── Akcja.java                   # Stock (±3% volatility)
    ├── Kryptowaluta.java            # Cryptocurrency (±10% volatility)
    ├── Surowiec.java                # Commodity (±1% volatility)
    ├── Rynek.java                   # Market — holds assets, runs simulation
    ├── Portfel.java                 # Portfolio — cash balance + positions
    ├── Pozycja.java                 # Position — asset holding in a portfolio
    ├── Transakcja.java              # Immutable transaction record
    ├── TypTransakcji.java           # Enum: KUPNO / SPRZEDAZ
    ├── Uzytkownik.java              # Abstract user
    ├── Amator.java                  # Amateur user (balance cap: 1M PLN)
    └── Profesjonalista.java         # Professional user (no balance limit)
```

---

## OOP Concepts Implemented

| Concept | Implementation |
|---|---|
| Abstract class | `Aktywo`, `Uzytkownik` |
| Inheritance | `Akcja`, `Kryptowaluta`, `Surowiec` extend `Aktywo` |
| Polymorphism | `zmienCene()` called on all assets, different volatility per type |
| Dynamic inheritance | `Amator` → `Profesjonalista` at runtime via `zmianaNaProfesjonaliste()` |
| Qualified association | `Uzytkownik` → `Map<String, Portfel>` (keyed by portfolio name) |
| Association class | `Pozycja` represents the Portfel–Aktywo association with state |
| Extent management | `ObjectPlus` tracks all instances per class |
| Persistence | Java serialization to `quantfolio.ser` |

---

## Documentation

Full project documentation (in Polish) is available in [`Dokumentacja_ZAO_Jastrzebski_s27397.pdf`](Dokumentacja_ZAO_Jastrzebski_s27397.pdf), including:
- User requirements
- Use case diagram
- Analytical and design class diagrams
- Activity diagram (Buy Asset use case)
- State diagram (Pozycja class)
- GUI wireframes
- Design decisions and dynamic analysis

---

## Tech Stack

- **Java** with **JavaFX** (FXML, TableView, Scene switching)
- Java Serialization (`ObjectOutputStream` / `ObjectInputStream`)
- OOP: abstract classes, interfaces, dynamic inheritance, qualified associations
- `BigDecimal` for financial precision

---

*Developed by Mateusz Jastrzębski (s27397) · PJATK · Semester 6, 2025*
