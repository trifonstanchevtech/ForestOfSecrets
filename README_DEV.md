# Forest of Secrets – Developer Documentation 🛠️

This document describes the **internal architecture, package structure, and extension points**
of the Java game **Forest of Secrets**.

It is intended for **developers, reviewers, and technical recruiters** who want to
understand how the game is built and how it can be extended.

---

## 🎮 Project Overview

**Forest of Secrets** is a Java-based 2D grid game built with **Swing** and a reusable
board-rendering system.

- The player controls a **Knight**
- The goal is to **find the Treasure Chest** or **defeat the Dragon**
- Energy management and item interactions are core mechanics
- The game is fully event-driven and grid-based (30×30 tiles)

---

## 🚀 Application Entry Point

The application starts in:

```
de.stanchev.forestofsecrets.app.GameController
```
This class:

- contains the ```main(String[] args)``` method
- initializes the UI and game core
- connects ```GameWindow``` (UI) with ```Game``` (logic)


---

## 🧠 Architecture Overview

The game follows a clean separation of concerns:

- Core logic is independent of rendering

- Game objects share common abstractions

- Rendering is handled by a reusable board system

- UI delegates all logic to the game core

This makes the project easy to understand, maintain, and extend.

---

## 📦 Package Structure
de.stanchev.forestofsecrets
│
├── app
│   ├──GameController        → Application entry point (main)
│   └── GameWindow           → Swing-based user interface
│ 
│
├── core
│   └── Game                  → Central game logic (movement, rules, collisions)
│
├── model
│   ├── Position              → Grid coordinates (x/y)
│   ├── GameObject            → Base class for all game objects
│   └── Treasure              → Base class for collectible items
│
├── entities
│   ├── Knight                → Player character
│   └── Dragon                → Enemy (win/lose logic)
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
└── board
    ├── Board                 → Rendering engine
    ├── BoardUI               → Swing board window
    └── UnknownElementException

---

## 🎲 Game Logic Rules

- The Knight moves in steps of 30 pixels on a grid

- Each movement consumes energy

- Energy can be restored using:
  - RedPotion 
  - RunePotion 
  - LifeStone

- Energy items disappear after being used

- The Sword changes the outcome of a Dragon encounter

---

## 🏆 Win Conditions

- Find the Treasure Chest

- Defeat the Dragon while carrying the Sword

## ☠️ Lose Condition

- Encounter the Dragon without a Sword

Game end states are handled via JOptionPane dialogs.

---

## 🖌️ Rendering System

Rendering is handled via the reusable board package:

- Each drawable object is identified by a string key
(e.g. ```"Sword"```, ```"Knight"```, ```"Tree"```)

- The Board maps keys to drawX(...) methods

- Game objects call:
```draw(BoardUI board)```
- The grid and all objects are redrawn after every move

The board uses a 15×15 grid, tile size **30×30**, coordinate range ```0–420```.

---

## ➕ Adding a New Item or Object

To add a new collectible item:

**1.** Create a new class extending Treasure

**2.** Assign a unique draw key (string)

**3.** Add a corresponding drawX(...) method in Board

**4.** Register it in initializeDrawables()

**5.** Handle its interaction in Game

This design allows new features to be added without modifying existing core logic.

---

## 🧪 Building the Runnable JAR (IntelliJ)

**1.** File → Project Structure → Artifacts

**2.** Create JAR → From modules with dependencies

**3.** Select GameController as the Main Class

**4.** Build via Build → Build Artifacts

**5.** Test with:
```java -jar ForestOfSecrets.jar```

A ```.bat``` file is provided for easy startup on Windows systems.

---

## 📄 License Notes

- **Source code**: MIT License

- **Board package**: used with explicit permission

- **Assets** (icons/images): AI-generated, usage rights confirmed
(see ```README.md``` for details)

---

## 🔧 Possible Future Improvements

- Additional enemies and item types

- More complex interactions

- Sound effects

- Save/load system

- Difficulty levels

---

## ✨ Final Notes

This project was designed to be:

- easy to read

- easy to extend

- suitable for learning and skill evaluation