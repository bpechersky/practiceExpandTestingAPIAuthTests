import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class OAuthRedirectListener {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new Handler(server));
        server.setExecutor(Executors.newSingleThreadExecutor());
        System.out.println("Listening on http://localhost:8080/");
        server.start();
    }

    static class Handler implements HttpHandler {
        private final HttpServer server;

        public Handler(HttpServer server) {
            this.server = server;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String response;

            if (query != null && query.contains("code=")) {
                String code = query.split("code=")[1].split("&")[0];
                response = "Authorization code received: " + code;
                System.out.println("✅ Authorization code: " + code);

                // Automatically stop the server after receiving the code
                new Thread(() -> {
                    try {
                        Thread.sleep(1000); // slight delay to let the browser show the message
                        server.stop(0);
                        System.out.println("🛑 Server stopped.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                response = "No authorization code found.";
            }

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
