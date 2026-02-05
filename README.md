#  💎 Forest of Secrets

## ▶ Play the Game

Download the latest version here:
https://github.com/trifonstanchevtech/ForestOfSecrets/releases/latest

---

**Forest of Secrets** is a small Java-based 2D grid game where a knight explores a mysterious forest, collects magical items, and faces a dangerous dragon in search of a hidden treasure.

The project focuses on clean object-oriented design, separation of concerns, and a custom rendering system built with Java Swing.

---

## 🎮 Gameplay

You control a **knight** moving across a grid-based forest.

### Core rules:
**🧍Knight:**
- Starts with 1500 energy.
- Movement consumes energy.
- Can obtain/carry a sword.

**🗡️ Sword** 
- Required to gain the ability to defeat the Dragon

**🐉 Dragon:**
- Without sword → instant defeat.
- With sword → victory.

**🧰 Treasure Chest**
- Finding it immediately wins the game.

🔋 **Energy Items**
  - Red Potion
  - Rune Potion
  - Life Stone  
All restore energy to full and disappear after use.

💎 **Collectible Items**
  - Crystal
  - Gold Pouch
  - Medallion
  - Helmet
  - Relic
  - Scroll  
These are optional collectibles for exploration feedback.

🌳 Hidden items appear as trees on the map

Each game starts with objects placed randomly on the grid.

---

## ⌨️ Controls

- **Arrow Keys** → Move the knight
- **Start button** → Start the game
- **Beenden / Exit** → Quit the game

The game window must be focused for controls to work properly.

---

## ▶️ Running the Game
### Option 1: Download & Play (Recommended)

1. Download the game from **Releases**
2. Extract the archive
3. Double-click **Forest Of Secrets**

(No console window - runs via launcher script)

> **Requires Java 17+**

---

### Option 2: Run from Source (IntelliJ)
1. Open the project in IntelliJ IDEA
2. Run ```de.stanchev.forestofsecrets.app.GameController```

---

## 🛠️ Technologies Used

- Java 17
- Java Swing
- Object-Oriented Programming
- IntelliJ IDEA
- Custom 2D rendering via a board-based drawing system

---

## 📁 Project Structure (simplified)

de.stanchev.forestofsecrets
│
├── app│
│   ├── GameWindow
│   └── GameController
│
├── core
│   └── Game
│
├── model
│   ├── Position
│   ├── GameObject
│   └── Treasure
│
├── entities
│   ├── Knight
│   └── Dragon
│
├── items
│   └──(all collectible and usable items)
│
├── board (external / allowed dependency)
└── resources

---

## 🖼️ Assets & Credits

- Icons and images: AI-generated (usage rights held by author)

- Board rendering package: used with permission

- Game logic and architecture: created by Trifon Stanchev

---

## 📜 License & Assets

This project is licensed under the **MIT License**.  
See `LICENSE` for details.