import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Server {
    private class RequestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            InputStream inStream = httpExchange.getRequestBody();
            Scanner scanner = new Scanner(inStream);
            String data = scanner.nextLine();
            System.out.println(data);

            // Properly closing the input stream and scanner
            inStream.close();
            scanner.close();

            // Sending a response back
            String response = "Received";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream outStream = httpExchange.getResponseBody();
            outStream.write(response.getBytes());
            outStream.close();
        }
    }

    private void runServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        HttpContext context = server.createContext("/main"); // describes all the HTTP-specific information about a single HTTP request
        context.setHandler(new RequestHandler());
        server.start();
        System.out.println("Server started on port 8000");
    }

    public static void main(String[] args){
        try {
            Server server = new Server();
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
