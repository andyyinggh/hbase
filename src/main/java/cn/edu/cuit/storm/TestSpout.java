package cn.edu.cuit.storm;

import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
/**
 * 
 * @author yp-tc-m-7177
 *
 */
public class TestSpout extends BaseRichSpout {
	
	private static Logger logger = LoggerFactory.getLogger(TestSpout.class);

	private static final long serialVersionUID = 1L;
	
	SpoutOutputCollector spoutOutputCollector;
	private String[] words = {"1","2","3","4","5"};

	public void nextTuple() {
		String word = words[new Random().nextInt(words.length)];
		spoutOutputCollector.emit(new Values(word));
	}

	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		this.spoutOutputCollector = arg2;
		
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("log"));
		
	}

}
