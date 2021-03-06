package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class QueryFocusedDataSet implements Serializable {
    private final String name;
    private final String hashBytes;

    private final Set<RequestAndReplySession> matches;

    public QueryFocusedDataSet(String name, String hashBytes, Set<RequestAndReplySession> matches){
        this.name = name;
        this.hashBytes = hashBytes;
        this.matches = Collections.unmodifiableSet(matches);
    }

    public String getName() {
        return name;
    }

    public String getHashBytes() {
        return hashBytes;
    }

    public Set<RequestAndReplySession> getMatches() {
        return matches;
    }

}
