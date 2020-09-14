import javax.swing.JPanel;
import java.awt.*;

/**
 * Erzeugt den Ball im Spielfeld Standard: 20x20 pixel, schwarz x- &
 * y-Geschwindigkeit, dreht bei Wandkontakt
 */
public class Ball extends JPanel {
    private static final long serialVersionUID = 1L;
    int velx, vely;

    public Ball() {
        setBackground(Color.BLACK);
        setBounds(0, 0, 20, 20);
        velx = -10 + (int) (Math.random() * 11);
        vely = -10 + (int) (Math.random() * 11);
    }

    public void aktualisierePosition() {
        setLocation(getLocation().x + velx, getLocation().y - vely);
    }

    public void wechsleXRichtung() {
        velx = velx * -1;
    }

    public void wechsleYRichtung() {
        vely = vely * -1;
    }

}