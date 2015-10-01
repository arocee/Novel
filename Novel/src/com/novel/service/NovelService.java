package com.novel.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.ArticleMapper;
import com.novel.dao.NovelMapper;
import com.novel.dao.ParagraphMapper;
import com.novel.dao.SectionMapper;
import com.novel.dao.TypeMapper;
import com.novel.dao.lucene.LuceneSearch;
import com.novel.model.Article;
import com.novel.model.Novel;
import com.novel.model.Search;
import com.novel.model.Section;
import com.novel.model.Type;
import com.novel.util.PropertiesUtil;

@Service("novelService")
public class NovelService {
	
	private static Log log = LogFactory.getLog(NovelService.class);
	
	@Resource(name="typeMapper")
	private TypeMapper typeMapper;
	
	@Resource(name="articleMapper")
	private ArticleMapper articleMapper;
	
	@Resource(name="sectionMapper")
	private SectionMapper sectionMapper;
	
	@Resource(name="paragraphMapper")
	private ParagraphMapper paragraphMapper;
	
	@Resource(name="novelMapper")
	private NovelMapper novelMapper;
	
	@Transactional(readOnly=true)
	public List<Type> getTypes(int isAll) throws Exception {
		return typeMapper.selectList(isAll);
	}
	
	@Transactional(readOnly=true)
	public Article getAricleAndSection(Integer id) throws Exception {
		return articleMapper.selectArticleAndSectionByPrimaryKey(id);
	}
	
	@Transactional(readOnly=true)
	public Section getSectionAndParagraph(Integer id) throws Exception {
		return sectionMapper.selectSectionAndParagraphByPrimaryKey(id);
	}
	
	@Transactional(readOnly=true)
	public Integer getNovelCount(String keyWords, Integer id, Integer searchType) throws Exception {
		Integer count = 0;
		LuceneSearch luceneSearch = null;
		
		try {
			luceneSearch = new LuceneSearch();
			
			switch(searchType) {
			case 0:
				count = luceneSearch.count(keyWords);
				break;
			case 1:
				count = luceneSearch.countType(keyWords, id);
				break;
			case 2:
				count = luceneSearch.countArticle(keyWords, id);
				break;
			case 3:
				count = luceneSearch.countSection(keyWords, id);
				break;
			}
		} catch(RuntimeException e) {
			log.error("搜索数目失败：<br/>" + e);
			throw e;
		} finally {
			luceneSearch.close();  // 关闭资源
		}
		
		return count;
	}
	
	@Transactional(readOnly=true)
	public List<Novel> getNovel(String keyWords, Integer id, Integer searchType, Integer pageNow) throws Exception {
		List<Novel> list = null;
		LuceneSearch luceneSearch = null;
		
		try {
			luceneSearch = new LuceneSearch();

			switch(searchType) {
			case 0:
				list = luceneSearch.search(keyWords, Integer.parseInt(PropertiesUtil.getPropertyValue("lucene.pagesize")), pageNow);
				break;
			case 1:
				list = luceneSearch.searchType(keyWords, id, Integer.parseInt(PropertiesUtil.getPropertyValue("lucene.pagesize")), pageNow);
				break;
			case 2:
				list = luceneSearch.searchArticle(keyWords, id, Integer.parseInt(PropertiesUtil.getPropertyValue("lucene.pagesize")), pageNow);
				break;
			case 3:
				list = luceneSearch.searchSection(keyWords, id, Integer.parseInt(PropertiesUtil.getPropertyValue("lucene.pagesize")), pageNow);
				break;
			}
		} catch(RuntimeException e) {
			log.error("搜索条目失败：<br/>" + e);
			throw e;
		} finally {
			luceneSearch.close(); // 关闭资源
		}
		
		return list;
	}
	
	@Transactional(readOnly=true)
	public List<Search> getHotestKeyWords(String key) throws Exception {
		return novelMapper.selectKeyword(key);
	}
}
