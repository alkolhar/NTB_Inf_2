/**
 * Ticketautomat Applikationslogik
 * 
 * @author akoller
 * @version 10.03.2020
 */
public class Ticketautomat {

    private double alreadyPaid;
    private double ticketPrice;

    public Ticketautomat() {
        ticketPrice = 5.00;
    }

    /**
     * @return the ticketPrice
     */
    public double getTicketPrice() {
        return ticketPrice;
    }

    /**
     * @return the alreadyPaid
     */
    public double getAlreadyPaid() {
        return alreadyPaid;
    }

    /**
     * pay methode, fügt der Variable 'alreadyPaid' den bezahlten Betrag hinzu
     * 
     * @param betrag zu addierender betrag
     */
    public void pay(double betrag) {
        if (betrag > 0) {
            alreadyPaid = alreadyPaid + betrag;
        }

    }

    /**
     * buy methode, kauft ein Ticket, alreadyPaid wird um ticketPrice erniedrigt
     * 
     * @return boolean true wenn bezahlt, false wenn zu wenig guthaben vorhanden ist
     */
    public boolean buy() {
        if (alreadyPaid >= ticketPrice) {
            alreadyPaid -= ticketPrice;
            return true;
        } else {
            return false; // zu wenig bezahlt
        }
    }

    /**
     * cashBack methode, der bereits bezahlte Betrag 'alreadyPaid' wird ausbezahlt
     * 'alreadyPaid' wird auf 0 gesetzt
     *
     */
    public void cashBack() {
        System.out.println(alreadyPaid + " ausbezahlt.");
        alreadyPaid = 0;
    }
}