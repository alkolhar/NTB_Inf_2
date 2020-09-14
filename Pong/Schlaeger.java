import java.awt.Color;
import javax.swing.JPanel;

/**
 * Stellt den Schlaeger dar, ableitung der Klasse JPanel
 */
public class Schlaeger extends JPanel {
    private static final long serialVersionUID = 1L;

    public Schlaeger(int width) {
        setBackground(Color.green);
        setBounds(0, 0, width, 20);
    }
    
}