package cn.edu.cuit.storm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
/**
 * 
 * @author yp-tc-m-7177
 *
 */
public class TestBolt extends BaseBasicBolt {
	
	private static Logger logger = LoggerFactory.getLogger(TestBolt.class);

	private static final long serialVersionUID = 1L;

	public void execute(Tuple arg0, BasicOutputCollector arg1) {
		try {
            String mesg = arg0.getString(0);
            if (mesg != null)
                System.out.println("=================="+mesg);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
