package com.hadoop.mapreduce;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;
import model.RequestAndReplySession;
import model.WTRKey;

import java.io.IOException;

public class QFDMapper extends Mapper<RequestAndReplySession, NullWritable, WTRKey, RequestAndReplySession> {
    public void map(RequestAndReplySession sessionRecord, NullWritable ignore, Context context) throws IOException, InterruptedException {
        String srcIp = sessionRecord.request.getSrcIp();
        String cookie = sessionRecord.request.cookie;
        String username = sessionRecord.reply.username;
        int ipKey = (srcIp).hashCode();
        context.write(new WTRKey("srcIP", ipKey), sessionRecord);
        int cookieKey = (cookie).hashCode();
        context.write(new WTRKey("cookie", cookieKey), sessionRecord);
        int nameKey = (username).hashCode();
        context.write(new WTRKey("username", nameKey), sessionRecord);
    }
}
