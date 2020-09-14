import java.util.Arrays;
import java.util.Random;

public class arrayexercise {

    public static void main(String[] args) {

        int[] aNumbers = { -57, 24, 0, 126, -101, 7, -1 };
        float[] cNumbers = createRandomIntArray(9);

        if (checkSorting(cNumbers)) { System.out.println("Sortiert!"); } else { System.out.println("Unsortiert!"); }

        printArray(cNumbers);
        Arrays.sort(cNumbers);
        if (checkSorting(cNumbers)) { System.out.println("Sortiert!"); } else { System.out.println("Unsortiert!"); }
        printArray(cNumbers);

        System.out.println("Kleinster Wert: " + getLowValue(aNumbers));
        int[] lohi = getHighLowValue(aNumbers);
        System.out.println("Kleinster Wert: " + lohi[0] + " höchster Wert: " + lohi[1]);

        float[] dNumbers = createRandomIntArray(8);
        printArray(dNumbers);
        if (checkSorting(bubbleSort(dNumbers))) { System.out.println("Sortiert!"); } else { System.out.println("Unsortiert!"); }
        printArray(dNumbers);

    }

    /**
     * bubbleSort Methode soll Arrays mit dem bubbleSort Algorithmus sortieren
     * 
     * @param arr unsortiertes float Array
     * @return das sortierte float Array
     */
    public static float[] bubbleSort(float[] arr) {
        float buf;
        int n = arr.length - 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    buf = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = buf;
                }
            }
        }
        return arr;
    }

    /**
     * createRandomIntArray Methode erstellt ein float Array
     * 
     * @param arrSize gewünschte grösse des Arrays
     * @return das erzeugt float Array
     */
    public static float[] createRandomIntArray(int arrSize) {
        Random rd = new Random();
        float[] arr = new float[arrSize];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt();
        }
        return arr;
    }

    /**
     * printArray gibt den Arrayinhalt in der Konsole aus
     * 
     * @param arr das auszugebende float Array
     */
    public static void printArray(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println("");
    }

    /**
     * getLowValue Methode sucht den kleinsten Wert im Array
     * 
     * @param aArray Das zu durchsuchende Integer Array
     * @return Den kleinsten Wert im Array
     */
    public static int getLowValue(int[] aArray) {
        int lowValue = aArray[0];
        for (int i = 1; i < aArray.length; i++) {
            if (aArray[i] < lowValue) {
                lowValue = aArray[i];
            }
        }
        return lowValue;
    }

    /**
     * getHighLowValue Methode gibt den kleinsten und grössten Wert des Array in der Konsole aus
     * 
     * @param aArray Das zu durchsuchende Integer Array
     * @return Ein Integer Array mit dem kleinsten Wert [0] und grösstem Wert [1]
     */
    public static int[] getHighLowValue(int[] aArray) {
        int[] val = new int[2];

        val[0] = aArray[0];
        val[1] = aArray[0];

        for (int i = 0; i < aArray.length; i++) {
            if (aArray[i] < val[0]) {
                val[0] = aArray[i];
            } else if (aArray[i] > val[1]) {
                val[1] = aArray[i];
            }
        }
        return val;
    }

    /**
     * checkSorting Methode prüft ob ein Integer Array aufsteigend sortiert ist.
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

    /**
     * checkSorting Methode prüft ob ein float Array aufsteigend sortiert ist.
     * 
     * @param aArray das zu prüfende float Array
     * @return true wenn aufsteigend sortiert, ansonsten false
     */
    public static boolean checkSorting(float[] aArray) {
        float prevValue = aArray[0];
        for (int i = 1; i < aArray.length; i++) {
            if (aArray[i] < prevValue) {
                return false;
            }
        }
        return true;
    }
}