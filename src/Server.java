import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Server {
    private List<User> usersList;

    public Server(List<User> usersList) {
        this.usersList = usersList;
    }

    private class RequestHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String requestMethod = httpExchange.getRequestMethod();
            if (requestMethod.equals("GET")) {

//                InputStream inStream = httpExchange.getRequestBody();
//                Scanner scanner = new Scanner(inStream);
//                String data = scanner.nextLine();
//                System.out.println(data);
//
//                inStream.close();
//                scanner.close();

                // Sending a successful response back
                String response = Server.this.usersList.toString();
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream outStream = httpExchange.getResponseBody();
                outStream.write(response.getBytes());
                outStream.close();
            } else {
                String response = "Method not allowed";
                httpExchange.sendResponseHeaders(405, response.length());
                OutputStream outStream = httpExchange.getResponseBody();
                outStream.write(response.getBytes());
                outStream.close();
            }


        }
    }

    private void runServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        HttpContext context = server.createContext("/users"); // describes all the HTTP-specific information about a single HTTP request
        context.setHandler(new RequestHandler()); //attaches handler to contect -> requests will be handled
        server.start();
        System.out.println("Server started on port 8000");
    }

    public static void main(String[] args){
        ArrayList<User> usersList = new ArrayList<>();
        usersList.addAll(Arrays.asList(new User("Alex"), new User("Anna"), new User("David")));
        try {
            Server server = new Server(usersList);
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
