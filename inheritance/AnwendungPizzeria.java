/**
 *  Aufgabe 3
 *  Anwendungsklasse der Vererbungen 'Speise'
 */
public class AnwendungPizzeria {

    public static void main(String[] args) {
        // Aufgabe 2a
        Pizza piz = new Pizza("Funghi", 7.30f);
        Salat sal = new Salat("Cesar", 8.90f, "dressing");

        piz.bestellen();
        sal.bestellen();

        // Aufgabe 2b
        Speise[] s = new Speise[2];

        // Aufgabe 2c
        s[0] = piz;
        s[1] = sal;

        // Aufgabe 2d
        for (int i = 0; i < s.length; i++) {
            System.out.println("Klassenname: " + s[i].getClass().getName());
            s[i].bestellen();
        }

        /**
         * Aufgabe 2e
         *  -> Klassenname sind unterschiedlich, jeweils Ober- und Unterklasse
         *  -> Array wird mit Oberklasse deklariert
         *  -> Laufzeittyp?
         *
         * Aufgabe 2f
         *  -> Nein, Typ muss identisch, bzw. vererbt sein
         * 
         * Aufgabe 2g
         *  -> Geht nicht
         */

    }
}