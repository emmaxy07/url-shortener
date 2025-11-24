import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

public class Shortener{
    
    public static void main(String[] args) throws URISyntaxException {
        Scanner scanner = new Scanner(System.in);
        String userUrl = "";
        String trimmedUrl;
        int nextId = 0;
        String shortUrl = "";
        String userInput = "";
        ConcurrentHashMap <String, UrlRecord> storeRecord = new ConcurrentHashMap<String, UrlRecord>();

        

        while (true) {    
            System.out.print("Input your url: ");
            userUrl = scanner.nextLine();
            if(userUrl.equals("exit")){
                break;
            } else if (userUrl != null && !userUrl.isEmpty()){
                trimmedUrl = userUrl.trim();

                try {
                    new URI(trimmedUrl).parseServerAuthority();
                        System.out.println("valid url");
                    } catch (URISyntaxException e) {
                        System.out.println("invalid url");
                    }
                
                nextId++;
                StringBuilder sb = generateId(nextId);

                URI uri = new URI(trimmedUrl);
                String domain = uri.getHost();
                String domainBuild = domain.startsWith("www.") ? domain.substring(4) : domain;
                shortUrl = "https://bit.ly/" + domainBuild + "/" + sb.toString();
                UrlRecord urlRecord = new UrlRecord(shortUrl);
                if(storeRecord.isEmpty()){
                    storeRecord.put(sb.toString(), urlRecord);
                } else {
                       if(storeRecord.containsKey(sb.toString())){
                        StringBuilder newSb = generateId(nextId);
                        shortUrl = domainBuild + "/" + newSb.toString();
                        UrlRecord urlRecordVal = new UrlRecord(shortUrl);
                        storeRecord.put(newSb.toString(), urlRecordVal);
                       } else {
                        storeRecord.put(sb.toString(), urlRecord);
                       }
                }

                sb.setLength(0);

                try {
                    // HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
                    // httpServer.createContext("/", new MyHandler());

                    // httpServer.setExecutor(null);
                    // httpServer.start();
                    // System.out.println("Server is running on port 8000");
                    // URL url = uri.toURL(shortUrl);
                    // URL url = uri.toURL();
                    // HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
                    HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
                    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(shortUrl)).build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                } catch(IOException e){
                    System.out.println("Error starting the server: " + e.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while(true){
                    System.out.print("Do you want to get the short url? ");
                    userInput = scanner.nextLine();
                    if(userInput.equals("Yes")){
                        System.out.print("Here is the short url: " + shortUrl + "\n");
                    }
                }

            }
            
        }
            scanner.close();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static StringBuilder generateId(int nextId){
        int value;
        StringBuilder sb = new StringBuilder();
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                value = nextId;
                while(value > 0){
                    int remainder = (int) (value % 62);
                    sb.insert(0, chars.charAt(remainder));
                    value /= 62;
                }
        return sb;
    }
}