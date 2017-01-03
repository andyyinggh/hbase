package cn.edu.cuit.mongodb;

import java.io.File;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoDBClient {

	private String ip;

	private int port;

	private MongoClient mg;

	public MongoDBClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			mg = new MongoClient(ip, port);
		} catch (Exception e) {
			
		}
	}

	public boolean isExisting(String id, String database) {
		DB db= mg.getDB(database);
		GridFS fs = new GridFS(db);
		BasicDBObject obj = new BasicDBObject();
		obj.put("_id", id);
		
		GridFSDBFile file = fs.findOne(obj);
		if (file != null) {
            return true;
		}
		return false;
	}
	
	public boolean saveFile(String id, String path, String database) throws Exception {
		DB db = mg.getDB(database);
		GridFS fs = new GridFS(db);
		GridFSInputFile input;
		BasicDBObject obj = new BasicDBObject();
		obj.put("_id", id);
		
		GridFSDBFile file = fs.findOne(obj);
		if (file != null) {
            fs.remove(obj);
		}
		input = fs.createFile(new File(path));
		input.setId(id);
		input.save();

		return true;
	}

	public boolean readFile(String id, String dest, String fileName, String database) throws Exception {
		DB db = mg.getDB(database);
		GridFS fs = new GridFS(db);
		BasicDBObject obj = new BasicDBObject();
		obj.put("_id", id);
		GridFSDBFile file = fs.findOne(obj);

		file.writeTo(dest + "/" + fileName);
		return true;
	}
	
	public boolean deleteFile(String id, String database) throws Exception {
		DB db = mg.getDB(database);
		GridFS fs = new GridFS(db);
		BasicDBObject obj = new BasicDBObject();
		obj.put("_id", id);
		GridFSDBFile file = fs.findOne(obj);
		if (file != null) {
            fs.remove(obj);
		}
		
		return true;
	}

	public static void main(String[] args) {
//		MongoDBClient client = new MongoDBClient("localhost", 27017);
		//client.saveFile("Container", "G:\\SMP\\DM Client Sample\\DMClient-Sample\\meu001-1477559052192\\Container.zip");
		//client.readFile("Container", "D:", "Container.zip");
	}
}
