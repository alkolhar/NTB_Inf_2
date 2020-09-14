import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;

/**
 * Ticketautomat GUI BoarderLayout - Container - Teil links (Button oben) - Teil
 * rechts (Grid)
 * 
 * @author akoller
 * @version 10.03.2020
 */
public class TicketautomatGUI {
    private JFrame myFrame;
    private Ticketautomat TickAut;

    public static void main(String[] args) {
        new TicketautomatGUI();
    }

    public TicketautomatGUI() {
        TickAut = new Ticketautomat();
        myFrame = createApplicationFrame();
        myFrame.setVisible(true);
    }

    /**
     * createApplicationFrame erzeugt die ContentPane und fügt deren einzelnen
     * Sektionen hinzu
     * 
     * @return fr JFrame des GUIs
     */
    private JFrame createApplicationFrame() {
        // JFrame erzeugen
        JFrame fr = new JFrame("Ticketautomat");
        // Schliessbedingung festlegen
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Startgrösse festsetzen (in Pixel)
        fr.setSize(800, 600);

        // Container & BorderLayout
        Container myContentPane = fr.getContentPane();
        BorderLayout layout = new BorderLayout();
        myContentPane.setLayout(layout);

        // Befehlszeile hinzufügen
        myContentPane.add(createCommandLine(), BorderLayout.SOUTH);

        // Mittlerer Teil hinzufügen
        myContentPane.add(createCenterSection(), BorderLayout.CENTER);

        // JFrame anzeigen
        fr.setVisible(true);

        return fr;
    }

    /**
     * createCommandLine fügt die Befehlszeile hinzu Die Befehlszeile beinhalten die
     * Buttons 'Fahrkarte kaufen' und 'Guthaben auszahlen'
     * 
     * @return pnCommands JPanel der Befehlszeile
     */
    private JPanel createCommandLine() {
        // Subpanel 'Befehlszeile' erstellen
        JPanel pnCommands = new JPanel();
        // Buttons zu Subpanel hinzufügen
        pnCommands.add(new JButton("Fahrkarte kaufen"));
        pnCommands.add(new JButton("Guthaben auszahlen"));

        return pnCommands;
    }

    /**
     * createCenterSection fügt ein GridLayout hinzu. Das GridLayout wird für die
     * Aufteilung von Ticketpreis (links) und Geldeinwurf (rechts) benötigt
     * 
     * @return pnCenter JPanel des zentralen GridLayouts
     */
    private JPanel createCenterSection() {
        JPanel pnCenter = new JPanel();
        pnCenter.setLayout(new GridLayout(0, 2));

        // Aufteilen in 'Fahrkarten' und 'Geldeinwurf'
        pnCenter.add(createTicketSection());
        pnCenter.add(createPaySection());

        return pnCenter;
    }

    /**
     * createTicketSection fügt den 'Fahrkarten' Bereich hinzu. Im Ticketbereich
     * wird auch die Preissektion eingebunden (createPriceSection())
     * 
     * @return pnTicket JPanel der 'Fahrkarten' Sektion
     */
    private JPanel createTicketSection() {
        JPanel pnTicket = new JPanel();
        pnTicket.setLayout(new BorderLayout());
        pnTicket.setBorder(BorderFactory.createTitledBorder("Fahrkarten"));

        pnTicket.add(createPriceSection());

        return pnTicket;
    }

    /**
     * createPriceSection fügt die 'Preis' Sektion hinzu. Diese Sektion besteht aus
     * Umrahmung mit Titel, sowie einem grün gefärbtem Label
     * 
     * @return pnTicket JPanel der 'Preis' Sektion
     */
    private JPanel createPriceSection() {
        JPanel pnTicket = new JPanel();
        TitledBorder tBorder = BorderFactory.createTitledBorder("Preis");
        tBorder.setTitleJustification(TitledBorder.CENTER);
        pnTicket.setBorder(tBorder);

        JLabel lblTicket = new JLabel();
        lblTicket.setPreferredSize(new DimensionUIResource(200, 80));
        lblTicket.setBackground(new ColorUIResource(0, 255, 0));
        lblTicket.setText(String.valueOf(TickAut.getTicketPrice()));

        pnTicket.add(lblTicket);
        return pnTicket;
    }

    /**
     * createPaySection fügt die 'Geldeinwurf' Sektion hinzu. Diese Sektion besteht
     * aus einem nördlichen (Guthaben) und südlichen (Münzen) Teil
     * 
     * @return pnPayment JPanel der Sektion 'Geldeinwurf'
     */
    private JPanel createPaySection() {
        JPanel pnPayment = new JPanel();
        pnPayment.setLayout(new BorderLayout());
        pnPayment.setBorder(BorderFactory.createTitledBorder("Geldeinwurf"));

        pnPayment.add(createCreditSection(), BorderLayout.NORTH);
        pnPayment.add(createValueSection(), BorderLayout.CENTER);

        return pnPayment;
    }

    /**
     * createCreditSection erstellt die 'Guthaben' Sektion. Diese besteht aus
     * Umrahmung mit Titel, sowie einem grün gefärbtem Label
     * 
     * @return pnCredit JPanel der 'Guthaben' Sektion
     */
    private JPanel createCreditSection() {
        JPanel pnCredit = new JPanel();
        TitledBorder tBorder = BorderFactory.createTitledBorder("Guthaben");
        tBorder.setTitleJustification(TitledBorder.CENTER);
        pnCredit.setBorder(tBorder);

        JLabel lblCredit = new JLabel();
        lblCredit.setBackground(new ColorUIResource(0, 255, 0));
        lblCredit.setText(String.valueOf(TickAut.getAlreadyPaid()));

        pnCredit.add(lblCredit);
        return pnCredit;
    }

    /**
     * createValueSection erstellt die 'Münzen' Sektion. Sie besteht aus sechs
     * Buttons, welche im GridLayout aufgelistet werden.
     * 
     * @return pnCredit JPanel der 'Münzen' Sektion
     */
    private JPanel createValueSection() {
        JPanel pnCredit = new JPanel();

        pnCredit.setLayout(new GridLayout(3, 2, 50, 10));

        pnCredit.add(addPayButton(5.00));
        pnCredit.add(addPayButton(0.50));
        pnCredit.add(addPayButton(2.00));
        pnCredit.add(addPayButton(0.20));
        pnCredit.add(addPayButton(1.00));
        pnCredit.add(addPayButton(0.10));

        return pnCredit;
    }

    /**
     * addPayButton erstellt einen neuen Button mit Preisangabe und ActionListener
     * mit Bezahlfunktion
     * 
     * @param value Preis, bezahlter Betrag
     * @return JButton
     */
    private JButton addPayButton(double value) {
        JButton buf = new JButton("Fr. " + String.format("%.2f", value));
        buf.addActionListener(e -> {
            TickAut.pay(value);
        });
        return buf;
    }
}