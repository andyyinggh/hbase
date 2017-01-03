package cn.edu.cuit.mongodb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoFileToos {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(MongoFileToos.class);

	private Mongo mg = null;
	private DB db = null;
	private GridFS myFS = null;

	public MongoFileToos(String ip, int port, String dbName, String tableName) {
		try {
			mg = new MongoClient(ip, port);
			db = mg.getDB(dbName);
			myFS = new GridFS(db, tableName);
		} catch (Exception e) {
			logger.error("MongoDB host error...");
		}
	}

	public String saveFile(String filePath, String fileName) {
		File file = new File(filePath);
		String fileId = null;
		try {
			String md5 = loadMD5(new FileInputStream(file));
			
			fileId = getFileId(md5, fileName);
			if (fileId != null && fileId.length() >0) {
				logger.debug("The file {} is exist, fileID: {}.", fileName, fileId);
				return fileId;
			}
			
			GridFSInputFile inputFile = myFS.createFile(new FileInputStream(file));
			inputFile.setFilename(fileName);
			inputFile.save();
			fileId = inputFile.getId().toString();
			logger.debug("The file {} is saved, fileID: {}.", fileName, fileId);
		} catch (IOException e) {
			logger.error("Save file occured error.", e);
		}
		
		return fileId;
	}
	
	private String loadMD5(InputStream inputStream) {
		String md5 = null;

		try {
			MessageDigest md5Digest = null;
			try {
				md5Digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				return null;
			}

			byte[] buffer = new byte[8192];
			int length;

			while ((length = inputStream.read(buffer)) != -1) {
				md5Digest.update(buffer, 0, length);
			}
			
			md5 = new String(Hex.encodeHex(md5Digest.digest()));
		} catch (IOException e) {
			logger.error("Load file occured error - method getMD5.", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Load file occured error - inputStream.close()", e);
				}
			}
		}

		return md5;
	}
	
	public File getFileById(String fileId, String localPath) {
		File file = null;
		try {
			new File(localPath).mkdirs();
			GridFSDBFile inputFile = myFS.findOne(new BasicDBObject("_id", new ObjectId(fileId)));
			file = new File(localPath + File.separator + inputFile.getFilename());
			if (file.exists()) {
				logger.debug("The file {} is exist, fileID: {}.", inputFile.getFilename(), fileId);
				return file;
			}
			inputFile.writeTo(file.getAbsolutePath());
			logger.debug("The file {} is loaded, fileID: {}.", inputFile.getFilename(), fileId);
		} catch (Exception e) {
			logger.error("Load file occured error.", e);
		}

		return file;

	}

	public String getFileId(String md5, String fileName) {
		String fileId = null;
		DBObject query = new BasicDBObject();
		query.put("md5", md5);
		query.put("filename", fileName);
		DBCursor cursor = myFS.getFileList(query);
		if (cursor.size() > 0) {
			DBObject dbObj = cursor.next();
			fileId = dbObj.get("_id").toString();
		}
		cursor.close();
		return fileId;
	}
}
