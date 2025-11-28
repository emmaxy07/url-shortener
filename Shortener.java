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
        String longUrl;
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
                longUrl = userUrl.trim();

                try {
                    new URI(longUrl).parseServerAuthority();
                        System.out.println("valid url");
                    } catch (URISyntaxException e) {
                        System.out.println("invalid url");
                    }
                
                nextId++;
                StringBuilder sb = generateId(nextId);

                URI uri = new URI(longUrl);
                System.out.println(uri);
                String domain = uri.getHost();
                String domainBuild = domain.startsWith("www.") ? domain.substring(4) : domain;
                shortUrl = "https://bit.ly/" + domainBuild + "/" + sb.toString();
                UrlRecord urlRecord = new UrlRecord(longUrl);
                if(storeRecord.isEmpty()){
                    storeRecord.put(sb.toString(), urlRecord);
                } else {
                       if(storeRecord.containsKey(sb.toString())){
                        StringBuilder newSb = generateId(nextId);
                        shortUrl = domainBuild + "/" + newSb.toString();
                        UrlRecord urlRecordVal = new UrlRecord(longUrl);
                        storeRecord.put(newSb.toString(), urlRecordVal);
                       } else {
                        storeRecord.put(sb.toString(), urlRecord);
                       }
                }

                sb.setLength(0);

                try {
                    HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
                    httpServer.createContext("/", new RedirectHandler(sb.toString()));

                    
                    httpServer.setExecutor(null); 
                    httpServer.start();
                    System.out.println("Server started at http://localhost:8080");
                } catch(IOException e){
                    System.out.println("Error starting the server: " + e.getMessage());
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

    static class RedirectHandler implements HttpHandler {
    
        private final String newPath;

        public RedirectHandler(String newPath){
            this.newPath = newPath;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Location", newPath);
            exchange.getRequestURI().getQuery();
            exchange.sendResponseHeaders(302, -1);
            exchange.close();
            throw new UnsupportedOperationException("Unimplemented method 'handle'");
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