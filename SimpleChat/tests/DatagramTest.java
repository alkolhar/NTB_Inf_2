package tests;

import java.net.*;

public class DatagramTest {
    DatagramSocket socket;

    public DatagramTest() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("Problem beim Erzeugen des Sockets: "+e);
        }
    }

    public DatagramTest(int meinePortNummer) {
        try {
            socket = new DatagramSocket(meinePortNummer);
        } catch (SocketException e) {
            System.out.println("Problem beim Erzeugen des Sockets: "+e);
        }
    }

    public void sendeDatagramm(String destinationsHostName, int destinationsPortNummer) {
        String text = "Hoi Du.";
        byte[] inhalt = text.getBytes();

        InetSocketAddress destinationAddress = new InetSocketAddress("localhost", destinationsPortNummer);
        DatagramPacket packet = new DatagramPacket(inhalt, inhalt.length, destinationAddress);

        try {
            socket.send(packet);
        } catch (Exception e) {
            System.out.println("Datagramm konnte nicht gesendet werden!");
        }
    }

    public void empfangeDatagramm(){
        byte[] empfangsPuffer = new byte[2048];
        DatagramPacket receivePacket = null;
        receivePacket = new DatagramPacket(empfangsPuffer, empfangsPuffer.length);

        try {
            socket.receive(receivePacket);
            empfangsPuffer = receivePacket.getData();
            System.out.println("Empfangen: " + new String(empfangsPuffer));
        } catch (Exception e) {
            System.out.println("Datagramm konnte nicht empfangen werden!");
        }
    }

    public void beenden() {
        if (socket != null){
            socket.close();
        }
    }

}
