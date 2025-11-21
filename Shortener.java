import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Shortener{
    
    public static void main(String[] args) throws URISyntaxException {
        Scanner scanner = new Scanner(System.in);
        String userUrl = "";
        String trimmedUrl;
        int nextId = 0;
        String longUrl = "";
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
                longUrl = domainBuild + "/" + sb.toString();
                UrlRecord urlRecord = new UrlRecord(longUrl);
                if(storeRecord.isEmpty()){
                    storeRecord.put(sb.toString(), urlRecord);
                } else {
                       if(storeRecord.containsKey(sb.toString())){
                        StringBuilder newSb = generateId(nextId);
                        longUrl = domainBuild + "/" + newSb.toString();
                        UrlRecord urlRecordVal = new UrlRecord(longUrl);
                        storeRecord.put(newSb.toString(), urlRecordVal);
                       } else {
                        storeRecord.put(sb.toString(), urlRecord);
                       }
                }

                sb.setLength(0);

            }
            
        }
            scanner.close();
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