import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            System.out.println("1. Conectado al servidor.");
            out.println("¡Hola desde el cliente!");
            
            String respuesta = in.readLine();
            System.out.println("2. Respuesta del servidor: " + respuesta);
            
        } catch (IOException e) {
            System.out.println("Error: No se pudo conectar. ¿Está el servidor encendido?");
        }
    }
}