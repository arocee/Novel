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
import com.novel.dao.lucene.LuceneIndex;
import com.novel.model.Article;
import com.novel.model.Novel;
import com.novel.model.Paragraph;
import com.novel.model.Section;
import com.novel.model.Type;

@Service("editService")
public class EditService {
	
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
	
	private LuceneIndex luceneIndex;
	
	@Transactional(readOnly=true)
	public List<Type> getTypes(int isAll) throws Exception {
		return typeMapper.selectList(isAll);
	}
	
	@Transactional(readOnly=true)
	public Type getType(Integer tid) throws Exception {
		return typeMapper.selectByPrimaryKey(tid);
	}
	
	@Transactional(readOnly=true)
	public Article getAricle(Integer id) throws Exception {
		return articleMapper.selectByPrimaryKey(id);
	}
	
	@Transactional(readOnly=true)
	public List<Article> getArticlesByTid(Integer tid) throws Exception {
		return articleMapper.selectByTid(tid);
	}
	
	@Transactional
	public Integer addArticle(Article article) throws Exception {
		int line =  articleMapper.insert(article);
		if(line > 0){
			return article.getId();
		} else {
			return null;
		}
	}
	
	@Transactional
	public Integer updateArticle(Article article) throws Exception {
		return articleMapper.updateByPrimaryKeySelective(article);
	}
	
	@Transactional
	public Integer deleteArticle(Integer id) throws Exception {
		return articleMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional(readOnly=true)
	public Section getSection(Integer id) throws Exception {
		return sectionMapper.selectByPrimaryKey(id);
	}
	
	@Transactional(readOnly=true)
	public List<Section> getSectionsByAid(Integer aid) throws Exception {
		return sectionMapper.selectByAid(aid);
	}
	
	@Transactional
	public Integer addSection(Section section) throws Exception {
		int line = sectionMapper.insert(section);
		if(line > 0){
			return section.getId();
		} else {
			return null;
		}
	}
	
	@Transactional
	public Integer updateSection(Section section) throws Exception {
		return sectionMapper.updateByPrimaryKeySelective(section);
	}
	
	@Transactional
	public Integer deleteSection(Integer id) throws Exception {
		return sectionMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional(readOnly=true)
	public List<Paragraph> getParagraphesBySid(Integer sid) throws Exception {
		return paragraphMapper.selectBySid(sid);
	}
	
	@Transactional(readOnly=true)
	public Paragraph getParagraphByPid(Integer pid) throws Exception {
		return paragraphMapper.selectByPrimaryKey(pid);
	}
	
	@Transactional
	public Integer addParagraph(Novel novel) throws Exception {
		Paragraph paragraph = new Paragraph(novel.getPid(), novel.getParagraph(), novel.getSid());
		int line =  paragraphMapper.insert(paragraph);
		
		if(line > 0){
			novel.setPid(paragraph.getId());
		} else {
			return null;
		}
		
		try {
			luceneIndex = new LuceneIndex();
			luceneIndex.addIndex(novel);
		} catch (Exception e) {
			log.error("添加索引失败：<br/>" + e);
			throw e;
		} finally {
			luceneIndex.close();
		}
		
		return paragraph.getId();
	}
	
	@Transactional
	public Integer updateParagraph(Novel novel) throws Exception {
		Paragraph paragraph = new Paragraph(novel.getPid(), novel.getParagraph(), novel.getSid());
		Integer line =  paragraphMapper.updateByPrimaryKeySelective(paragraph);

		try {
			luceneIndex = new LuceneIndex();
			luceneIndex.updateIndex(novel);
		} catch (Exception e) {
			log.error("更新索引失败：<br/>" + e);
			throw e;
		} finally {
			luceneIndex.close();
		}
		
		return line;
	}
	
	@Transactional
	public Integer deleteParagraph(Novel novel) throws Exception {
		Integer line = paragraphMapper.deleteByPrimaryKey(novel.getPid());
		
		try {
			luceneIndex = new LuceneIndex();
			luceneIndex.deleteIndex(novel.getPid());
		} catch (Exception e) {
			log.error("删除索引失败：<br/>" + e);
			throw e;
		} finally {
			luceneIndex.close();
		}
		
		return line;
	}
	
	public void resetIndex() throws Exception {
		List<Novel> novels = novelMapper.selectAll();
		
		try {
			luceneIndex = new LuceneIndex();
			luceneIndex.createIndex(novels);
		} catch (Exception e) {
			log.error("重置索引失败：<br/>" + e);
			throw e;
		} finally {
			luceneIndex.close();
		}
	}
}
