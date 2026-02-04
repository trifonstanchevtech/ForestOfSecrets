#  💎 Forest of Secrets

**Forest of Secrets** is a small Java-based grid game where a knight explores a mysterious forest, collects magical items, and faces a dangerous dragon in search of a hidden treasure.

The game focuses on clean object-oriented design, simple but expressive game logic, and custom 2D rendering using Java Swing.

---

## 🎮 Gameplay

You control a **knight** moving across a grid-based forest.

### Core rules:
**🧍Knight:**
- Has energy (starts with 1500).
- Movement costs energy.
- Can carry a sword.

**🗡️ Sword** 
Required to gain the ability to defeat the Dragon

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

## ▶️ How to Run the Game

### Option 1: Run the JAR (recommended)
1. Download the runnable JAR from the **Releases** section
2. Double-click `Start Forest Of Secrets.bat`  
   *(or run `java -jar ForestOfSecrets.jar`)*

> **Requires Java 17+**

---

### Option 2: Run from source (IntelliJ)
1. Open the project in IntelliJ IDEA
2. Run the `Main` class
3. The game window will open automatically

---

## 🛠️ Technologies Used

- Java 17
- Java Swing
- Object-Oriented Programming
- IntelliJ IDEA
- Custom rendering via a board-based drawing system

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
│   ├── (all collectible and usable items)
│
├── board (external / allowed dependency)
└── resources

---

## 🖼️ Assets & Credits

- **Treasure chest image (`schatztruhe.png`)**  
  Generated using an AI image generation tool.  
  Included for use within this project according to the tool’s terms.

- **Board rendering system**  
  Originally provided as a learning utility and used with permission.

---

## 📜 License & Assets

This project is licensed under the **MIT License**.  
See the `LICENSE` file for details.

Parts of board package: used with explicit permission

Images: AI-generated, usage rights confirmed

