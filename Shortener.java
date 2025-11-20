import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Shortener{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userUrl = "";
        String trimmedUrl;
        StringBuilder sb = new StringBuilder();
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int nextId = 0;

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
                
                int value;
                nextId++;
                value = nextId;
                while(value > 0){
                    int remainder = (int) (value % 62);
                    sb.insert(0, chars.charAt(remainder));
                    value /= 62;
                }
                System.out.println(sb.toString());
                sb.setLength(0);
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