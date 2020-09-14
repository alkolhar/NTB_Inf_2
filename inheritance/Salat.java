/**
 * Aufgabe 3
 * Unterklasse der Klasse Speise
 */
public class Salat extends Speise {

    String dressing;

    public Salat(String name, float preis, String dressing) {
        super(name, preis);
        this.dressing = dressing;
    }
}