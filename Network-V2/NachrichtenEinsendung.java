import java.util.ArrayList;

/**
 * Diese Klasse speichert Informationen über eine Einsendung für den Newsfeed eines sozialen Netzwerks.
 * Der Hauptteil der Einsendung besteht aus einer (möglicherweise mehrzeiligen) Textnachricht. 
 * Weitere Daten, wie Autor und Datum, werden ebenfalls gespeichert.
 * 
 * @author Michael Kölling und David J. Barnes
 * @version 0.2
 */
public class NachrichtenEinsendung extends Einsendung {
    private String nachricht;   // eine beliebig lange, mehrzeilige Nachricht

    /**
     * Konstruktor für Objekte der Klasse NachrichtenEinsendung.
     * 
     * @param autor  der Benutzername des Einsenders
     * @param text   der Text dieser Einsendung
     */
    public NachrichtenEinsendung(String autor, String text) {
        super(autor);
        nachricht = text;
    }

    /**
     * Liefere den Text der Einsendung.
     * 
     * @return  der Nachrichtentext der Einsendung
     */
    public String gibText() {
        return nachricht;
    }

    /**
     * Gibt den Benutzername der Einsendung zurück
     */
    public void kurzinfoAusgeben() {
        System.out.println("Nachrichteneinsendung von " + gibBenutzername());
    }
}
