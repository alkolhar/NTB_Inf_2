import java.awt.*;
import java.awt.BorderLayout;

import javax.swing.*;

/**
 * Hauptklasse, erzeugt das GUI und ruft weitere Methoden auf
 */
public class Pong extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new Pong();
    }

    public Pong() {
        super("Spielfeld; Anzahl Bälle: 1");
        createApplication();
    }

    private void createApplication() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);

        // Spielfeld
        Spielfeld sp = new Spielfeld();
        add(sp, BorderLayout.CENTER);

        // Button panel hinzufuegen
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 2));       

        // Slider Anzahl Baelle
        JSlider slider = new JSlider(1, 5, 1);
        btnPanel.add(addSlider(slider));

        // Start Button
        JButton startButton = new JButton("Starten");
        startButton.addActionListener(e -> { sp.spielStarten(Integer.valueOf(slider.getValue()), false); });
        btnPanel.add(startButton);

        // Start Button Eigenspiel
        JButton startAIButton = new JButton("Anfängermodus");
        startAIButton.addActionListener(e -> { sp.spielStarten(Integer.valueOf(slider.getValue()), true); });
        btnPanel.add(startAIButton);

        // Button Panel hinzufuegen
        add(btnPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private JSlider addSlider(JSlider slider) {
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);

        slider.addChangeListener(e -> {
            super.setTitle("Spielfeld; Anzahl Bälle: " + slider.getValue());
        });
        
        return slider;
    }

}