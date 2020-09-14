import java.util.Arrays;

/**
 * Klasse zur Verarbeitung von Bildern.
 * 
 * @author Carlo Bach
 * @version 16.2.2016
 */
public class Bild {
    
    private String titel;
    private int[][] bild;
       
    /**
     * Erzeuge ein neues leeres Bild mit dem Titel "Kein Bild".
     */
    public Bild() {
        this("Kein Bild", null);
    }
    
    /**
     * Erzeuge ein neues Bild mit dem gegebenen Titel und den Bilddaten.
     * 
     * @param titel die Titel des Bildes
     * @param bild die Bilddaten
     */
    public Bild(String titel, int[][] bild) {
        this.titel = titel;
        this.bild = bild;
    }

    /**
     * Der Titel des Bildes.
     * 
     * @return der Titel des Bildes
     */
    public String gibTitel() {
        return titel + " (" + gibBreite() + " x " + gibHoehe() + ")";
    }
    
    /**
     * Die Breite des Bildes. Falls kein Bild gesetzt worden ist, gibt die Methode 0 zur�ck.
     * 
     * @return die Bildbreite, oder 0, falls kein Bild gesetzt worden ist.
     */
    public int gibBreite() {
        if (bild != null){
            return bild.length;
        } else {
            return 0;
        }
    }
    
    /**
     * Die H�he des Bildes. Falls kein Bild gesetzt worden ist, gibt die Methode 0 zur�ck.
     * 
     * @return die Bildh�he, oder 0, falls kein Bild gesetzt worden ist.
     */
    public int gibHoehe() {
        if (bild != null && bild[0] != null){
            return bild[0].length;
        } else {
            return 0;
        }
    }

    /**
     * Die Intensität an der gegebenen Spalte und Zeile. Falls die Angaben bezüglich Zeile oder Spalte ungültig sind, wird 0 zurückgegeben. 
     * 
     * @param spalte die Spalte
     * @param zeile die Zeile
     * @return der Intensitätswert zwischen 0 (schwarz) und 255 (weiss), oder 0 bei ungültigen Zeilen-/Spaltenwerten 
     */
    public int gibIntensitaetswert(int spalte, int zeile) {
        if (0 <= spalte && spalte < gibBreite() && 0 <= zeile && zeile < gibHoehe()) {
            return bild[spalte][zeile];
        } else {
            //Der Rueckgabewert 0 ermoeglicht eine einfache Randbehandlung bei linearen Filtern.
            return 0;
        }
    }

    /**
     * Die Bilddaten des Bildes.
     * 
     * @return die Bilddaten oder null, falls kein Bild gesetzt wurde.
     */
    public int[][] gibBilddaten() {
        return bild;
    }
    
    /**
     * Bild kopieren
     *
     * @return eine Kopie des Bildes. Als Titel wird an den Originalnamen die Endung " - Kopie" angehängt
     */
    public Bild erstelleKopie() {
        int[][] b = new int[gibBreite()][gibHoehe()];
        for (int spalte = 0; spalte < gibBreite(); spalte++) {
            for (int zeile = 0; zeile < gibHoehe(); zeile++) {
                b[spalte][zeile] = bild[spalte][zeile];
            }
        }
        return new Bild(gibTitel() + " - Kopie", b);
    }
    
    /**
     * Erstellt ein Histogramm des Intensitätwertes
     * Nur für 8 bit Quantisierung, sprich 256 verschiedene Grauwerte
     * 
     * @return das Histogramm als integer array
     */
    public int[] gibHistogramm() {
        int[] histo = new int[256];

        for (int u = 0; u < gibBreite(); u++) {
            for (int v = 0; v < gibHoehe(); v++) {
                histo[gibIntensitaetswert(u, v)]++;
            }
        }

        return histo;
    }

    /**
     * Invertiert den Grauton des Bildes
     * Dies wird mit '255 - aktueller Graustufenwert' berechnet
     */
    public void bild_invertieren() {
        for (int u = 0; u < bild.length; u++) {
            for (int v = 0; v < bild[u].length; v++) {
                bild[u][v] = 255 - bild[u][v];
            }
        }
    }

    /**
     * Aendert die Intensitaetswerte um einen bestimmten offset
     * 
     * @param offset der Offset, um den die Intensitaetswerte angehoben werden
     */
    public void bild_aufhellen(int offset) {
        for (int u = 0; u < bild.length; u++) {
            for (int v = 0; v < bild[u].length; v++) {
                if (offset > 0) {
                    bild[u][v] = Math.min(bild[u][v] + offset, 255); // stellt sicher das 255 nicht überschritten wird
                } else {
                    bild[u][v] = Math.max(bild[u][v] + offset, 0); // stellt sicher das 0 nicht unterschritten wird
                }
            }
        }
    }

    /**
     * Aendert die Intensitaetswerte um einen bestimmten faktor
     * 
     * @param faktor der Faktor, um den die Intensitaetswerte angehoben werden
     */
    public void bild_kontrastErhoehen(double faktor) {
        for (int u = 0; u < bild.length; u++) {
            for (int v = 0; v < bild[u].length; v++) {
                if (faktor > 1) {
                    bild[u][v] = (int) Math.min((double) bild[u][v] * faktor, 255); // stellt sicher das 255 nicht überschritten wird
                } else {
                    bild[u][v] = (int) Math.max((double) bild[u][v] * faktor, 0); // stellt sicher das 0 nicht unterschritten wird
                }
            }
        }
    }

    /**
     * Optimiert den Kontrast in einem Bild, verteilt den Intensitaetswert zwischen 0 und 255
     */
    public void bild_kontrastOptimieren() {
        int lowerIdx = 0;
        int upperIdx = 255;
        int[] histo = gibHistogramm();

        // find alow 'lowerIdx'
        for (int i = 0; i < histo.length; i++) {
            if (histo[i] != 0) {
                lowerIdx = i;
                break;
            }
        }

        // find ahigh 'upperIdx'
        for (int i = histo.length - 1; i >= 0; i--) {
            if (histo[i] != 0) {
                upperIdx = i;
                break;
            }
        }

        if (lowerIdx != 0 || upperIdx != 255) { // Ausführung unnötig wenn bereits gesamtes Spektrum benutzt wird
            for (int u = 0; u < bild.length; u++) {
                for (int v = 0; v < bild[u].length; v++) {
                    bild[u][v] = (int) (((double) bild[u][v] - lowerIdx) * (double) 255.0 / (upperIdx - lowerIdx)); // transformieren
                }
            }
        }
    }

    /**
     * Binarisiert den Grauwert
     * alles unterhalbt des Schwellwerts wird auf schwarz (0) gesetzt, überhalb auf weiss (255)
     * 
     * @param schwellwert der Schwellwert, der als Grenze genommen wird
     */
    public void bild_schwellwertAnwenden(int schwellwert) {
        for (int u = 0; u < bild.length; u++) {
            for (int v = 0; v < bild[u].length; v++) {
                if (bild[u][v] > schwellwert) {
                    bild[u][v] = 255;
                } else if (bild[u][v] < schwellwert) {
                    bild[u][v] = 0;
                }
            }
        }
    }

    /**
     * Der Mittelwertfilter berechnet die neue Intensität aus dem Mittelwert der direkt angrenzenden Pixel
     * 
     * @param eingangsbild das Bild, das gefiltert wird
     */
    public void bild_mittelwertfilter(Bild eingangsbild) {
        double sum;
        
        for (int u = 0; u < bild.length; u++) {
            for (int v = 0; v < bild[u].length; v++) {
                sum = 0;
                for (int i = u - 1; i <= u + 1; i++) {
                    for (int j = v - 1; j <= v + 1; j++) {
                        sum += eingangsbild.gibIntensitaetswert(i, j);
                    }
                }
                bild[u][v] = (int) (sum / 9.0);
            }
        }
    }

    /**
     * Sobelfilter zur Kantenerkennung
     * 
     * @param eingangsbild das Bild, das gefiltert wird
     * @param filter der Filterkern
     */
    public void bild_linearerfilter(Bild eingangsbild, double[][] filter) {
        double sum;
        int k = 0, l = 0;
        for (int u = 0; u < bild.length; u++) {
            for (int v = 0; v < bild[u].length; v++) {
                sum = 0; k = 0; l = 0;
                for (int i = u - 1; i <= u + 1; i++) {
                    for (int j = v - 1; j <= v + 1; j++) {
                        sum = sum + eingangsbild.gibIntensitaetswert(i, j) * filter[k][l];
                        l++;
                    }
                    l = 0; k++;
                }
                k = 0;
                bild[u][v] = Math.max(0, Math.min((int) sum / (filter.length * filter[0].length), 255));
            }
        }
    }

    
    /**
     * 3x3 Medianfilterung
     * 
     * @param eingangsbild das Bild, das gefiltert wird
     */
    public void bild_medianfilter(Bild eingangsbild) {
        int k = 0;
        int[] nachbar = new int[9];
        for (int u = 0; u < bild.length; u++) {
            for (int v = 0; v < bild[u].length; v++) {
                k = 0;
                for (int i = u - 1; i <= u + 1; i++) {
                    for (int j = v - 1; j <= v + 1; j++) {
                        nachbar[k] = eingangsbild.gibIntensitaetswert(i, j);
                        k++;
                    }
                }
                Arrays.sort(nachbar);
                bild[u][v] = nachbar[4];
            }
        }
    }

    /**
     * Aufgabe "Clip" vom 16.04.2020
     * 
     * @param u Ursprung des Auschnitts x-Achse
     * @param v Ursprung des Auschnitts y-Achse
     * @param b Breite des Auschnitts
     * @param h Hoehe des Auschnitts
     * @return ausschnitt als neues bild
     */
	public Bild clip(int u, int v, int b, int h) {
		int[][] bi = new int[b][h]; // neues array mit grösse des ausschnitts
		int k = 0; int l = 0; // zählervariablen
		
		for (int i = u; i < u+b; i++) {
			for (int j = v; j < v+h; j++) {
				bi[k][l] = bild[i][j];
				l++;
			}
			k++;
		}
		
		return new Bild("Clip", bi);
	}

    /**
     * Aufgabe: Template Matching
     * 
     * @param suchbild das Bild, das durchsucht wird
     * @param template das Bild, das gesucht wird
     */
    public void bild_templatematching(Bild suchbild, Bild template) {
        double sum;
 
        for (int r = 0; r < bild.length; r++) {
            for (int s = 0; s < bild[r].length; s++) {
                sum = 0;

                for (int i = 0; i < template.gibBreite(); i++) {
                    for (int j = 0; j < template.gibHoehe(); j++) {
                        double diff = Math.pow(suchbild.gibIntensitaetswert(r + i, s + j) - template.gibIntensitaetswert(i, j), 2);
                        sum += diff;
                    }
                }
                
                sum = Math.sqrt(sum);
                bild[r][s] = (int) Math.sqrt(sum);
            }
        }
    }
        
    
    /**
     * Aufgabe: Geometrische Transformation: Rotation. Beachten Sie, dass das Rotationszentrum zuerst in den Ursprung geschoben, dann rotiert und wieder zurueckgeschoben werden muss.
     * 
     * @param eingangsbild das Bild, das veraendert wird
     * @param rotationszentrumSpalte die u-Position des Rotationszentrums
     * @param rotationszentrumZeile die v-Position des Rotationszentrums
     * @param winkel der Rotationswinkel (Achtung in Radiant)
     */
    public void bild_rotation(Bild eingangsbild, int rotationszentrumSpalte, int rotationszentrumZeile, double winkel) {
    }

    /**
     * Aufgabe: Verzerren. Twirl-Transformation: Rotation um alpha, die mit dem Abstand vom Zentrum abnimmt 
     * 
     * @param eingangsbild das Bild, das veraendert wird
     * @param rmax der maximale Radius, bis wohin gedreht wird
     * @param alpha der Rotationswinkel (Achtung in Radiant)
     */
    public void bild_twirl(Bild eingangsbild, int rmax, double alpha) {
    }

}