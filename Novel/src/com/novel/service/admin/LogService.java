package com.novel.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novel.model.admin.Log;
import com.novel.util.FileHandle;
import com.novel.util.PropertiesUtil;

@Service("logService")
public class LogService {
	
	String path = PropertiesUtil.getPropertyValue("log.path");  // log所在目录

	/**
	 * 获取日志列表
	 * @return
	 * @throws Exception
	 */
	public List<Log> getLogs() throws Exception {

		String[] list = FileHandle.getFileList(path);
		
		List<Log> logs = new ArrayList<>();
		
		for (String filename : list) {
			String logName = filename.substring(0, filename.indexOf("."));
			
			boolean exist = false;
			
			for (Log log : logs) {
				if(log.getLog().equals(logName)) {
					exist = !exist;
					break;
				}
			}
			
			if(exist) {
				continue;
			}
			
			Log log = new Log();
			log.setLog(logName);
			
			List<String> fileNames = new ArrayList<>();
			for (String name : list) {
				if(name.startsWith(logName)) {
					fileNames.add(name);
				}
			}
			log.setFileNames(fileNames);
			
			logs.add(log);
		}
		
		return logs;
	}
	
	/**
	 * 获取日志的详细内容
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String getLogContent(String fileName) throws Exception {
		String log = FileHandle.getText(path + "/" + fileName);
		if(log == null || "".equals(log)){
			return "<p class='emptyTip'>没有日志</p>";
		} else {
			return log.replaceAll("'", "\"") + "</table>";
		}
		
	}
}
