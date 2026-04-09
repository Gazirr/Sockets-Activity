import java.io.*;
import java.net.*;

public class ServidorLento {
    public static void main(String[] args) {
        int contadorTickets = 0;
        int puerto = 8080;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("--- SERVIDOR DE TICKETS (ITERATIVO) ---");
            System.out.println("Escuchando en el puerto " + puerto);
            System.out.println("Este servidor solo atiende a uno por uno. Si alguien se cuelga, bloquea al resto.");

            while (true) {
                // El servidor se queda aquí parado esperando un cliente
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    System.out.println("\n[!] Cliente conectado desde: " + socket.getInetAddress());
                    
                    // Aquí el servidor se bloquea esperando que el cliente escriba algo
                    String peticion = in.readLine(); 
                    
                    if (peticion != null && peticion.toLowerCase().contains("ticket")) {
                        contadorTickets++;
                        // Simulamos que el servidor tarda un poco en procesar
                        Thread.sleep(2000); 
                        out.println("Tu número de ticket es: " + contadorTickets);
                        System.out.println("[OK] Ticket " + contadorTickets + " entregado.");
                    } else {
                        out.println("Error: Debes pedir un 'ticket'.");
                    }
                    
                    System.out.println("[?] Cliente finalizado. Volviendo a esperar...");
                    // Al salir del bloque try, el socket se cierra y el servidor vuelve al accept()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}