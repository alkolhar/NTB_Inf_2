import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;

import java.io.*;
import javax.imageio.*;

/**
 * Bildbetrachter ist die Hauptklasse der Bildbetrachter-Anwendung. Sie
 * erstellt die GUI der Anwendung, zeigt sie an und initialisiert alle
 * anderen Komponenten.
 * 
 * @author Carlo Bach (auf Basis von Michael K�lling und David J Barnes)
 * @version 1.1
 * 1.1: Eigenes ImagePanel durch JLabel(ImageIcon)) ersetzt.
 */
public class Bildbetrachter extends JFrame {

    private static final long serialVersionUID = 1L;
    // statische Datenfelder (Klassenkonstanten und -variablen)
    private static final String VERSION = "Version 10.01.2018";
    private static JFileChooser dateiauswahldialog = new JFileChooser(System.getProperty("user.dir"));

    // Datenfelder
    private Bild aktuellesBild;

    private ImageIcon bildPanel;
    private JLabel bildLabel;
    private HistogramPanel histogramPanel;

    public static void main(String[] args) {
        new Bildbetrachter();
    }

    /**
     * Erzeuge einen Bildbetrachter und zeige seine GUI auf dem Bildschirm an.
     */
    public Bildbetrachter() {
        aktuellesBild = null;
        fensterErzeugen();
    }

    // ---- Implementierung der Menu-Funktionen ----
    /**
     * 'Datei oeffnen'-Funktion: öffnet einen Dateiauswahldialog zur 
     * Auswahl einer Bilddatei und zeigt das selektierte Bild an.
     */
    public void dateiOeffnen() {
        Bild b = bildOeffnen("Bild auswählen");
        if (b != null) {
            setzeBild(b);
        }
    }

    private Bild bildOeffnen(String title) {
        dateiauswahldialog.setDialogTitle(title);
        int ergebnis = dateiauswahldialog.showOpenDialog(this);
        if(ergebnis != JFileChooser.APPROVE_OPTION) { // abgebrochen
            return null;  
        }
        File selektierteDatei = dateiauswahldialog.getSelectedFile();

        BufferedImage img = null;
        try {
            img = ImageIO.read(selektierteDatei);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Die Datei konnte nicht gelesen werden.",
                "Fehler beim Bildladen",
                JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (img != null) {
            return new Bild(selektierteDatei.getName(), getGreyValues(img));
        } else {
            JOptionPane.showMessageDialog(this, "Dieses Bildformat wird nicht unterstützt.",
                "Fehler beim Bildladen",
                JOptionPane.ERROR_MESSAGE);
            return null;
        }            
    }

    public void aktualisieren() {
        setzeBild(aktuellesBild);
    }

    public void setzeBild(Bild bild) {
        if (bild == null) return;

        aktuellesBild = bild;

        int w = bild.gibBreite();
        int h = bild.gibHoehe();
        BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster r = bi.getRaster();
        int[] pixel1D = new int[w*h];
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                pixel1D[y*w+x] = bild.gibIntensitaetswert(x, y);
            }
        }
        r.setPixels(0,0,w,h,pixel1D);
        bildPanel.setImage(bi);  
        bildLabel.repaint();

        histogramPanel.setValues(bild.gibHistogramm());

        setTitle(bild.gibTitel());

        pack();
    }

    public Bild gibBild() {
        return aktuellesBild;
    }

    /**
     * 'Schliessen'-Funktion: Schliesst das aktuelle Bild.
     */
    private void schliessen() {
    }

    /**
     * 'Speichern unter'-Funktion: Speichert das aktuelle
     * Bild in eine Datei. 
     */
    private void speichernUnter() {
        int ergebnis = dateiauswahldialog.showSaveDialog(this);
        if(ergebnis != JFileChooser.APPROVE_OPTION) { // abgebrochen
            return;  
        }

        try {
            // retrieve image
            BufferedImage bi = (BufferedImage)bildPanel.getImage();
            ImageIO.write(bi, "png", dateiauswahldialog.getSelectedFile());
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

    /**
     * 'Beenden'-Funktion: Beendet die Anwendung.
     */
    private void beenden() {
        System.exit(0);
    }

    /**
     * 'Info'-Funktion: Zeige Informationen zur Anwendung.
     */
    private void zeigeInfo() {
        JOptionPane.showMessageDialog(this, 
            "Bildbetrachter\n" + VERSION + "\nCarlo Bach, NTB",
            "Info zu Bildbetrachter", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    // ---- Swing-Anteil zum Erzeugen des Fensters mit allen Komponenten ----
    /**
     * Erzeuge das Swing-Fenster samt Inhalt.
     */
    private void fensterErzeugen() {
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

        // Ein Layout mit hübschen Abständen definieren
        contentPane.setLayout(new BorderLayout(6, 6));

        menuezeileErzeugen(this);
        toolbarErzeugen(this);

        bildPanel = new ImageIcon(); //ImagePanel null);
        bildLabel = new JLabel(bildPanel);
        JScrollPane sp = new JScrollPane(bildLabel);
        sp.setBorder(new TitledBorder("Bild"));
        contentPane.add(sp,BorderLayout.CENTER);

        histogramPanel = new HistogramPanel(null);
        histogramPanel.setBorder(new TitledBorder("Histogramm"));
        contentPane.add(histogramPanel, BorderLayout.SOUTH);

        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * Die Menuzeile des Hauptfensters erzeugen.
     * 
     * @param fenster  Das Fenster, in das die Menuzeile eingefügt werden soll.
     */
    private void menuezeileErzeugen(JFrame fenster) {
        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        JMenuBar menuezeile = new JMenuBar();
        fenster.setJMenuBar(menuezeile);

        JMenu menue;
        JMenuItem eintrag;

        // Das Datei-Menu erzeugen
        menue = new JMenu("Datei");
        menuezeile.add(menue);

        eintrag = new JMenuItem("Öffnen...");
        eintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
        eintrag.addActionListener(e -> { dateiOeffnen(); });
        menue.add(eintrag);

        eintrag = new JMenuItem("Schliessen");
        eintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0));
        eintrag.addActionListener(e -> { schliessen(); });
        menue.add(eintrag);
        menue.addSeparator();

        eintrag = new JMenuItem("Speichern unter...");
        eintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, SHORTCUT_MASK));
        eintrag.addActionListener(e -> { speichernUnter(); });
        menue.add(eintrag);
        menue.addSeparator();

        eintrag = new JMenuItem("Beenden");
        eintrag.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        eintrag.addActionListener(e -> { beenden(); });
        menue.add(eintrag);

        // Das Hilfe-Menu erzeugen
        menue = new JMenu("Hilfe");
        menuezeile.add(menue);

        eintrag = new JMenuItem("Info...");
        eintrag.addActionListener(e -> { zeigeInfo(); });
        menue.add(eintrag);

    }

    private void toolbarErzeugen(JFrame fenster)
    {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);

        JButton b;

        b = new JButton("Anzeige aktualisieren");
        b.addActionListener(e -> { aktualisieren(); });        
        tb.add(b);

        b = new JButton("Invertieren");
        b.addActionListener(e -> { aktuellesBild.bild_invertieren(); aktualisieren(); });        
        tb.add(b);

        b = new JButton("Aufhellen");
        b.addActionListener(e -> { 
                    String eingabe = JOptionPane.showInputDialog("Offset?"); 
                    aktuellesBild.bild_aufhellen(Integer.parseInt(eingabe)); aktualisieren(); 
                });        
        tb.add(b);

        b = new JButton("Kontrast erhoehen");
        b.addActionListener(e -> { 
                    String eingabe = JOptionPane.showInputDialog("Faktor?"); 
                    aktuellesBild.bild_kontrastErhoehen(Double.parseDouble(eingabe)); aktualisieren(); 
                });        
        tb.add(b);

        b = new JButton("Kontrast optimieren");
        b.addActionListener(e -> { aktuellesBild.bild_kontrastOptimieren(); aktualisieren(); });        
        tb.add(b);

        b = new JButton("Schwellwert");
        b.addActionListener(e -> { 
                    String eingabe = JOptionPane.showInputDialog("Schwellwert?"); 
                    aktuellesBild.bild_schwellwertAnwenden(Integer.parseInt(eingabe)); aktualisieren(); 
                });        
        tb.add(b);

        b = new JButton("Mittelwertfilter");
        b.addActionListener(e -> { 
                    Bild kopie = aktuellesBild.erstelleKopie();
                    kopie.bild_mittelwertfilter(aktuellesBild);
                    setzeBild(kopie);
                });        
        tb.add(b);

        b = new JButton("Sobelfilter");
        b.addActionListener(e -> { 
                    Bild kopie = aktuellesBild.erstelleKopie();
                    //hebt horizontale Kanten hervor
                    double[][] sobelfilter = {{-1.0, 0.0, 1.0}, {-2.0, 0.0, 2.0}, {-1.0, 0.0, 1.0}};
                    //hebt vertikale Kanten hervor
                    //double[][] sobelfilter = {{-1.0, -2.0, -1.0}, {0.0, 0.0, 0.0}, {1.0, 2.0, 1.0}};
                    kopie.bild_linearerfilter(aktuellesBild, sobelfilter);
                    setzeBild(kopie);
                });        

        tb.add(b);
        b = new JButton("Medianfilter");
        b.addActionListener(e -> { 
                    Bild kopie = aktuellesBild.erstelleKopie();
                    kopie.bild_medianfilter(aktuellesBild);
                    setzeBild(kopie);
                });        
        tb.add(b);

        b = new JButton("Template Matching");
        b.addActionListener(e -> { 
                    Bild kopie = aktuellesBild.erstelleKopie();
                    Bild bild =  bildOeffnen("Template ausw�hlen");
                    if (bild != null) {
                        kopie.bild_templatematching(aktuellesBild, bild);
                        kopie.bild_kontrastOptimieren(); 
                        //kopie.bild_schwellwertAnwenden(100);
                        setzeBild(kopie);
                    }
                });        
        tb.add(b);

        b = new JButton("Rotation");
        b.addActionListener(e -> { 
                    String eingabe = JOptionPane.showInputDialog("Winkel (in Radian)?"); 
                    Bild kopie = aktuellesBild.erstelleKopie();
                    kopie.bild_rotation(aktuellesBild, aktuellesBild.gibBreite()/2, aktuellesBild.gibHoehe()/2, Double.parseDouble(eingabe));
                    setzeBild(kopie);
                });        
        tb.add(b);

        b = new JButton("Twirl");
        b.addActionListener(e -> { 
                    Bild kopie = aktuellesBild.erstelleKopie();
                    kopie.bild_twirl(aktuellesBild, (int)(0.9*aktuellesBild.gibBreite()/2), Math.PI/4);
                    setzeBild(kopie);
                });        
        tb.add(b);

        fenster.add(tb, BorderLayout.NORTH);
    }

    private int[][] getGreyValues(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = bi.createGraphics();
        g.drawImage(img,0,0,null);
        WritableRaster r = bi.getRaster();
        int[] pixel1D; int[] iArray=null;
        pixel1D = r.getPixels(0,0,w,h,iArray);
        int[][] pixel2D = new int[w][h];
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                pixel2D[x][y] = (int) pixel1D[y*w+x];
            }
        }
        return pixel2D;
    }

}
