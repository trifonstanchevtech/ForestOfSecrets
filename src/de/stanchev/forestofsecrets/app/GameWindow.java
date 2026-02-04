package de.stanchev.forestofsecrets.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Grafische Benutzeroberfläche (Swing) für das Spiel "Forest of Secrets".
 *
 * Aufgaben dieser Klasse:
 * - Anzeige der Eingabedaten (Titel, Name, Alter, Startposition)
 * - Anzeige der aktuellen Werte (Position, Energie)
 * - Anzeige von Spielnachrichten
 * - Steuerung über Buttons und Pfeiltasten
 *
 * Hinweis:
 * Das Spielfenster muss aktiv/fokussiert sein, damit die Tastatursteuerung funktioniert.
 *
 * Diese Klasse enthält bewusst keine Spiellogik. Aktionen werden an den {@link GameController}
 * weitergeleitet (Controller-Prinzip / MVC-View).
 *
 * @author Trifon Stanchev
 * @version 1.0
 */
public class GameWindow extends JFrame {

    private final GameController myControl;

    private JTextField txtTitel;   // z.B. "King", "Sir"
    private JTextField txtName;    // z.B. "Arthur"
    private JTextField txtAlter;   // z.B. 30
    private JTextField txtX;
    private JTextField txtY;
    private JTextField txtEnergie;

    private JTextArea txtNachrichtArea;

    private JButton btnAnwenden;   // Start
    private JButton btnBeenden;    // Exit
    private JButton btnMoveUp;
    private JButton btnMoveDown;
    private JButton btnMoveLeft;
    private JButton btnMoveRight;

    /**
     * Erstellt das Hauptfenster und verbindet es mit dem Controller.
     *
     * @param control Controller (Steuerklasse), der Benutzeraktionen verarbeitet
     */
    public GameWindow(GameController control) {
        this.myControl = control;
        initUI();
    }

    /**
     * Baut die komplette GUI auf (Layout, Felder, Buttons, Events).
     * Wird im Konstruktor einmalig aufgerufen.
     */
    public void initUI() {
        setTitle("Forest of Secrets");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Grund-Defaults
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        // Titelbereich (Überschrift + Icon)
        JLabel title = createTitleLabel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);

        // Reset nach Titel
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        // Eingabefelder
        addLabel(gbc, "Titel:", 0, 1);
        txtTitel = new JTextField();
        txtTitel.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtTitel, 1, 1);

        // Pfeilkreuz
        createMoveButtons();
        JPanel arrows = createArrowsPanel();

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(arrows, gbc);

        // Reset nach Pfeilkreuz
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;

        addLabel(gbc, "Name:", 0, 2);
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtName, 1, 2);

        addLabel(gbc, "Alter:", 0, 3);
        txtAlter = new JTextField();
        txtAlter.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtAlter, 1, 3);

        addLabel(gbc, "X:", 0, 4);
        txtX = new JTextField();
        txtX.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtX, 1, 4);

        addLabel(gbc, "Y:", 0, 5);
        txtY = new JTextField();
        txtY.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtY, 1, 5);

        addLabel(gbc, "Energie:", 0, 6);
        txtEnergie = new JTextField();
        txtEnergie.setPreferredSize(new Dimension(200, 24));
        txtEnergie.setEditable(false);
        addField(gbc, txtEnergie, 1, 6);

        // Nachrichtenfeld
        addLabel(gbc, "Nachricht:", 0, 7);

        txtNachrichtArea = new JTextArea(6, 22);
        txtNachrichtArea.setEditable(false);
        txtNachrichtArea.setLineWrap(true);
        txtNachrichtArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtNachrichtArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(260, 110));

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scroll, gbc);

        // Reset nach Nachrichtfeld
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Start/Beenden Buttons unten (gleich breit)
        JPanel actionPanel = createActionPanel();

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(actionPanel, gbc);

        // Events & Key-Bindings
        wireEvents();
        setupKeyBindings();

        // Default-Werte
        setDefaultValues();
    }

    /** Erstellt die Überschrift inkl. optionalem Bild. */
    private JLabel createTitleLabel() {
        JLabel title = new JLabel("Finde die Schatztruhe", SwingConstants.CENTER);
        title.setForeground(new Color(76, 7, 7, 252));
        title.setFont(new Font("Arial", Font.BOLD, 22));

        // Schatztruhe-Bild aus Ressourcen laden
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/schatztruhe.png"));
            title.setIcon(icon);
            title.setIconTextGap(12);
        } catch (Exception ignored) {
            // Falls PNG nicht gefunden wird: Fallback (Emoji)
            title.setText("\uD83D\uDCB0  Finde die Schatztruhe");
        }

        title.setHorizontalTextPosition(SwingConstants.RIGHT);
        title.setVerticalTextPosition(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        return title;
    }

    /** Initialisiert die Bewegungsbuttons und deaktiviert sie zunächst (bis Start gedrückt wurde). */
    private void createMoveButtons() {
        btnMoveUp = new JButton("^");
        btnMoveDown = new JButton("v");
        btnMoveLeft = new JButton("<");
        btnMoveRight = new JButton(">");

        btnMoveUp.setEnabled(false);
        btnMoveDown.setEnabled(false);
        btnMoveLeft.setEnabled(false);
        btnMoveRight.setEnabled(false);
    }

    /** Baut das Pfeilkreuz-Panel. */
    private JPanel createArrowsPanel() {
        JPanel arrows = new JPanel(new GridLayout(3, 3, 6, 6));
        arrows.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        arrows.add(new JLabel());
        arrows.add(btnMoveUp);
        arrows.add(new JLabel());
        arrows.add(btnMoveLeft);
        arrows.add(new JLabel());
        arrows.add(btnMoveRight);
        arrows.add(new JLabel());
        arrows.add(btnMoveDown);
        arrows.add(new JLabel());
        return arrows;
    }

    /** Erstellt das Panel mit Start/Beenden Buttons (gleich groß). */
    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        btnAnwenden = new JButton("Start");
        btnBeenden = new JButton("Beenden");

        Dimension btnSize = new Dimension(120, 28);
        btnAnwenden.setPreferredSize(btnSize);
        btnBeenden.setPreferredSize(btnSize);

        actionPanel.add(btnAnwenden);
        actionPanel.add(btnBeenden);
        return actionPanel;
    }

    /** Verbindet Button-Events zum Controller. */
    private void wireEvents() {
        btnAnwenden.addActionListener(e -> myControl.startGame());
        btnMoveUp.addActionListener(e -> myControl.moveUp());
        btnMoveDown.addActionListener(e -> myControl.moveDown());
        btnMoveLeft.addActionListener(e -> myControl.moveLeft());
        btnMoveRight.addActionListener(e -> myControl.moveRight());
        btnBeenden.addActionListener(e -> myControl.exit(0));
    }

    /** Setzt Key-Bindings (Pfeiltasten) für Bewegung. */
    private void setupKeyBindings() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");

        actionMap.put("moveUp", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (btnMoveUp.isEnabled()) myControl.moveUp();
            }
        });
        actionMap.put("moveDown", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (btnMoveDown.isEnabled()) myControl.moveDown();
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (btnMoveLeft.isEnabled()) myControl.moveLeft();
            }
        });
        actionMap.put("moveRight", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (btnMoveRight.isEnabled()) myControl.moveRight();
            }
        });
    }

    private void addLabel(GridBagConstraints gbc, String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;
        add(lbl, gbc);
    }

    private void addField(GridBagConstraints gbc, JComponent field, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.WEST;

        // damit Textfelder wirklich breit werden
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(field, gbc);

        // Reset
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    // Getter für Controller
    public JTextField getTxtTitel() { return txtTitel; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtAlter() { return txtAlter; }
    public JTextField getTxtX() { return txtX; }
    public JTextField getTxtY() { return txtY; }
    public JTextField getTxtEnergie() { return txtEnergie; }

    public JButton getBtnAnwenden() { return btnAnwenden; }
    public JButton getBtnMoveUp() { return btnMoveUp; }
    public JButton getBtnMoveDown() { return btnMoveDown; }
    public JButton getBtnMoveLeft() { return btnMoveLeft; }
    public JButton getBtnMoveRight() { return btnMoveRight; }

    // Nachrichten-Ausgabe
    /** Setzt die Nachricht (ersetzt den bisherigen Text). */
    public void setNachricht(String text) {
        txtNachrichtArea.setText(text);
    }

    /** Hängt eine Nachricht an (optional). */
    public void addNachricht(String text) {
        txtNachrichtArea.append(text + "\n");
    }

    /** Setzt Default-Werte für einen schnellen Spielstart. */
    private void setDefaultValues() {
        txtTitel.setText("King");
        txtName.setText("Arthur");
        txtAlter.setText("30");
        txtX.setText("210");
        txtY.setText("180");
    }
}
