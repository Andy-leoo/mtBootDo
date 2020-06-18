package com.bootdo.common.utils;

import java.io.*;
import java.util.UUID;

public class FileUtil {

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	public static String readJsonFile(String path){
		File file = new File(path);
		if (!file.exists() || file.isDirectory()) {
			System.out.println("json 文件不存在");
		}
		StringBuffer strJson = new StringBuffer("[");

		BufferedReader bf = null;

		try {
			bf = new BufferedReader(new FileReader(file));
			String tempStr = null;
			int line = 1;
			//一行一行的读取
			while ((tempStr = bf.readLine()) != null){
				strJson.append(tempStr).append(",");
				line++;
			}
			strJson.append("]");
		} catch (FileNotFoundException e) {
			System.out.println("读取文件出错");
		} catch (IOException e) {
			System.out.println("读取文件出错");
		}
		return strJson.toString();
	}
}
