import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Shortener{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userUrl = "";
        String trimmedUrl;
        int nextId = 1;
        StringBuilder sb = new StringBuilder(1);
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int remainder = 0;

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
                
                while(nextId > 0){
                    sb.insert(0, chars.charAt((int) (nextId % 62)));
                    nextId /= 62;
                    nextId++;
                }
                System.out.println(sb.toString());
            }
            
        }
        System.out.println("here" + " " + sb);
            // sb = "";
            scanner.close();
    }

    static void generateId(String trimmedUrl){
        // int nextId = 0;
        
        // nextId = nextId + 1;
        // if(nextId)
        
    }
}