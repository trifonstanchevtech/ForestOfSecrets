## Forest of Secrets – Design Decisions & Architecture

Dieses Dokument beschreibt die wichtigsten **Entwurfsentscheidungen**,  
**Architekturprinzipien** und **technischen Überlegungen** hinter dem Spiel  
**Forest of Secrets**.

Es ergänzt die README-Dateien und richtet sich an Entwickler:innen,
Dozent:innen sowie Reviewer (z. B. im IHK-Kontext).

---

## 🎯 Ziel des Designs

Das Ziel war es, ein **übersichtliches, erweiterbares und gut wartbares**
Spiel zu entwickeln, das:

- klare Verantwortlichkeiten besitzt
- leicht erweitert werden kann (neue Items, Gegner, Regeln)
- Spiellogik strikt von Darstellung trennt
- didaktisch sauber für Ausbildungs- und Portfoliozwecke ist

---

## 🧱 Architekturüberblick

Das Projekt folgt einer **klaren Schichtenarchitektur**, angelehnt an MVC:

```text
UI (Swing)
  ↓
Controller
  ↓
Game (Spiellogik)
  ↓
Model / Entities / Items
```
**Warum diese Trennung?**

- **Lesbarkeit**: Jede Klasse hat genau eine Aufgabe

- **Testbarkeit**: Spiellogik ist unabhängig von der GUI

- **Erweiterbarkeit**: Neue Inhalte können ohne Umbauten ergänzt werden

---

## 📦 Package-Design – Begründung
```app```

**Start & Steuerung**

- ```GameController``` enthält die ```main()```-Methode

- Vermittelt zwischen UI und Spiellogik

- Reagiert auf Benutzeraktionen (Buttons / Tastatur)

**Begründung:**
Der Controller bündelt alle Use-Cases und verhindert Spiellogik in der UI.

---

```core```

**Zentrale Spiellogik**

- ```Game``` verwaltet:

- Spielfigur

- Spielobjekte

- Kollisionen

- Gewinn- und Verlustbedingungen

**Begründung:**
Alle Spielregeln sind an einer Stelle gebündelt → einfache Wartung.

---

```model```

**Abstraktionen & Grundlagen**

- ```Position``` – Rasterkoordinaten

- ```GameObject``` – Basisklasse für alle Objekte

- ```Treasure``` – Basisklasse für sammelbare Objekte

**Begründung:**
Gemeinsames Verhalten wird zentral definiert (DRY-Prinzip).

---

```entities```

**Lebende Spielobjekte**

- ```Knight```

- ```Dragon```

**Begründung:**
Lebewesen besitzen Verhalten und Status, Items nicht.

---

```items```

**Sammel- & Spezialobjekte**

- Energieobjekte (RedPotion, RunePotion, LifeStone)

- Atmosphärische Funde (Crystal, Scroll, Relic, …)

- Spielrelevante Items (Sword, TreasureChest)

**Begründung:**
Neue Items können ergänzt werden, ohne bestehende Logik zu verändern.

---

```board```

**Rendering-System**

- ```Board``` → Zeichenlogik

- ```BoardUI``` → Fenster & Anzeige

- ```UnknownElementException``` → Sicherheitsmechanismus

**Begründung:**
Trennung von Spiellogik und grafischer Darstellung.

---
## 🖌️ Rendering-Konzept (Board)

**String-basierte Zeichen-Keys**

Beispiel:
```board.draw("Knight", x, y);```

- GameObjects kennen **nur ihren Zeichen-Key**

- Das Board entscheidet, wie ein Objekt dargestellt wird

- Die konkrete Zeichnung wird zentral in ```Board``` registriert:
  - ```drawables.put("Knight", this::drawKnight);```


**Vorteile:**

- Spiellogik bleibt grafikfrei

- Austauschbare Darstellungen (Design kann geändert werden, ohne Game anzupassen)

- Zentrale Kontrolle über das Aussehen

---

## 🌳 Tarnungsprinzip: Treasure = Tree

Alle ```Treasure```-Objekte werden **auf dem Spielfeld als Baum dargestellt.**

Erst beim Fund:

- wird das echte Objekt in der Fundzone angezeigt

- erscheint eine Fundmeldung im UI

**Designentscheidung:**

- Überraschungseffekt

- Fokus auf Exploration

- Einheitliche Spielfeldoptik (weniger visuelles Chaos)

---

## ⚔️ Sword–Dragon-Mechanik

Der Drache ist ein Spezialfall:

| Zustand des Ritters | Begegnung mit Drache |
|---------------------|----------------------|
| ohne Schwert        | Spiel verloren       |
| mit Schwert         | Spiel gewonnen       |

**Begründung:**

- Einfache, klare Regel

- Erhöht Spannung

- Fördert Exploration („erst Schwert finden!“)

---

## 🔋 Energie-System

- Jede Bewegung kostet Energie (abhängig von der Verschiebung)

- Energieobjekte setzen Energie vollständig zurück (z. B. auf 1500)

- Keine Teilwerte oder komplexe Effekte → bewusst simpel gehalten

**Begründung:**

- Verständlich für Spieler:innen

- Weniger Fehlerquellen

- Gute Grundlage für spätere Erweiterungen

---

## 🧩 Objektmodell – zentrale Abstraktionen

```GameObject``` **(Basis)**

Jedes Objekt besitzt:

- eine Position (```Position```)

- eine Zeichenfunktion (```draw(BoardUI)```)

Dadurch kann jedes Objekt in einem gemeinsamen Array (```GameObject[]```) gehalten
und einheitlich behandelt werden (Polymorphie).

```Treasure``` **(Sammelobjekte)**

```Treasure``` erweitert ```GameObject``` um:

- einen Namen/Key für das Rendering (```getName()```)

- Standard-Verhalten auf dem Spielfeld (als ```Tree``` getarnt)

- optional: ```isWinningTreasure()``` für Gewinnobjekte

---

## 🧠 Bewusste Vereinfachungen

Aktuell bewusst **nicht implementiert**:

- Sounds
- Save-/Load-System
- komplexe Item-Effekte
- mehrere Level / Karten

**Begründung:**

- Fokus auf eine saubere und verständliche Architektur
- Konzentration auf die Kernmechaniken des Spiels
- Das Design ist bewusst so vorbereitet, dass spätere Erweiterungen möglich sind

---

## ➕ Erweiterbarkeit (Extension Points)
**Neues Item hinzufügen (Beispiel-Checkliste)**

**1.** Neue Klasse in ```items``` erstellen (extends ```Treasure```)

**2.** Key festlegen (z. B. ```"NewItem"```)

**3.** Im ```Board```:

- Zeichenmethode ```drawNewItem(Graphics, Point)``` hinzufügen

- In ```initializeDrawables()``` registrieren:

- ```drawables.put("NewItem", this::drawNewItem);```

**4.** Im ```Game```:

- Objekt in ```initializeElements()``` hinzufügen

- Optional: Interaction in ```handleInteraction()``` ergänzen

**Vorteil:**
Das System bleibt stabil – Erweiterungen sind lokal und übersichtlich.

---

## ✅ Fazit

**Forest of Secrets** ist bewusst als **kleines, aber sauber strukturiertes Spielsystem**
konzipiert:

- klare Projekt- und Paketstruktur
- saubere Anwendung von OOP-Prinzipien
- string-basiertes Rendering über definierte Keys
- klare Trennung von UI, Controller und Spiellogik
- bewusst einfach gehaltene Kernmechaniken mit Fokus auf Erweiterbarkeit

Das Design erlaubt jederzeitige Erweiterungen, ohne dass bestehende
Strukturen grundlegend angepasst werden müssen.