## Forest of Secrets – Design Decisions

This document explains the design reasoning behind the implementation of *Forest of Secrets.*

While ```README_DEV.md``` describes the structure, this file explains why the system was built this way.

---

## 🎯 Design Goals

The project was designed with the following priorities:

**1.** Clear separation of responsibilities

**2.** Easy extendability

**3.** Understandable code for learners and reviewers

**4.** Minimal coupling between components

**5.** Replaceable graphics system

The goal was not to build a complex game, but a clean and maintainable one.

---

## 🧱 Separation of Logic and Rendering

The most important design decision:

- The game logic must not depend on the graphics implementation.

The ```Game``` class never draws shapes directly.

Instead:

    GameObject → BoardUI → Board → draw method

---

The logic only says what should be drawn, not how.

### Why?

If the rendering system changes (Swing → JavaFX → Console → Web),
the game rules remain untouched.

---

## 🎨 String-Based Rendering Keys

Instead of drawing objects directly, each element uses a string key:

```"Knight"```
```"Sword"```
```"Dragon"```
```"Tree"```

The ```Board``` maps these keys to drawing functions.

### Advantages

- New objects can be added without modifying the engine

- Rendering and game logic stay independent

- Simplifies object responsibilities

- Prevents large if-else drawing blocks

### Trade-off

Less type safety (strings instead of enums), but easier extensibility for this project scope.

---

## 🧍 GameObject Inheritance Hierarchy

GameObject
└── Treasure
      ├── Sword
      ├── Potions
      ├── Collectibles
      ├── Dragon
      └── TreasureChest

### Why Dragon extends Treasure?

Because it behaves like a hidden object on the map:

- appears as a tree

- triggers event when discovered

This keeps collision handling centralized.

---

## 🎮 Centralized Interaction Handling

All interactions are handled inside Game.

Not inside objects.

### Why?

Avoids this problem:

-    Objects controlling game flow → chaotic dependencies

Instead:

    Knight moves → Game checks collision → Game decides outcome

This keeps behavior predictable and testable.

---

## 🔁 No Continuous Game Loop

The game is event-driven instead of using a real-time loop.

### Reason

- Simpler architecture

- Easier debugging

- Better suited for Swing

- More appropriate for grid-based gameplay

Each user action = one game update.

---

## 🗺️ Fixed Grid System

Tile size: 30 pixels
Board size: 15 × 15 tiles

### Why fixed grid?

- predictable movement

- simple collision detection

- no floating point calculations

- easier random placement

---

## 🔋 Energy System Design

Energy consumption is calculated from movement distance:

    energy -= |dx| + |dy|

### Why this formula?

It works automatically for any movement step size and keeps logic simple.

---

## 🧩 Items as Data, Not Logic

Items do not change the game themselves.

Instead:

    Game detects item → Game applies rule

### Advantage

Adding a new item requires only:

- defining type

- defining rule in Game

No side effects inside item classes.

---

## 🪟 Controller-Driven UI

The UI never calls game internals directly.

    UI → Controller → Game

This prevents the UI from becoming game logic.

---

## 🧠 Simplicity Over Over-Engineering

Some typical patterns were intentionally avoided:

Not used:

- ECS architecture

- Observer frameworks

- dependency injection

- game engine loop

**Reason**

The project aims to demonstrate clean fundamentals, not framework complexity.

---

## 🔮 Future Extension Strategy

The architecture supports future features without rewriting core parts:

**Possible additions:**

- new enemies

- status effects

- inventory

- multiple levels

- different renderers

Because:

- behavior is centralized and rendering is abstracted

---

✅ Conclusion

The project is intentionally small but structured.

**It demonstrates:**

- object-oriented design

- separation of concerns

- event-driven architecture

- extendable rendering system

The focus is clarity and maintainability rather than feature count.

---