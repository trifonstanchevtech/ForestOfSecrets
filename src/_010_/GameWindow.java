package _010_;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Titel: Forest of Secrets – ConsoleUI
 *
 * Beschreibung:
 * Grafische Benutzeroberfläche (Swing) für das Spiel „Forest of Secrets“.
 * Diese Klasse ist ausschließlich für die Darstellung und Benutzereingaben
 * zuständig und enthält keine Spiellogik.
 *
 * Funktionen:
 * - Anzeige der Rittereigenschaften
 * - Anzeige von Spielnachrichten
 * - Steuerung über Buttons und Tastatur (Pfeiltasten)
 *
 * Architektur:
 * - Teil der MVC-Struktur (View)
 * - Kommuniziert ausschließlich mit {@link GameController}
 *
 * Hinweis:
 * Das Spielfenster muss aktiv/fokussiert sein, damit die Tastatursteuerung funktioniert.
 *
 * @author Trifon Stanchev
 * @version 1.0
 */

public class GameWindow extends JFrame {

    private final GameController myControl;

    private JTextField txtTitel;  // Bedeutung: Titel (z.B. King, Sir)
    private JTextField txtName;   // Bedeutung: Name (z.B. Arthur)
    private JTextField txtAlter;  // Bedeutung: Alter
    private JTextField txtX;
    private JTextField txtY;
    private JTextField txtEnergie;

    private JTextArea txtNachrichtArea;

    private JButton btnAnwenden;
    private JButton btnBeenden;
    private JButton btnMoveUp;
    private JButton btnMoveDown;
    private JButton btnMoveLeft;
    private JButton btnMoveRight;

    public GameWindow(GameController control) {
        this.myControl = control;
        initUI();
    }

    public void initUI() {
        setTitle("Forest of Secrets");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Grund-Defaults für alle Komponenten
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        // Titel
        JLabel title = new JLabel("Finde die Schatztruhe", SwingConstants.CENTER);
        title.setForeground(new Color(76, 7, 7, 252));
        title.setFont(new Font("Arial", Font.BOLD, 22));

        // Schloss-Bild laden (liegt im gleichen Package wie _09_0_ConsoleUI)
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("Schloss.png"));
            title.setIcon(icon);
            title.setIconTextGap(12); // Abstand zwischen Icon und Text
        } catch (Exception ignored) {
            // Falls PNG nicht gefunden wird: optional Emoji
            title.setText("\uD83C\uDFF0  Finde die Schatztruhe");
        }

        // Wichtig: Text rechts vom Icon, aber das Gesamte zentriert
        title.setHorizontalTextPosition(SwingConstants.RIGHT);
        title.setVerticalTextPosition(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);

        // Reset nach Titel
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        // Titel
        addLabel(gbc, "Titel:", 0, 1);
        txtTitel = new JTextField();
        txtTitel.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtTitel, 1, 1);

        // Pfeilkreuz
        btnMoveUp = new JButton("^");
        btnMoveDown = new JButton("v");
        btnMoveLeft = new JButton("<");
        btnMoveRight = new JButton(">");

        btnMoveUp.setEnabled(false);
        btnMoveDown.setEnabled(false);
        btnMoveLeft.setEnabled(false);
        btnMoveRight.setEnabled(false);

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

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        add(arrows, gbc);

        // Reset nach Pfeilkreuz
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;

        // Name
        addLabel(gbc, "Name:", 0, 2);
        txtName = new JTextField();  // () - ohne Zahl
        txtName.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtName, 1, 2);

        // Alter
        addLabel(gbc, "Alter:", 0, 3);
        txtAlter = new JTextField();
        txtAlter.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtAlter, 1, 3);

        // X
        addLabel(gbc, "X:", 0, 4);
        txtX = new JTextField();
        txtX.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtX, 1, 4);

        // Y
        addLabel(gbc, "Y:", 0, 5);
        txtY = new JTextField();
        txtY.setPreferredSize(new Dimension(200, 24));
        addField(gbc, txtY, 1, 5);

        // Energie
        addLabel(gbc, "Energie:", 0, 6);
        txtEnergie = new JTextField();
        txtEnergie.setPreferredSize(new Dimension(200, 24));
        txtEnergie.setEditable(false);
        addField(gbc, txtEnergie, 1, 6);

        // Nachricht
        addLabel(gbc, "Nachricht:", 0, 7);

        txtNachrichtArea = new JTextArea(6, 22);
        txtNachrichtArea.setEditable(false);
        txtNachrichtArea.setLineWrap(true);
        txtNachrichtArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtNachrichtArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(260, 110)); // 300, 110

        // Scrollpane
        gbc.gridx = 0; // 0
        gbc.gridy = 8; // 8
        gbc.gridwidth = 2; // 2
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // 1.0
        gbc.weighty = 1.0; // 1.0

        add(scroll, gbc);

        // Reset nach Nachrichtfeld (sonst wächst alles weiter)
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridwidth = 1; // 1
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buttons unten
        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 12, 0)); // (1, 2, 12, 0)
        //JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        btnAnwenden = new JButton("Start");
        btnBeenden = new JButton("Beenden");
        Dimension btnSize = new Dimension(120, 28); // (120, 28)
        btnAnwenden.setPreferredSize(btnSize);
        btnBeenden.setPreferredSize(btnSize);
        actionPanel.add(btnAnwenden);
        actionPanel.add(btnBeenden);

        gbc.gridx = 0;
        gbc.gridy = 9; // 8
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(actionPanel, gbc);

        // Events
        btnAnwenden.addActionListener(e -> myControl.startGame());
        btnMoveUp.addActionListener(e -> myControl.moveUp());
        btnMoveDown.addActionListener(e -> myControl.moveDown());
        btnMoveLeft.addActionListener(e -> myControl.moveLeft());
        btnMoveRight.addActionListener(e -> myControl.moveRight());
        btnBeenden.addActionListener(e -> myControl.exit(0));

        // Key bindings
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

        setDefaultValues();
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

    // Getter
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

    public void setNachricht(String text) {
        txtNachrichtArea.setText(text);
    }

    // Optional: neue Nachricht anhängen
    public void addNachricht(String text) {
        txtNachrichtArea.append(text + "\n");
    }

    // Default-Werte setzen
    private void setDefaultValues() {
        txtTitel.setText("King");
        txtName.setText("Arthur");
        txtAlter.setText("30");
        txtX.setText("210");
        txtY.setText("180");
    }
}
