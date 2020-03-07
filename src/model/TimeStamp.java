package model;

public class TimeStamp {
    public long getTime() {
        return time;
    }

    long time = 0;
    public TimeStamp(long time) {
        this.time = time;
    }
}
