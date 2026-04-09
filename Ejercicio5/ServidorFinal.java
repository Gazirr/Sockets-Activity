import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServidorFinal {
    public static void main(String[] args) throws IOException {
        // Usamos el puerto 8085 para no chocar con los otros
        ServerSocket server = new ServerSocket(8085);
        System.out.println(">>> SIMULADOR APACHE INICIADO EN http://localhost:8085 <<<");

        while (true) {
            try (Socket cliente = server.accept();
                 OutputStream out = cliente.getOutputStream();
                 BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()))) {

                // 1. Leer la petición del navegador (ej: GET / HTTP/1.1)
                String peticion = in.readLine();
                if (peticion != null) {
                    System.out.println("Cliente solicita: " + peticion);
                    
                    // 2. Cargar el archivo index.html
                    File file = new File("index.html");
                    if (!file.exists()) {
                        String error = "HTTP/1.1 404 Not Found\r\n\r\nArchivo no encontrado";
                        out.write(error.getBytes());
                    } else {
                        byte[] contenido = Files.readAllBytes(file.toPath());

                        // 3. ENVIAR CABECERAS (Protocolo HTTP)
                        String cabeceras = "HTTP/1.1 200 OK\r\n" +
                                           "Content-Type: text/html; charset=utf-8\r\n" +
                                           "Content-Length: " + contenido.length + "\r\n" +
                                           "Connection: close\r\n" +
                                           "\r\n";
                        
                        out.write(cabeceras.getBytes()); // Enviamos protocolo
                        out.write(contenido);           // Enviamos la web
                    }
                    out.flush();
                }
            } catch (Exception e) {
                System.out.println("Error atendiendo cliente: " + e.getMessage());
            }
        }
    }
}