package com.hadoop.mapreduce;

import model.QueryFocusedDataSet;
import model.RequestAndReplySession;
import model.WTRKey;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.hash.Hash;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

public class TorMapper extends Mapper<LongWritable, Text, WTRKey, RequestAndReplySession> {
    //read in a list of Exit Tor Nodes' Ip. might be a srcIp or destIp though, we only take it when it's srcIP
    // user <---->  【 torNode  <----> website】  we only take the second part.
    public void map(LongWritable lineNo, Text line, Context context) throws IOException, InterruptedException {
        String ip = line.toString();
        String fileName = "qfds/srcIp/srcIp_"+ ip.hashCode();
        Path path = new Path(fileName);
        //the websites visited by this torNode Ip.
        QueryFocusedDataSet qfdsByIp = null;
        try {
            FileSystem hdfs=FileSystem.get(context.getConfiguration());
            FSDataInputStream inputStream = hdfs.open(path);
            ObjectInputStream dataStream=new ObjectInputStream(inputStream);
            qfdsByIp=(QueryFocusedDataSet)dataStream.readObject();
            inputStream.close();
            dataStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(qfdsByIp == null) {
            System.out.println("qfdsByIp is Empty!");
            return;
        }


        QueryFocusedDataSet qfdsByCookie = null;

        //for cookie associated with these dialogue sessions between tor Node and websites
        Set<RequestAndReplySession> matches = qfdsByIp.getMatches();
        for(RequestAndReplySession session : matches) {
            String cookie = session.request.cookie;
            String filename = "hdfs/cookie/cookie_" + cookie.hashCode();
            Path cookiePath = new Path(fileName);
            try {
                FileSystem cookieHdfs=FileSystem.get(context.getConfiguration());
                FSDataInputStream inputStreams = cookieHdfs.open(cookiePath);
                ObjectInputStream dataStreams=new ObjectInputStream(inputStreams);
                qfdsByCookie=(QueryFocusedDataSet)dataStreams.readObject();
                inputStreams.close();
                dataStreams.close();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            if(qfdsByCookie == null) {
                System.out.println("qfdsCookie is Empty!");
                return;
            }

            //From websites reply, we can see the userName inside. Then we know which users are using Tors.
            // Then we can use userName qfds to find out all of their activities in addition to those data when using Tor
            // Take a step further, we can even find their real ips, by checking username qfds and get <srcIp, destIp> pairs
            Set<RequestAndReplySession> sessions = qfdsByCookie.getMatches();
            for(RequestAndReplySession requestAndReply: sessions) {
                String torUserName = requestAndReply.reply.username;
                context.write(new WTRKey("torUsers", torUserName.hashCode()), requestAndReply);
            }

        }
    }
}
