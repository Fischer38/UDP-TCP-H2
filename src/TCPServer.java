import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {
    private static Set<PrintWriter> klientOutputs = new HashSet<>();
    
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Chat server kører...");
            
            while (true) {
                Socket klientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(klientSocket.getOutputStream(), true);
                klientOutputs.add(writer);
                
                Thread klientThread = new Thread(new KlientHandler(klientSocket, writer));
                klientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static class KlientHandler implements Runnable {
        private Socket socket;
        private PrintWriter thisClient;
        private BufferedReader reader;
        
        public KlientHandler(Socket socket, PrintWriter writer) {
            this.socket = socket;
            this.thisClient = writer;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public void run() {
            try {
                String besked;
                while ((besked = reader.readLine()) != null) {
                    System.out.println("Modtaget: " + besked);
                    for (PrintWriter klient : klientOutputs) {
                        if (klient != thisClient) {
                            klient.println("Klient: " + besked);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                klientOutputs.remove(thisClient);
            }
        }
    }
}