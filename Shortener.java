import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Shortener{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userUrl = "";
        

        while (true) {    
            System.out.print("Input your url: ");
            userUrl = scanner.nextLine();
            if(userUrl.equals("exit")){
                break;
            } else if (userUrl != null && !userUrl.isEmpty()){
                String trimmedUrl = userUrl.trim();
    
                try {
                    URI url = new URI(trimmedUrl);
                    if(url.getScheme().equals("http") || url.getScheme().equals("https")){
                        if(url.getHost() != null && !url.getHost().isEmpty()){
                            System.out.println("Link is valid");
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            
        }
            scanner.close();
    }
}