package com.hadoop.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import model.WebRecord;
import utilities.inputParser;
import java.io.IOException;


public class WebRecordMapper extends Mapper<LongWritable, Text, IntWritable, WebRecord>{
    public void map(LongWritable key, Text line, Context context) throws IOException, InterruptedException {
        //读入数据
        String data = line.toString();
        //分类成Request or reply and output to reducer
        WebRecord webRecord = inputParser.parseRecord(data.split(" "));

        if (webRecord != null)
            context.write(new IntWritable(webRecord.getHash()), webRecord);
    }
}
