import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("1. Servidor iniciado en puerto 8080. Esperando...");
            
            try (Socket cliente = server.accept();
                 PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()))) {
                
                System.out.println("2. Cliente conectado desde: " + cliente.getInetAddress());
                
                String mensaje = in.readLine();
                System.out.println("3. El cliente dijo: " + mensaje);
                
                out.println("Servidor dice: Recibido '" + mensaje + "' correctamente.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}