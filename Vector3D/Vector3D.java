import java.util.Random;

public class Vector3D {
    
    private double[] varr;

    /**
     * Konstruktor für die Erstellung eines dreidimensionalen Vektors
     * @param xx x-Komponente
     * @param yy y-Komponente
     * @param zz z-Komponente
     */
    public Vector3D(double xx, double yy, double zz) {
        varr = new double[] {xx, yy, zz};
    }

    /**
     * Überladung des Konstruktors zur Erstellung eines mehrdimensionalen Vektors
     * @param size anzahl dimensionen des Vektors
     */
    public Vector3D(int size) {
        Random rd = new Random();
        varr = new double[size];

        for (int i = 0; i < varr.length; i++) {
            varr[i] = rd.nextDouble();
        }
    }

    /**
     * Überladung des Konstruktors zur Erstellung eines zufälligen dreidimensionalen Vektors
     */
    public Vector3D() {
        Random rd = new Random();
        varr = new double[3];

        for (int i = 0; i < varr.length; i++) {
            varr[i] = rd.nextDouble();
        }
    }

    /**
     * Verkettet die Komponenten als String und gibt sie als String zurück
     * @return die Komponenten des Vektors als String
     */
    public String alsString() {
        String vTxt = "(";

        for (int i = 0; i < varr.length; i++) {
            if (i != 0) {
                vTxt = vTxt + "," + varr[i];
            } else {
                vTxt = vTxt + varr[i];
            }
        }

        return vTxt + ")";
    }

    /**
     * addiert zu dem aktuellen vektor ein neuer vektor 'a'
     * @param a vektor der zum aktuellen vektor addiert wird
     * @return ein neuer vektor aus der addition der beiden vektoren b
     */
    public Vector3D addiere(Vector3D a) {
        Vector3D b = new Vector3D(a.varr.length);

        for (int i = 0; i < varr.length; i++) {
            b.varr[i] = a.varr[i] + this.varr[i];
        }

        return b;
    }

    /**
     * berechnet den betrag (laenge) des aktuellen vektors
     * Satz von Pythagoras
     * @return den betrag der länge als double
     */
    public double betrag() {
        double bet = 0;

        for (int i = 0; i < varr.length; i++) {
            bet = bet + (Math.pow(varr[i], 2));
        }
  
        return Math.sqrt(bet);
    }

    /**
     * berechnet das Skalarprodukts zweier Vektoren
     * der aktuelle Vektor wird elementweise mit dem vektor a multipliziert
     * @param a mit diesem vektor wird multipliziert
     * @return das Skalarprodukt der beiden Vektoren als double
     */
    public double skalarProdukt(Vector3D a) {
        double skalP = 0;

        for (int i = 0; i < varr.length; i++) {
            skalP = skalP + (a.varr[i] * this.varr[i]);
        }

        return skalP;
    }

    /**
     * berechnet den winkel zwischen dem aktuellen vektor und vektor a
     * @param a der zweite Vektor
     * @return den winkel in ° zwischen dem aktuellen vektor und dem vektor a, als double
     */
    public double winkel(Vector3D a) {
        double zaehler = this.skalarProdukt(a);
        double nenner = Math.pow(this.betrag(), 2);

        return Math.toDegrees(Math.acos(zaehler / nenner));
    }
}