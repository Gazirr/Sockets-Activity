import java.net.URI;
import java.net.http.*;
import java.util.concurrent.CompletableFuture;

public class ComparadorServidores {
    public static void main(String[] args) {
        // PASO 1: Probar con Apache (Puerto 80)
        probarServidor("http://localhost:80", "APACHE");

        // PASO 2: Después cambia a Nginx (Puerto 8081) y vuelve a ejecutar
        // probarServidor("http://localhost:8081", "NGINX");
    }

    public static void probarServidor(String url, String nombre) {
        HttpClient client = HttpClient.newHttpClient();
        System.out.println("Bombardeando a " + nombre + " en " + url + "...");
        
        long inicio = System.currentTimeMillis();

        // Lanzamos 50 peticiones simultáneas
        CompletableFuture<?>[] peticiones = new CompletableFuture[50];
        for (int i = 0; i < 50; i++) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            peticiones[i] = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        }

        CompletableFuture.allOf(peticiones).join();
        long fin = System.currentTimeMillis();
        
        System.out.println("Resultado " + nombre + ": " + (fin - inicio) + "ms para 50 peticiones.");
    }
}