package cn.edu.cuit.utils;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 配置文件工具类
 * @author Guanghua.Ying
 * @date 2016年12月20日
 *
 */
public class ConfigUtil {
	
	private static Properties properties = new Properties();
	
	public static Properties load(String configPath) {
		ResourceBundle rb = ResourceBundle.getBundle(configPath);
		for(String key : rb.keySet()) {
			properties.put(key, rb.getString(key));
		}
		return properties;
	}
	
	public static Properties load(String configPath, ClassLoader clazLoader) {
		ResourceBundle rb = ResourceBundle.getBundle(configPath, Locale.getDefault(), clazLoader);
		for(String key : rb.keySet()) {
			properties.put(key, rb.getString(key));
		}
		return properties;
	}
	
	public static String getString(String key) {
		return properties.getProperty(key);
	}
	
	public static Integer getInteger(String key) {
		String val = properties.getProperty(key);
		Integer intVal = null;
		try {
			intVal = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return null;
		}
		return intVal;
	}
	
	public static Boolean getBoolean(String key) {
		String val = properties.getProperty(key);
		if(val == null) {
			return null;
		}
		return Boolean.parseBoolean(val);
	}

	public static Properties getProperties() {
		return properties;
	}
	

	
}
