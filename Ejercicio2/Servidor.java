
import java.io.*;
import java.net.*;



public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("Servidor multimensaje esperando...");
            
            try (Socket cliente = server.accept();
                 PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()))) {
                
                String mensaje;
                // Bucle para leer mensajes continuamente
                while ((mensaje = in.readLine()) != null) {
                    if (mensaje.equalsIgnoreCase("exit")) {
                        System.out.println("Cliente solicitó cerrar la conexión.");
                        break; 
                    }
                    System.out.println("Cliente dice: " + mensaje);
                    out.println("Echo: " + mensaje);
                }
            }
            System.out.println("Conexión cerrada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
