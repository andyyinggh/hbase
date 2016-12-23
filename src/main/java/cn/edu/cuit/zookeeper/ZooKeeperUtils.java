package cn.edu.cuit.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZooKeeperUtils {
	
	private static Logger logger = LoggerFactory.getLogger(ZooKeeperUtils.class);
	
	public static String create(ZooKeeper zkp, String path, byte[] data, List<ACL> acl, CreateMode createMode) {
		try {
			String node = zkp.create(path, data, acl, createMode);
			return node;
		} catch (KeeperException e) {
			logger.error("ZooKeeper exception: ", e);
		} catch (InterruptedException e) {
			logger.error("exception: ", e);
		}
		logger.error("Failed to create ZooKeeper path: " + path);
		return null;
	}

	public static List<String> getChildren(ZooKeeper zkp, String path, boolean watchFlag) {
		try {
			List<String> nodeList = zkp.getChildren(path, watchFlag);
			return nodeList;
		} catch (KeeperException e) {
			logger.error("ZooKeeper exception: ", e);
		} catch (InterruptedException e) {
			logger.error("exception: ", e);
		}
		logger.error("Failed to get ZooKeeper child nodes for path:" + path);
		return null;
	}
	
	public static boolean pathExists(ZooKeeper zkp, String path, boolean watchFlag) throws Exception {
		try {
			Stat stat = zkp.exists(path, watchFlag);
			if(stat == null) {
				return false;
			}
			return true;
		} catch (KeeperException e) {
			logger.error("ZooKeeper exception: ", e);
		} catch (InterruptedException e) {
			logger.error("exception: ", e);
		}
		logger.error("Failed to check ZooKeeper path existence: {}", path);
		throw new Exception("ZooKeeper Server Exception");
	}
	
	public static byte[] getData(ZooKeeper zkp, String path, boolean watchFlag) throws Exception {
		try {
			return zkp.getData(path, true, null);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.error("Failed to get data of ZooKeeper path: {}", path);
		throw new Exception("ZooKeeper Server Exception");
	}
	
	public static void delete(ZooKeeper zkp, String path) {
		try {
			zkp.delete(path, -1);
			return;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
		logger.error("Failed to delete data of ZooKeeper path: {}", path);
	}
	
	
	
}
