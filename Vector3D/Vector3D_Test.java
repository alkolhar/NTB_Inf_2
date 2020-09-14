public class Vector3D_Test {

    public static void main(String[] args) {
        Vector3D_Test a = new Vector3D_Test();
        a.test();
    }

    public void test() {

        Vector3D v;
        v = new Vector3D(); // initialisiere (0.0, 0.0, 0.0)

        String s = v.alsString();
        System.out.println(s);

        // ---------------------------------------------------------------

        Vector3D v1 = new Vector3D(4.0, 3.0, 5.0);
        System.out.println(v1.alsString());
        // Erwartet: (4.0, 3.0, 5.0)

        // ---------------------------------------------------------------

        Vector3D v2 = new Vector3D(-1.0, 1.2, 0.5);
        Vector3D v3 = v1.addiere(v2);
        System.out.println(v3.alsString()); // Erwartet: (3.0, 4.2, 5.5)

        // ---------------------------------------------------------------

        double b = v3.betrag();
        System.out.println("Der Betrag von " + v3.alsString() + " ist " + b);

        // ---------------------------------------------------------------

        // https://de.wikipedia.org/wiki/Skalarprodukt
        Vector3D v4 = new Vector3D(1.0, 0.0, 3.0);
        Vector3D v5 = new Vector3D(1.0, 1.0, 2.0);
        double sp = v4.skalarProdukt(v5);
        System.out.println("Das Skalarprodukt von " + v4.alsString() + " und " + v5.alsString() + " ist " + sp); // 7.0
 
        double phi = v4.winkel(v5);
        System.out.println("Der eingeschlossene Winkel zwischen " + v4.alsString() + " und " + v5.alsString() + " ist " + phi);

    }
}




