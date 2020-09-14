/**
 * Aufgabe 3: Oberklasse der Speisen
 * Unterklassen werden von dieser Klasse abgeleitet
 */
public class Speise {
    String name;
    float preis;

    public Speise(String name, float preis) {
        this.name = name;
        this.preis = preis;
    }

    /**
     * bestellen methode
     * Gibt die Bestellung als Text in der Konsole aus
     */
    public void bestellen() {
        System.out.println("Bestllung von: " + this.name);
    }
}