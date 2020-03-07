package com.hadoop.mapreduce;


import model.QueryFocusedDataSet;
import model.RequestAndReplySession;
import model.WTRKey;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class QFDReducer extends Reducer<WTRKey, RequestAndReplySession, NullWritable, NullWritable> {
    public void reduce(WTRKey key, Iterable<RequestAndReplySession> sessions, Context context) throws IOException {
        Set<RequestAndReplySession> matchSet = new HashSet();
        for(RequestAndReplySession session: sessions)
        {
            matchSet.add(session);
        }

        String keyName=key.getName();
        int dataHash=key.getHashBytes();
        String filename="qfds/"+keyName+"/"+keyName+"_"+dataHash;
        FileSystem hdfs=FileSystem.get(context.getConfiguration());
        Path path=new Path(filename);
        FSDataOutputStream outputStream = hdfs.create(path);
        ObjectOutputStream oos=new ObjectOutputStream(outputStream);
        oos.writeObject(new QueryFocusedDataSet(keyName, ""+dataHash, matchSet));
        outputStream.close();
        oos.close();
    }
}