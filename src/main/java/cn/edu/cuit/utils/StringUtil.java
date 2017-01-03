package cn.edu.cuit.utils;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static byte[] serialize(String str) {
		return str == null ? null : str.getBytes();
	}
	
	public static String normalizeFieldName(String name) {
		String firstWord = name.substring(0, 1).toUpperCase();
		String backWord = name.substring(1, name.length());
		return firstWord + backWord;
	}
}
