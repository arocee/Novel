package com.novel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 文件对象的操作
 * @author Aroceee
 *
 */
public class FileHandle {

	/**
	 * 获取文件列表
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public final static String[] getFileList(String path) throws Exception {
		File file = new File(path);
		
		if(!file.exists()){
			throw new RuntimeException("文件目录不存在");
		}
		
		if(!file.isDirectory()) {
			throw new RuntimeException("非目录");
		}
		
		String[] list = file.list();
		return list;
	}
	
	/**
	 * 缓冲字符流读取文本内容
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public final static String getText(String path) throws Exception {
		
		FileReader fr = null;
		BufferedReader br = null;
		StringBuilder sb = null;
		String str = null;
		
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			
			sb = new StringBuilder();
			
			while((str = br.readLine())!=null){
				sb.append(str);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(br != null)
				br.close();
			if(fr != null)
				fr.close();
		}
		
		return sb.toString();
	}
}
