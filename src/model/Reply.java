package model;

public class Reply extends  WebRecord {
    public String username = "";

    public Reply(String srcIp, String srcPort, String destIp, String destPort, TimeStamp timeStamp, String username) {
        super(srcIp, srcPort, destIp, destPort, timeStamp);
        this.username = username;
        setType("reply");
    }
}
