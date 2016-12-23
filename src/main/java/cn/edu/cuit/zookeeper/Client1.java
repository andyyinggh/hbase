package cn.edu.cuit.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.security.ntlm.Client;

public class Client1 {
	
	private static Logger logger = LoggerFactory.getLogger(Client.class);
	private static String connectString = "127.0.0.1:2181";
	private static int sessionTimeout = 50000;
	private static String appName = "test";
	private static String nodeName = "node1";
	private static String parentPath = "/" + appName;
	private static String childPath = parentPath + "/" + nodeName;
	private static String sequenceNodeName;

	public static void main(String[] args) {
		try {
			ZooKeeper zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
				
				@Override
				public void process(WatchedEvent event) {
					logger.info("receive watch event on node: " + event.getPath() + ", event type: " + event.getType().toString() + " event state: " + event.getState()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				
				}
			});
			
			try {
				if(!ZooKeeperUtils.pathExists(zk, parentPath, true)) {
					if (ZooKeeperUtils.create(zk, parentPath, appName.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT) == null) {
						throw (new Exception("Unable to create path on ZooKeeper: " + parentPath));
					}
				}
				sequenceNodeName = ZooKeeperUtils.create(zk, childPath, nodeName.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
				if(sequenceNodeName == null) {
					throw (new Exception("Unable to create path on ZooKeeper: " + childPath));
				}
				System.out.println(sequenceNodeName);
//				zk.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	

}
