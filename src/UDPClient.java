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

            do{
                System.out.print("Skriv besked: ");
                byte[] besked = scanner.nextLine().getBytes();
                byte[] receiveData = new byte[1024];

                if (besked.length == 0 || besked[0] == '\n' || besked[0] == '\r') {
                    break;
                }

                DatagramPacket sendPacket = new DatagramPacket(besked, besked.length, serverAddress, serverPort);
                clientSocket.send(sendPacket);

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                String svar = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Svar fra server: " + svar);
            }while (true);
            clientSocket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}