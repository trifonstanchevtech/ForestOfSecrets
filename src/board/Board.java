package board;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2026
 * @author:		 Trifon Stanchev
 * @version:	 1.0
 */

@SuppressWarnings("serial")
public class Board extends Panel {

	private final List<String> elements;
	private final List<Point> positions;
	private final Map<String, Drawable> drawables;

	public Board() {
		super();
		elements = new ArrayList<>();
		positions = new ArrayList<>();
		drawables = new HashMap<>();
		initializeDrawables();
	}

	private void initializeDrawables() {
		drawables.put("Grid", this::drawGrid);
		drawables.put("Sword", this::drawSword);
		drawables.put("Tree", this::drawTree);

		// neue Spielfiguren
		drawables.put("RedPotion", this::drawRedPotion);
		drawables.put("RunePotion", this::drawRunePotion);
		drawables.put("LifeStone", this::drawLifeStone);
		drawables.put("Dragon", this::drawDragon);
		drawables.put("Chest", this::drawChest);
		drawables.put("Knight", this::drawKnight);
		drawables.put("Crystal", this::drawCrystal);
		drawables.put("GoldPouch", this::drawGoldPouch);
		drawables.put("Medallion", this::drawMedallion);
		drawables.put("Helmet", this::drawHelmet);
		drawables.put("Relic", this::drawRelic);
		drawables.put("Scroll", this::drawScroll);
	}

	public void draw(String figure, int xpos, int ypos) throws UnknownElementException {
		if (!drawables.containsKey(figure)) {
			throw new UnknownElementException("Unknown element: " + figure);
		}
		elements.add(figure);
		positions.add(new Point(xpos, ypos));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawLine(0, 455, 450, 455);
		for (int i = 0; i < elements.size(); i++) {
			String element = elements.get(i);
			Point position = positions.get(i);
			drawables.get(element).draw(g, position);
		}
	}

	public void wipe() {
		elements.clear();
		positions.clear();
		repaint();
	}

	/**
	 *
	 * @param g
	 * @param position
	 */
	private void drawGrid(Graphics g, Point position) {
		int j = 0;
		g.setColor(new Color(150, 150, 150));
		for (int i = 1; i < 15; i++) {
			j = 30 * i;
			g.drawLine(j, 0, j, 450);
			g.drawLine(0, j, 450, j);
		}
	}

	/**
	 *
	 * @param g
	 * @param position
	 */
	private void drawTree(Graphics g, Point position) {
		int[] xPoints = {15, 18, 16, 22, 16, 24, 16, 26, 16, 16, 14, 14, 4, 14, 6, 14, 8, 14, 11};
		int[] yPoints = {4, 7, 7, 12, 12, 17, 17, 22, 22, 25, 25, 22, 22, 17, 17, 12, 12, 7, 7};
		int nPoints = xPoints.length;
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] += position.x;
			yPoints[i] += position.y;
		}
		g.setColor(new Color(0, 100, 0));
		g.fillPolygon(xPoints, yPoints, nPoints);
	}

	/**
	 *
	 * @param g
	 * @param position
	 */
	// Spielfigur: Schwert
	private void drawSword(Graphics g, Point position) {
		// Farben
		Color bladeColor = new Color(200, 200, 200);   // Stahl
		Color bladeEdge  = new Color(240, 240, 240);   // Glanz
		Color guardColor = new Color(150, 110, 60);    // Parierstange
		Color gripColor  = new Color(110, 70, 30);     // Griff
		Color pommelColor= new Color(170, 130, 80);    // Knauf

		// Klinge
		g.setColor(bladeColor);
		g.fillRect(position.x + 14, position.y + 2, 4, 18);

		// Glanzkante
		g.setColor(bladeEdge);
		g.fillRect(position.x + 15, position.y + 3, 1, 16);

		// Spitze
		int[] tipX = {14, 16, 18};
		int[] tipY = {2, 0, 2};
		for (int i = 0; i < tipX.length; i++) {
			tipX[i] += position.x;
			tipY[i] += position.y;
		}
		g.fillPolygon(tipX, tipY, 3);

		// Parierstange
		g.setColor(guardColor);
		g.fillRect(position.x + 10, position.y + 20, 12, 3);

		// Griff
		g.setColor(gripColor);
		g.fillRect(position.x + 14, position.y + 23, 4, 6);

		// Knauf
		g.setColor(pommelColor);
		g.fillOval(position.x + 13, position.y + 29, 6, 4);
	}

	// Spielfigur: Drache
	private void drawDragon(Graphics g, Point position) {
		// Grüne Farbe für den Drachenkörper setzen
		g.setColor(new Color(0, 120, 0));

		// Kopf des Drachen zeichnen
		g.fillOval(position.x + 10, position.y + 7, 10, 10);

		// Körper des Drachen zeichnen
		g.fillOval(position.x + 8, position.y + 14, 14, 10);

		// Koordinaten für den linken Flügel definieren
		int[] wx1 = {position.x + 8, position.x + 2, position.x + 8};
		int[] wy1 = {position.y + 16, position.y + 20, position.y + 22};

		// Linken Flügel als Dreieck zeichnen
		g.fillPolygon(wx1, wy1, 3);

		// Koordinaten für den rechten Flügel definieren
		int[] wx2 = {position.x + 22, position.x + 28, position.x + 22};
		int[] wy2 = {position.y + 16, position.y + 20, position.y + 22};

		// Rechten Flügel als Dreieck zeichnen
		g.fillPolygon(wx2, wy2, 3);

		// Schwarze Farbe für das Auge setzen
		g.setColor(Color.BLACK);

		// Auge des Drachen zeichnen
		g.fillOval(position.x + 17, position.y + 11, 2, 2);
	}

	// Spielfigur: Schatztruhe
	private void drawChest(Graphics g, Point position) {
		// Holzfarben für die Truhe definieren
		Color wood = new Color(139, 69, 19);
		Color darkWood = new Color(101, 50, 15);
		Color gold = new Color(212, 175, 55);

		// Helle Holzfarbe setzen
		g.setColor(wood);

		// Unteren Teil der Truhe zeichnen
		g.fillRect(position.x + 6, position.y + 14, 18, 12);

		// Dunkle Holzfarbe setzen
		g.setColor(darkWood);

		// Oberen Deckel der Truhe zeichnen
		g.fillRect(position.x + 6, position.y + 10, 18, 6);

		// Goldfarbe für Beschläge setzen
		g.setColor(gold);

		// Linke goldene Verstärkung zeichnen
		g.fillRect(position.x + 10, position.y + 10, 2, 16);

		// Rechte goldene Verstärkung zeichnen
		g.fillRect(position.x + 18, position.y + 10, 2, 16);

		// Goldenes Schloss in der Mitte zeichnen
		g.fillOval(position.x + 14, position.y + 18, 4, 4);

		// Schwarze Farbe für Umrisse setzen
		g.setColor(Color.BLACK);

		// Umriss des unteren Truhenteils zeichnen
		g.drawRect(position.x + 6, position.y + 14, 18, 12);

		// Umriss des Deckels zeichnen
		g.drawRect(position.x + 6, position.y + 10, 18, 6);
	}

	// Spielfigur: Ritter
	private void drawKnight(Graphics g, Point position) {
		//  Kopf / Helm
		g.setColor(new Color(170, 170, 170)); // Stahl
		g.fillOval(position.x + 9, position.y + 4, 12, 10);

		// Helm-Rand / Kontur
		g.setColor(Color.BLACK);
		g.drawOval(position.x + 9, position.y + 4, 12, 10);

		// Visier (dunkel)
		g.setColor(new Color(60, 60, 60));
		g.fillRect(position.x + 11, position.y + 9, 8, 3);

		// Nasenschutz (kleiner Strich)
		g.setColor(new Color(120, 120, 120));
		g.drawLine(position.x + 15, position.y + 8, position.x + 15, position.y + 13);

		// Körper / Rüstung
		g.setColor(new Color(150, 150, 150));
		g.fillRect(position.x + 11, position.y + 14, 8, 10);

		g.setColor(Color.BLACK);
		g.drawRect(position.x + 11, position.y + 14, 8, 10);

		// Gürtel
		g.setColor(new Color(120, 70, 20));
		g.fillRect(position.x + 11, position.y + 20, 8, 2);

		// Umhang (links)
		g.setColor(new Color(120, 20, 20)); // dunkelrot
		g.fillRect(position.x + 9, position.y + 15, 2, 11);

		// Beine / Stiefel
		g.setColor(new Color(90, 90, 90));
		g.fillRect(position.x + 12, position.y + 24, 3, 4);
		g.fillRect(position.x + 16, position.y + 24, 3, 4);

		// Schild außen rechts (oval)
		g.setColor(new Color(80, 120, 160)); // bläulich
		g.fillOval(position.x + 19, position.y + 16, 9, 11);

		// Schildrand
		g.setColor(Color.BLACK);
		g.drawOval(position.x + 19, position.y + 16, 9, 11);

		// Schild-Symbol (kleines Kreuz)
		g.setColor(new Color(230, 230, 230));
		g.drawLine(position.x + 23, position.y + 18, position.x + 23, position.y + 25);
		g.drawLine(position.x + 21, position.y + 21, position.x + 25, position.y + 21);

		// Arm (zum Schild)
		g.setColor(new Color(130, 130, 130));
		g.drawLine(position.x + 19, position.y + 17, position.x + 22, position.y + 18);
	}


	// Spielfigur: RedPotion (rot)
	private void drawRedPotion(Graphics g, Point position) {
		// Glas
		g.setColor(new Color(180, 180, 180));
		g.drawOval(position.x + 8, position.y + 10, 14, 16);

		// Inhalt
		g.setColor(new Color(200, 0, 0));
		g.fillOval(position.x + 9, position.y + 16, 12, 9);

		// Hals
		g.setColor(new Color(160, 160, 160));
		g.fillRect(position.x + 12, position.y + 6, 6, 6);

		// Korken
		g.setColor(new Color(120, 70, 20));
		g.fillRect(position.x + 12, position.y + 4, 6, 3);
	}

	// Spielfigur: RunePotion (lila + braune Rune)
	private void drawRunePotion(Graphics g, Point position) {
		// Glas
		g.setColor(new Color(180, 180, 180));
		g.drawOval(position.x + 8, position.y + 10, 14, 16);

		// Inhalt
		g.setColor(new Color(120, 0, 170));
		g.fillOval(position.x + 9, position.y + 16, 12, 9);

		// Hals
		g.setColor(new Color(160, 160, 160));
		g.fillRect(position.x + 12, position.y + 6, 6, 6);

		// Rune (braun)
		g.setColor(new Color(120, 70, 20));
		g.drawLine(position.x + 13, position.y + 18, position.x + 17, position.y + 18);
		g.drawLine(position.x + 15, position.y + 16, position.x + 15, position.y + 23);
		g.drawLine(position.x + 13, position.y + 22, position.x + 17, position.y + 22);
	}

	// Spielfigur: Lebensstein (grün, leuchtend)
	private void drawLifeStone(Graphics g, Point position) {
		// rundlicher "Herzstein" mit magischem Kern (rot)
		// Außenform (abgerundeter Stein)
		g.setColor(new Color(170, 40, 40)); // dunkelrot
		g.fillOval(position.x + 6, position.y + 7, 18, 18);

		// leichte "Stein"-Kante
		g.setColor(Color.BLACK);
		g.drawOval(position.x + 6, position.y + 7, 18, 18);

		// innerer Kern (heller, leuchtend)
		g.setColor(new Color(255, 90, 90)); // hellrot
		g.fillOval(position.x + 11, position.y + 12, 8, 8);

		// magisches Detail (kleines Symbol)
		g.setColor(new Color(255, 220, 220));
		g.drawLine(position.x + 15, position.y + 9, position.x + 15, position.y + 23);
		g.drawLine(position.x + 12, position.y + 16, position.x + 18, position.y + 16);

		// Glanzpunkt
		g.setColor(new Color(255, 240, 240));
		g.fillOval(position.x + 9, position.y + 10, 3, 3);
	}


	// Spielfigur: Kristall
	private void drawCrystal(Graphics g, Point position) {
		// Facettierter Kristall
		int[] x = {
				position.x + 15,
				position.x + 9,
				position.x + 11,
				position.x + 8,
				position.x + 15,
				position.x + 22,
				position.x + 19,
				position.x + 21
		};
		int[] y = {
				position.y + 3,
				position.y + 9,
				position.y + 15,
				position.y + 22,
				position.y + 27,
				position.y + 22,
				position.y + 15,
				position.y + 9
		};

		// Grundfläche (bläulich)
		g.setColor(new Color(70, 160, 255));
		g.fillPolygon(x, y, x.length);

		// Facetten (heller)
		g.setColor(new Color(180, 230, 255));
		g.fillPolygon(
				new int[]{position.x + 15, position.x + 11, position.x + 15},
				new int[]{position.y + 3,  position.y + 15, position.y + 27},
				3
		);

		// Facette (dunkler)
		g.setColor(new Color(30, 110, 200));
		g.fillPolygon(
				new int[]{position.x + 15, position.x + 19, position.x + 15},
				new int[]{position.y + 3,  position.y + 15, position.y + 27},
				3
		);

		// Glanzkanten
		g.setColor(new Color(245, 250, 255));
		g.drawLine(position.x + 13, position.y + 6, position.x + 10, position.y + 12);
		g.drawLine(position.x + 10, position.y + 12, position.x + 12, position.y + 18);

		// Outline
		g.setColor(Color.BLACK);
		g.drawPolygon(x, y, x.length);
	}

	// Spielfigur: Goldbeutel
	private void drawGoldPouch(Graphics g, Point position) {
		// Hauptkörper (Beutel)
		g.setColor(new Color(184, 134, 11)); // Goldbraun
		g.fillOval(position.x + 6, position.y + 10, 18, 16);

		// Beutelöffnung (zusammengezogen)
		g.setColor(new Color(139, 69, 19)); // Dunkelbraun
		g.fillRect(position.x + 9, position.y + 6, 12, 6);

		// Schnur
		g.setColor(new Color(90, 50, 20));
		g.drawLine(position.x + 9,  position.y + 9, position.x + 21, position.y + 9);
		g.drawLine(position.x + 11, position.y + 11, position.x + 19, position.y + 11);

		// Knoten
		g.fillOval(position.x + 13, position.y + 10, 4, 4);

		// Falten (dezente Linien)
		g.setColor(new Color(160, 120, 40));
		g.drawLine(position.x + 12, position.y + 14, position.x + 12, position.y + 23);
		g.drawLine(position.x + 18, position.y + 14, position.x + 18, position.y + 23);

		// Gold-Highlights
		g.setColor(new Color(230, 200, 120));
		g.fillOval(position.x + 10, position.y + 15, 3, 3);
		g.fillOval(position.x + 17, position.y + 17, 3, 3);

		// Outline
		g.setColor(Color.BLACK);
		g.drawOval(position.x + 6, position.y + 10, 18, 16);
		g.drawRect(position.x + 9, position.y + 6, 12, 6);
	}

	// Spielfigur: Medaillion
	private void drawMedallion(Graphics g, Point position) {
		// Band oben (braun)
		g.setColor(new Color(120, 70, 20));
		g.fillRect(position.x + 11, position.y + 6, 8, 3);

		// kleiner Ring/Öse
		g.setColor(Color.BLACK);
		g.drawOval(position.x + 13, position.y + 4, 4, 4);

		// Anhänger außen (lila)
		g.setColor(new Color(160, 120, 200));
		g.fillOval(position.x + 7, position.y + 8, 16, 18);

		// Anhänger innen (braun/gold)
		g.setColor(new Color(120, 70, 20));
		g.fillOval(position.x + 11, position.y + 12, 8, 10);

		// kleines Symbol innen
		g.setColor(new Color(230, 210, 120));
		g.drawLine(position.x + 15, position.y + 13, position.x + 15, position.y + 20);
		g.drawLine(position.x + 13, position.y + 16, position.x + 17, position.y + 16);

		// Outline
		g.setColor(Color.BLACK);
		g.drawOval(position.x + 7, position.y + 8, 16, 18);
	}

	// Spielfigur: Stahlhelm
	private void drawHelmet(Graphics g, Point position) {
		// Helmform
		g.setColor(new Color(160, 160, 160)); // Stahlgrau
		g.fillOval(position.x + 6, position.y + 8, 18, 14);

		// Visier
		g.setColor(Color.DARK_GRAY);
		g.fillRect(position.x + 10, position.y + 14, 10, 4);

		// Rand
		g.setColor(Color.BLACK);
		g.drawOval(position.x + 6, position.y + 8, 18, 14);
	}

	// Spielfigur: Relikt (als kleines Idol)
	private void drawRelic(Graphics g, Point position) {
		// Sockel
		g.setColor(new Color(120, 90, 60)); // Steinbraun
		g.fillRect(position.x + 8, position.y + 21, 14, 5);

		g.setColor(Color.BLACK);
		g.drawRect(position.x + 8, position.y + 21, 14, 5);

		// Körper
		g.setColor(new Color(170, 140, 90)); // Sandstein
		g.fillRoundRect(position.x + 10, position.y + 8, 10, 14, 4, 4);

		g.setColor(Color.BLACK);
		g.drawRoundRect(position.x + 10, position.y + 8, 10, 14, 4, 4);

		// Goldene Rune/Symbol
		g.setColor(new Color(220, 180, 70)); // Gold
		g.drawOval(position.x + 13, position.y + 12, 4, 4);
		g.drawLine(position.x + 15, position.y + 16, position.x + 15, position.y + 19);
		g.drawLine(position.x + 13, position.y + 18, position.x + 17, position.y + 18);

		// Glanzpunkt
		g.setColor(new Color(245, 235, 200));
		g.fillOval(position.x + 12, position.y + 10, 2, 2);
	}

	// Spielfigur: Pergamentrolle
	private void drawScroll(Graphics g, Point position) {
		// Pergamentfläche (leicht warm)
		g.setColor(new Color(240, 225, 190));
		g.fillRoundRect(position.x + 6, position.y + 9, 18, 12, 6, 6);

		// Schattenkante
		g.setColor(new Color(210, 190, 150));
		g.drawRoundRect(position.x + 6, position.y + 9, 18, 12, 6, 6);

		// Eingerollte Enden
		g.setColor(new Color(200, 180, 140));
		g.fillOval(position.x + 4, position.y + 9, 5, 12);
		g.fillOval(position.x + 21, position.y + 9, 5, 12);

		g.setColor(Color.BLACK);
		g.drawOval(position.x + 4, position.y + 9, 5, 12);
		g.drawOval(position.x + 21, position.y + 9, 5, 12);

		// Dezente Schnur/Band in der Mitte
		g.setColor(new Color(140, 90, 40));
		g.fillRect(position.x + 14, position.y + 9, 2, 12);

		// Kleiner Knoten (dezent)
		g.fillOval(position.x + 13, position.y + 14, 4, 4);

		// Textlinien
		g.setColor(new Color(90, 90, 90));
		g.drawLine(position.x + 8, position.y + 13, position.x + 20, position.y + 13);
		g.drawLine(position.x + 8, position.y + 16, position.x + 18, position.y + 16);
		g.drawLine(position.x + 8, position.y + 19, position.x + 19, position.y + 19);
	}

	@FunctionalInterface
	interface Drawable {
		void draw(Graphics g, Point position);
	}
}
