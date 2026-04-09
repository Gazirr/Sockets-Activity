import java.io.*;
import java.net.*;

public class ServidorTicketsConcurrente {
    private static int contadorTickets = 0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("--- SERVIDOR MULTIHILO ACTIVO ---");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[+] Cliente conectado.");

                // Creamos el hilo para que maneje a este cliente
                new Thread(() -> {
                    // Usamos try-with-resources DENTRO del hilo para que el socket se cierre al acabar
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        
                        String mensaje;
                        // Bucle para que el hilo siga vivo mientras el cliente hable
                        while ((mensaje = in.readLine()) != null) {
                            if (mensaje.equalsIgnoreCase("exit")) break;
                            
                            contadorTickets++;
                            System.out.println("Hilo atendiendo: " + mensaje);
                            out.println("Ticket #" + contadorTickets + " generado para: " + mensaje);
                        }
                        
                    } catch (IOException e) {
                        System.err.println("Error en hilo: " + e.getMessage());
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}