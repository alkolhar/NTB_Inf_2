import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class UebungenArrays {
    int[][] test = {
        {9, 3, 45, 243, 53},
        {13, 42, 542, 24, 28}
    };
	public static int[] createRandomIntArray(int length) {
		Random r = new Random();
        IntStream is = r.ints(length);
		return is.toArray();
    }
    public static int[] copyArray(int[] a) {
        return a.clone();
    }
    public static boolean equal(int[] a, int[] b) {
        return Arrays.equals(a, b);
    }
    public static void sortArray(int[] a) {
        Arrays.sort(a);
    }



	/**
	 * Schreibt jeden Wert im Array a in die Konsole
	 * @param a das auszugebende array
	 */
	public static void printArray(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println( a[i] ); 
		}
    }
    /**
	 * Schreibt jeden Wert im Array a in die Konsole
	 * @param a das auszugebende array
	 */
	public static void printArray(float[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println( a[i] ); 
		}
    }

    /**
	 * Schreibt jeden Wert im Array a von hinten nach vorne in die Konsole
	 * @param a das auszugebende array
	 */
	public static void printReverse(int[] a) {
		for (int i = a.length - 1; i >= 0; i--) {
			System.out.println( a[i] ); 
        }
    }


    
    
    /**
     * checkSorting Methode prüft ob ein Integer Array aArray aufsteigend sortiert ist.
     * 
     * @param aArray das zu prüfende Integer Array
     * @return true wenn aufsteigend sortiert, ansonsten false
     */
    public static boolean checkSorting(int[] aArray) {
        int prevValue = aArray[0];
        for (int i = 1; i < aArray.length; i++) {
            if (aArray[i] < prevValue) {
                return false;
            }
        }
        return true;
    }


    // ------------------------------------------------------------------------
    // ------------------ Uebung mehrdimensionale Arrays ----------------------

    /**
    * Berechnet die Summe aller Elemente in a.
    * @param a Ein 2-D int array
    * @return die Summe
    */
    public static int sum(int[][] a) {
        int summe = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                summe += a[i][j];
            }
        }

        return summe;
    }

    /**
    * Berechnet den Mittelwert pro Spalte von a.
    * @param a Ein (rechteckiges) 2-D int array
    * @return Mittelwert jeder Spalte von a
    */
    public static float[] avgPerCol(int[][] a) {
        float summe = 0;
        float[] res = new float[a.length];
        
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                summe += a[i][j];
            }
            res[i] = summe / a[i].length;
        }
        
        return res;
    }


	public static void main(String[] args) {
        int[] zuf = createRandomIntArray(7);

        printArray(zuf);
        printReverse(zuf);

        System.out.println("Beginn mit: " + String.valueOf(checkSorting(zuf)));
        Arrays.sort(zuf);
        System.out.println("Endet mit: " + String.valueOf(checkSorting(zuf)));

    // ------------------------------------------------------------------------
    // ------------------ Uebung mehrdimensionale Arrays ----------------------


        System.out.println(sum(test));

        printArray(avgPerCol(test));
	}

}