package com.hadoop.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import model.Reply;
import model.Request;
import model.RequestAndReplySession;
import model.WebRecord;

import java.io.IOException;
import java.util.ArrayList;

public class WebRecordReducer extends Reducer<IntWritable, WebRecord, RequestAndReplySession, NullWritable> {
    public void reduce(IntWritable key, Iterable<WebRecord> values, Context context) throws IOException, InterruptedException {
        //receiving a list of request and reply
        ArrayList<WebRecord> res = new ArrayList<>();
        for(WebRecord record : values) {
            res.add(record);
        }

        //output  to QFDS based on hash(srcIP), hash(cookie), hash(username)
        for(int i = 0; i < res.size(); i++) {
            WebRecord record1 = res.get(i);
            if(!record1.getType().equals("request"))
                continue;
            for(int j = 0; j < res.size(); j++) {
                WebRecord record2 = res.get(j);
                if(!record2.getType().equals("reply"))
                    continue;
                if(Math.abs(record1.getTimeStamp().getTime() - record2.getTimeStamp().getTime()) <= 10 ) {
                    context.write(new RequestAndReplySession((Request)record1, (Reply)record2) , NullWritable.get());
                }
            }
        }
    }
}
