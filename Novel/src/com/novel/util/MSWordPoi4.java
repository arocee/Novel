/*
 * 文 件 名:  MSWordPoi4.java
 * 版    权:  Sunny Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  L.Hao
 * 修改时间:  2014-8-8
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.novel.util;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  L.Hao
 * @version  [版本号, 2014-8-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

public class MSWordPoi4 {

	/**
	 * 实现对word读取和修改操作
	 * 
	 * @param filePath
	 *            word模板路径和名称
	 */
	public static List<List<String>> readWord(String filePath) {
		// 读取word模板
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		HWPFDocument hdt = null;
		try {
			hdt = new HWPFDocument(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		List<List<String>> article = new ArrayList<>();

		// 读取word文本内容
		Range range = hdt.getRange();
		TableIterator tableIt = new TableIterator(range);
		
		while (tableIt.hasNext()) {
			Table tb = (Table) tableIt.next();
			
			List<String> section = new ArrayList<>();
			
			// 迭代行，默认从0开始
			for (int i = 0; i < tb.numRows(); i++) {
				TableRow tr = tb.getRow(i);
				// 迭代列，默认从0开始
				for (int j = 0; j < tr.numCells(); j++) {
					TableCell td = tr.getCell(j);// 取得单元格
					// 取得单元格的内容
					for (int k = 0; k < td.numParagraphs(); k++) {
						Paragraph para = td.getParagraph(k);
						String s = para.text();
						section.add(s.substring(0, s.length() - 1));
					} // end for
				} // end for
			} // end for
			
			article.add(section);
			
		} // end while
		
		return article;
	}
}
