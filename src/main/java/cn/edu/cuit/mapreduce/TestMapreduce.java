package cn.edu.cuit.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * MapReduce程序
 * @author guanghua.ying
 * @date 2017年7月10日 上午11:12:16
 */
public class TestMapreduce {

	public static void main(String[] args) throws Exception {
		Configuration conf =HBaseConfiguration.create();
		
		org.apache.hadoop.mapreduce.Job job = new Job(conf, "test");
		job.setJarByClass(TestMapreduce.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MapWritable.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	public static class MyMapper extends Mapper<LongWritable, Text, Text, MapWritable> {
		
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, MapWritable>.Context context)
				throws IOException, InterruptedException {

		}

	}

	public static class MyReducer extends Reducer<Text, MapWritable, Text, Text> {

		@Override
		protected void reduce(Text key, Iterable<MapWritable> values,
				Reducer<Text, MapWritable, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
		}
	}

}
