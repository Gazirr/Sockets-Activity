import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger; // Para tickets seguros

public class ServidorTicketsFinal {
    // AtomicInteger garantiza que el incremento sea único, aunque haya mil hilos a la vez
    private static AtomicInteger contadorTickets = new AtomicInteger(0);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("--- SERVIDOR PROFESIONAL (HILOS + ATOMIC) ---");

            while (true) {
                Socket socket = serverSocket.accept();
                
                new Thread(() -> {
                    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        
                        String mensaje;
                        while ((mensaje = in.readLine()) != null) {
                            if (mensaje.equalsIgnoreCase("exit")) break;
                            
                            // .incrementAndGet() es la forma segura de hacer contador++
                            int ticketActual = contadorTickets.incrementAndGet();
                            
                            out.println("Ticket #" + ticketActual + " entregado de forma segura.");
                            System.out.println("Hilo atendiendo cliente. Ticket actual: " + ticketActual);
                        }
                    } catch (IOException e) {
                        System.err.println("Cliente desconectado.");
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}