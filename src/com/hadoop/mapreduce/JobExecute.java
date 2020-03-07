package com.hadoop.mapreduce;


import model.WTRKey;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import model.RequestAndReplySession;
import model.WebRecord;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;

//main entry
public class JobExecute {
    private static final int NUM_MAPPERS = 8;
    private static final int NUM_REDUCERS = 8;
    public static void main(String[] args) throws Exception{
        Configuration conf1 = new Configuration();
        Job job1 = Job.getInstance(conf1, "QFDWriter");

        conf1.setInt(MRJobConfig.NUM_MAPS, NUM_MAPPERS);
        conf1.setInt(MRJobConfig.NUM_REDUCES, NUM_REDUCERS);

        job1.setJarByClass(JobExecute.class);
        job1.setJobName("Hadoop finds tor");

        job1.setMapperClass(WebRecordMapper.class);
        job1.setMapOutputKeyClass(IntWritable.class);
        job1.setMapOutputValueClass(WebRecord.class);

        //it's intermediate output
        job1.setReducerClass(WebRecordReducer.class);
        job1.setOutputKeyClass(RequestAndReplySession.class);
        job1.setOutputValueClass(NullWritable.class);


        //设置读取文件的路径
        FileInputFormat.addInputPath(job1, new Path(args[0]));
        //MapReduce的输出路径
        FileOutputFormat.setOutputPath(job1, new Path(args[1]));
        job1.waitForCompletion(true);

        //-------------------Second Part -----------------

        Configuration conf2 = new Configuration();
        Job job2 = job1.getInstance(conf2, "QFDWriter2");

        conf2.setInt(MRJobConfig.NUM_MAPS, NUM_MAPPERS);
        conf2.setInt(MRJobConfig.NUM_REDUCES, NUM_REDUCERS);

        job2.setJarByClass(JobExecute.class);
        job2.setMapperClass(QFDMapper.class);
        job2.setReducerClass(QFDReducer.class);
        job2.setInputFormatClass(SequenceFileInputFormat.class);
        job2.setOutputFormatClass(NullOutputFormat.class);

        job2.setMapOutputKeyClass(WTRKey.class);
        job2.setMapOutputValueClass(RequestAndReplySession.class);

        FileInputFormat.addInputPath(job2, new Path(args[1])); //read From Intermediate output path
        job2.waitForCompletion(true);

    }

}
