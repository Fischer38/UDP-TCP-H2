import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        try {
            System.out.println("Forbinder til server...");
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Forbundet til server!");
            
            // Opret input og output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // Start en tråd til at håndtere indkommende beskeder
            Thread modtagTråd = new Thread(() -> {
                try {
                    String besked;
                    while ((besked = reader.readLine()) != null) {
                        System.out.println(besked);
                    }
                } catch (IOException e) {
                    System.out.println("Mistet forbindelse til serveren");
                }
            });
            modtagTråd.start();

            // Hovedløkke til at sende beskeder
            System.out.println("Skriv dine beskeder (skriv 'exit' for at afslutte):");
            try {
                while (true) {
                    String besked = scanner.nextLine();
                    writer.println(besked);
                    
                    if (besked.equalsIgnoreCase("exit")) {
                        break;
                    }
                }
            } finally {
                // Oprydning
                System.out.println("Lukker forbindelsen...");
                writer.close();
                reader.close();
                socket.close();
                scanner.close();
            }
            
        } catch (ConnectException e) {
            System.out.println("Kunne ikke forbinde til serveren. Sørg for at serveren kører først.");
        } catch (Exception e) {
            System.out.println("Der opstod en fejl: " + e.getMessage());
            e.printStackTrace();
        }
    }
}