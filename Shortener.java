import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Shortener{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userUrl = "";

        System.out.print("Input your url: ");
        userUrl = scanner.nextLine();

        if(userUrl != null && !userUrl.isEmpty()){
            String trimmedUrl = userUrl.trim();

            try {
                URI url = new URI(trimmedUrl);
                System.out.println(url.getClass().getName());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }


        scanner.close();
    }
}