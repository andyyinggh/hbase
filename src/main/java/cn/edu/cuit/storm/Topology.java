package cn.edu.cuit.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class Topology {
	
	
	private static TopologyBuilder builder = new TopologyBuilder();

    public static void main(String[] args) {

        Config config = new Config();

        builder.setSpout("spout", new TestSpout());
        builder.setBolt("bolt", new TestBolt()).shuffleGrouping("spout");
        config.setDebug(false);

        config.setMaxTaskParallelism(1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("topology", config, builder.createTopology());
        Utils.sleep(1000000);
    }

}
