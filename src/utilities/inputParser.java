package utilities;

import model.Reply;
import model.Request;
import model.TimeStamp;
import model.WebRecord;

public class inputParser {
    public static WebRecord parseRecord(String[] input) {
        TimeStamp timeStamp = new TimeStamp(Long.parseLong(input[0]));
        String srcIp = input[1];
        String srcPort = input[2];
        String destIp = input[3];
        String destPort = input[4];
        String identifier = input[input.length - 1];
        if (identifier.startsWith("cookie")) {
            String cookie = identifier.split(":")[1];
            return new Request(srcIp, srcPort, destIp, destPort, timeStamp, cookie);
        } else if (identifier.startsWith("username")){
            String username = identifier.split(":")[1];
            return new Reply(srcIp, srcPort, destIp, destPort, timeStamp, username);
        }
        return null;
    }
}
