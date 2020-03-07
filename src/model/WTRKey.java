package model;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class WTRKey {

    private String name;
    private int hashBytes;

    public WTRKey() {}

    public WTRKey(String name, int hashBytes) {
        this.name = name;
        this.hashBytes = hashBytes;
    }

    public String getName() {
        return name;
    }

    public int getHashBytes() {
        return hashBytes;
    }
}
