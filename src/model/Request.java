package model;

public class Request extends WebRecord {
    public String cookie;
    public Request(String srcIp, String srcPort, String destIp, String destPort, TimeStamp timeStamp, String cookie) {
        super(srcIp, srcPort, destIp, destPort, timeStamp);
        this.cookie = cookie;
        setType("request");
    }
}
