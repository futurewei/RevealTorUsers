package model;

import java.util.Objects;

public abstract class WebRecord {
    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public String getDestIp() {
        return destIp;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    public String getDestPort() {
        return destPort;
    }

    public void setDestPort(String destPort) {
        this.destPort = destPort;
    }

    private String srcIp;
    private String srcPort;
    private String destIp;
    private String destPort;

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    private TimeStamp timeStamp;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type = "";
    public WebRecord(String srcIp, String srcPort, String destIp, String destPort, TimeStamp timeStamp) {
        this.srcIp = srcIp;
        this.srcPort = srcPort;
        this.destIp = destIp;
        this.destPort = destPort;
        this.timeStamp = timeStamp;
    }

    public int getHash() {
        return Objects.hash(srcIp, srcPort, destIp, destPort);
    }
}
