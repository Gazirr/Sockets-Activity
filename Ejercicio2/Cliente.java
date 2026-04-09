import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        // Conectamos al puerto 8080
        try (Socket socket = new Socket("localhost", 8080);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner teclado = new Scanner(System.in)) {
            
            System.out.println("--- CONECTADO AL CHAT ---");
            System.out.println("Escribe mensajes. Escribe 'exit' para salir.");
            
            String linea;
            while (true) {
                System.out.print("> ");
                linea = teclado.nextLine(); 
                
                out.println(linea); // Enviamos al servidor
                
                if (linea.equalsIgnoreCase("exit")) break; 
                
                String respuesta = in.readLine(); // Esperamos el "Echo"
                System.out.println("Servidor: " + respuesta);
            }
            
        } catch (IOException e) {
            System.err.println("Error: ¿Está el servidor encendido?");
        }
    }
}