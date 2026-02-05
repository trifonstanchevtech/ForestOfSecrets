# Forest of Secrets – Developer Documentation 🛠️

This document explains the **technical structure and design decisions**
of the Java game ***Forest of Secrets***.

It is intended for developers, reviewers, and technical recruiters who want to
understand how the application works internally and how it can be extended.

---

## 🎮 Project Overview

**Forest of Secrets** is a small Java-based 2D grid game built with **Swing** and a reusable
board-rendering system.

The player controls a knight exploring a forest while managing energy, collecting items and interacting with special objects.

The project focuses on:

- clean object-oriented structure

- separation between UI and logic

- extendable item system

- simple but maintainable architecture

---

## 🚀 Application Entry Point

The application starts in:

```
de.stanchev.forestofsecrets.app.GameController
```

Responsibilities of this class:

- contains the ```main(String[] args)``` method

- creates the main window (```GameWindow```)

- initializes the game (```Game```)

- connects user actions with the game logic

The controller does not contain game rules — it only coordinates components.

---

## 🧠 Architecture Overview

The project loosely follows the MVC principle:

| Layer      | Responsibility                        |
|------------|---------------------------------------|
| UI (View)  | Displays data and receives user input |
| Controller | Forwards actions and updates UI       |
| Core Logic | Processes rules and game state        |


Important design goal:

- The game logic must work independently from the graphical representation.

This allows replacing the UI without rewriting the game rules.

---

## 📦 Package Structure
de.stanchev.forestofsecrets
│
├── app
│   ├── GameController   → Application control & input handling
│   └── GameWindow       → Swing GUI
│
├── core
│   └── Game             → Main game logic (movement, collisions, rules)
│
├── model
│   ├── Position         → Coordinates on the grid
│   ├── GameObject       → Base class for all world objects
│   └── Treasure         → Base class for collectible objects
│
├── entities
│   ├── Knight           → Player character
│   └── Dragon           → Special enemy object
│
├── items
│   ├── Sword
│   ├── TreasureChest
│   ├── RedPotion
│   ├── RunePotion
│   ├── LifeStone
│   ├── Crystal
│   ├── GoldPouch
│   ├── Medallion
│   ├── Helmet
│   ├── Relic
│   └── Scroll
│
└── board (provided rendering system)
├── Board
├── BoardUI
└── UnknownElementException


---

## 🎲 Game Logic Rules

### Grid System

- Tile size: 30 × 30 pixels

- Playable coordinates: 0 – 420

- Objects spawn randomly without overlapping

---

### Player (Knight)

The knight has:

- name

- age

- position

- energy

- sword state

Movement:

- costs energy based on distance

- blocked if energy is insufficient

---

### Interactions
| Object               | Result                       |
|----------------------|------------------------------|
| Energy Items         | restore energy to 1500       |
| Sword                | enables defeating the dragon |
| Dragon without sword | defeat                       |
| Dragon with sword    | victory                      |
| Treasure Chest       | victory                      |
| Collectibles         | message only                 |


All interactions are handled centrally inside the ```Game``` class.

---

## 🖌️ Rendering System

The project uses a **string-based rendering registry.**

Each drawable element registers a key:

```"Knight"```
```"Sword"```
```"Tree"```
```"RedPotion"```
...

The ```Board``` maps these keys to drawing methods.

Game objects never draw themselves directly — they only request rendering:

```board.draw("Knight", x, y);```

---

### Why this approach?

Advantages:

- logic independent from graphics

- new objects require minimal changes

- rendering system reusable

---

## 🔁 Game Loop (Simplified)

**1.** User presses key/button

**2.** Controller calls ```Game.play()```

**3.** Game updates state 

**4.** Board is redrawn 

**5.** UI displays message

---

## ➕ Adding a New Item

To introduce a new item:

**1.** Create class extending ```Treasure``` 

**2.** Give it a unique key name 

**3.** Implement drawing in ```Board``` 

**4.** Register drawable in ```initializeDrawables()```

**5.** Add interaction in ```Game```

No other files need modification.

---

## 🧪 Building the Runnable JAR (IntelliJ)

**1.** File → Project Structure → Artifacts 

**2.** Create JAR from modules with dependencies 

**3.** Select GameController as Main Class 

**4.** Build via Build → Build Artifacts

Run with:

```java -jar ForestOfSecrets.jar```

A Windows start script is provided for easier usage.

---

## 📄 License Notes

- Source code: MIT License

- Board package: used with permission

- Images: AI-generated assets (usage rights confirmed)

---

## 🔧 Possible Extensions

The architecture was prepared for future features:

- additional enemies

- status effects

- multiple maps

- save/load system

- sound system

---

## ✨ Final Remark

The project intentionally keeps the gameplay simple while emphasizing:

- readability

- separation of responsibilities

- extendability

The goal is to demonstrate structured programming and maintainable design rather than complex mechanics.