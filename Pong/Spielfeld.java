import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Kuemmert sich um das eigentliche Spiel
 */
public class Spielfeld extends JPanel implements MouseMotionListener, ActionListener {
    private static final long serialVersionUID = 1L;
    private Timer timer;
    private Schlaeger schlaeger;
    private Ball[] ball = new Ball[5];
    private boolean bEigenSpiel;
    private int dimX = 500, dimY = 500;

    public Spielfeld() {
        super();
        setPreferredSize(new Dimension(dimX, dimY));
        setLayout(null);
        setBackground(Color.yellow);

        addSchlaeger();

        addMouseMotionListener(this);

        timer = new Timer(20, this);
        timer.setInitialDelay(1000);
    }

    /**
     * fuegt dem Spielfeld einen schlaeger hinzu.
     * Der Schlaeger wird am unteren Rand in der Mitte eingefuegt
     */
    private void addSchlaeger() {
        schlaeger = new Schlaeger(100);
        schlaeger.setLocation(dimX / 2 - (schlaeger.getWidth() / 2), dimY);
        add(schlaeger);
    }

    /**
     * fuegt dem Spielfeld einen Ball hinzu
     * Der Ball wird am oberen Rand in der Mitte eingefuegt
     */
    private Ball addBall() {
        Ball tempBall = new Ball();
        tempBall.setLocation(dimX / 2 - tempBall.getWidth() / 2, 1);
        add(tempBall);
        return tempBall;
    }

    public void mouseDragged(java.awt.event.MouseEvent e) {
        schlaeger.setLocation(e.getX() - schlaeger.getWidth() / 2, schlaeger.getLocation().y);
    }

    public void mouseMoved(java.awt.event.MouseEvent e) {
        schlaeger.setLocation(e.getX() - schlaeger.getWidth() / 2, schlaeger.getLocation().y);
    }

    /**
     * startet das Spiel.
     * Setzt einen refresh timer, der alle 20ms das Spielfeld aktualisiert.
     */
    public void spielStarten(int anzBall, boolean eigenSpiel) {
        
        // zuerst alle vorherigen baelle entfernen und neu zeichnen
        for (int i = 0; i < anzBall; i++) {
            if (ball[i] != null) { remove(ball[i]); }
            ball[i] = null;
        }
        repaint();

        // prüfen des spielmodus
        if (eigenSpiel) {
            bEigenSpiel = eigenSpiel;
            anzBall = 1;
            removeMouseMotionListener(this);
        } else {
            bEigenSpiel = eigenSpiel;
            addMouseMotionListener(this);
        }

        // gewuenschte anzahl baelle hinzufuegen
        for (int i = 0; i < anzBall; i++) {
            ball[i] = addBall();
        }

        timer.start();
    }

    /**
     * actionPerformed methode wird vom Timer alle 20ms ausgefuehrt
     */
    public void actionPerformed(ActionEvent e) {
        
        for (int i = 0; i < ball.length; i++) {
            if (ball[i] != null) {
                collision(ball[i]);
                ball[i].aktualisierePosition();
                if (bEigenSpiel) { schlaeger.setLocation(ball[i].getLocation().x - schlaeger.getWidth()/2, schlaeger.getLocation().y); }
            }
        }
        
    }

    /**
     * Kollisionsüberwachung
     * Ueberprueft alle vier Seiten, sowie den Schlaeger.
     */
    private void collision(Ball ball) {
        int actX = ball.getLocation().x;
        int actY = ball.getLocation().y;

        // check x position seiten
        if (actX <= 0 || actX >= this.getWidth() - ball.getWidth()) {
            ball.wechsleXRichtung();
        }
        // check y position oben
        if (actY <= 0) {
            ball.wechsleYRichtung();
        } else if (actY + ball.getHeight() >= schlaeger.getY() + schlaeger.getHeight()) {
            repaint();
            remove(ball);
        } else if (actY + ball.getHeight() >= schlaeger.getY()) { // kontrolliere Schlaeger
            if (actX + ball.getWidth() >= schlaeger.getX() && actX <= schlaeger.getX() + schlaeger.getWidth()) {
                ball.wechsleYRichtung();
            }
        }

    }

}