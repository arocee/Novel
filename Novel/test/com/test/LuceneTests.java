package com.test;

import java.util.List;

import org.junit.Test;

import com.novel.dao.lucene.LuceneSearch;
import com.novel.model.Novel;
import com.novel.util.PropertiesUtil;

public class LuceneTests {
	
	@Test
	public void testSearch() throws Exception {
		LuceneSearch luceneSearch = new LuceneSearch();
		
		List<Novel> list = luceneSearch.search("的", Integer.parseInt(PropertiesUtil.getPropertyValue("lucene.pagesize")), 0);
		
		for (Novel novel : list) {
			System.out.println(novel.getParagraph());
		}
	}

}
