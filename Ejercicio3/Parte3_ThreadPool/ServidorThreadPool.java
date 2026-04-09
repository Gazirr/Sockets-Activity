import java.io.*;
import java.net.*;
import java.util.concurrent.*; // Importante para el Pool

public class ServidorThreadPool {
    public static void main(String[] args) {
        int puerto = 8080;
        
        // CREAMOS EL POOL: Solo permitimos 3 hilos a la vez. 
        // Si llega un 4º cliente, tendrá que esperar a que uno quede libre.
        ExecutorService pool = Executors.newFixedThreadPool(3);

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("--- SERVIDOR CON THREAD POOL (Máx 3 hilos) ---");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[+] Cliente en espera de hilo...");

                // En lugar de "new Thread", le pasamos la tarea al POOL
                pool.execute(() -> {
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        
                        String mensaje = in.readLine();
                        // Simulamos que el trabajo tarda 5 segundos para ver el límite del pool
                        Thread.sleep(5000); 
                        out.println("Atendido por el Pool. Mensaje: " + mensaje);
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown(); // Cerramos el pool al terminar
        }
    }
}