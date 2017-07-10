package cn.edu.cuit.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class TestMapreduce {
	
	public static void main(String[] args) {
		
	}
	
	public static class MyMapper extends Mapper<LongWritable, Text, Text, MapWritable> {
		
		protected void map() {
			
		}
		
	}
	
	public static class MyReduce extends Reducer<Text, MapWritable, Text, Text> {
		
		protected void reduce() {
			
		}
	}

}
