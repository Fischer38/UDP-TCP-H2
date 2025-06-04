import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        try {
            InetAddress serverAddress = InetAddress.getByName("10.108.149.31");
            int serverPort = 9876;
            DatagramSocket clientSocket = new DatagramSocket(); // Ingen binding til serverens adresse!
            Scanner scanner = new Scanner(System.in);

            System.out.print("Skriv besked: ");
            String besked = scanner.nextLine();
            byte[] sendData = besked.getBytes();
            byte[] receiveData = new byte[1024];

            // Send besked til serveren
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);

            // Modtag serverens svar
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String svar = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Svar fra server: " + svar);

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}