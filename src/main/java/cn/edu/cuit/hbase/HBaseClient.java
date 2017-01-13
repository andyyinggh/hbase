package cn.edu.cuit.hbase;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HBaseClient {
	
	private static final Logger logger = LoggerFactory.getLogger(HBaseClient.class);
	private static Connection con = null;
	
	static {
		Configuration conf = HBaseConfiguration.create();
    	/*conf.set("hbase.master", "hdfs://172.22.35.199:60000");
    	conf.set("hbase.zookeeper.quorum", "172.22.35.199,172.22.35.201,172.22.35.202");*/
		
		conf.set("hbase.client.keyvalue.maxsize", "104857600");
		
    	conf.addResource("classpath:./hbase-site.xml");
    	try {
    		con = ConnectionFactory.createConnection(conf);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
    
    public static boolean create(String tableName, String[] columnFamilys) throws IOException {
    	TableName tablename = TableName.valueOf(tableName);
    	Admin admin = con.getAdmin();
    	if(!admin.tableExists(tablename)) {
    		System.out.println(tablename+" is exists");
    		return false;
    	}
    	HTableDescriptor desc = new HTableDescriptor(tablename);
    	for(String familyName : columnFamilys) {
    		desc.addFamily(new HColumnDescriptor(Bytes.toBytes(familyName)));
    	}
    	admin.createTable(desc);
    	return true;
    }
    
    public static void putRow(String tableName, String rowkey, String columnFamily, String column, String data) throws IOException {
    	TableName tablename = TableName.valueOf(tableName);
    	Table table = con.getTable(tablename);
    	Put put = new Put(Bytes.toBytes(rowkey));
    	put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(data));
    	table.put(put);
    	System.out.println("put '" + tableName + "','" + rowkey + "','" + columnFamily + ":" + column + "','" + data  + "'");
    }
    
    public static String query(String tableName, String rowkey, String columnFamily, String column) throws IOException {
    	String value = null;
    	TableName  tablename = TableName.valueOf(tableName);
    	Table table = con.getTable(tablename);
    	Get get = new Get(Bytes.toBytes(rowkey));
    	
    	Result result = table.get(get);
    	
    	value = new String(result.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(column)));
    	System.out.println(value);
    	logger.info("success");
    	return value;
    }
    
    public static void deleteColumn(String tableName, String rowkey, String columnFamily, String column) throws IOException {
    	TableName  tablename = TableName.valueOf(tableName);
    	Table table = con.getTable(tablename);
    	Delete delete = new Delete(Bytes.toBytes(rowkey));
    	delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
    	table.delete(delete);
    }
    
    public static void deleteAllColumns(String tableName, String rowkey) throws IOException {
    	TableName tablename = TableName.valueOf(tableName);
    	Delete delete = new Delete(Bytes.toBytes(rowkey));
    	Table table = con.getTable(tablename);
    	table.delete(delete);
    }
    
    /**
     * 删除表
     * @param tableName
     * @throws IOException
     */
    public static void deleteTable(String tableName) throws IOException {
    	TableName tablename = TableName.valueOf(tableName);
    	Admin admin = con.getAdmin();
    	admin.disableTable(tablename);
    	admin.deleteTable(tablename);

    }
    
    
	
    public static void main( String[] args ) throws IOException {
    	String tableName = "user";
    	String rowkey = "jack";
    	String columnFamily = "course";
    	String column = "math";
    	String data = "100";
//    	putRow(tableName, rowkey, columnFamily, column, data);
    	query(tableName, rowkey, columnFamily, column);
    	
    }
    
}
