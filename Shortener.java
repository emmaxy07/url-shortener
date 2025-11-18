import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Shortener{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userUrl = "";
        String trimmedUrl;
        int nextId = 0;
        // StringBuilder sb = new StringBuilder();
        String sb = "";
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
                
                nextId++;
                if(nextId > 0){
                    remainder = nextId % 62;
                    sb = String.valueOf(chars.charAt(remainder)) + sb;
                    nextId /= 62;
                }
            }
            
            System.out.println("here" + " " + sb);
                sb = "";
            }
            scanner.close();
    }

    static void generateId(String trimmedUrl){
        // int nextId = 0;
        
        // nextId = nextId + 1;
        // if(nextId)
        
    }
}