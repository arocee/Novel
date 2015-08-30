package com.novel.dao.lucene;

import java.nio.file.FileSystems;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.novel.model.Novel;

/**
 * Lucene索引操作
 * @author Aroceee
 *
 */
@Component("luceneIndex")
public class LuceneIndex {
	
	private Analyzer analyzer;
	private Directory directory;
	private IndexWriterConfig config;
	private IndexWriter indexWriter;
	
	/**
	 * 创建索引
	 * @param novel
	 * @throws Exception
	 */
	public void createIndex(List<Novel> novel) throws Exception {
		if(indexWriter == null)
			initIndexWriter(true);
		
		indexWriter.deleteAll();
		
		for (Novel article: novel) {
			Document doc = new Document();
			doc.add(new IntField("pid", article.getPid(), Store.YES));
			doc.add(new IntField("tid", article.getTid(), Store.YES));
			doc.add(new IntField("aid", article.getAid(), Store.YES));
			doc.add(new IntField("sid", article.getSid(), Store.YES));
			doc.add(new TextField("p", article.getParagraph(), Store.YES));
			doc.add(new TextField("s", article.getSection(), Store.YES));
			doc.add(new TextField("a", article.getArticle(), Store.YES));
			doc.add(new TextField("t", article.getType(), Store.YES));
			
			doc.add(new NumericDocValuesField("sortId", article.getPid()));

			indexWriter.addDocument(doc);				
		}
		
		indexWriter.forceMerge(1);
		indexWriter.commit();
	}
	
	/**
	 * 添加索引
	 * @param article
	 * @throws Exception
	 */
	public void addIndex(Novel article) throws Exception {
		if(indexWriter == null)
			initIndexWriter(false);
		
		Document doc = new Document();
		doc.add(new IntField("pid", article.getPid(), Store.YES));
		doc.add(new IntField("tid", article.getTid(), Store.YES));
		doc.add(new IntField("aid", article.getAid(), Store.YES));
		doc.add(new IntField("sid", article.getSid(), Store.YES));
		doc.add(new TextField("p", article.getParagraph(), Store.YES));
		doc.add(new TextField("s", article.getSection(), Store.YES));
		doc.add(new TextField("a", article.getArticle(), Store.YES));
		doc.add(new TextField("t", article.getType(), Store.YES));
		
		doc.add(new NumericDocValuesField("sortId", article.getPid()));
		
		indexWriter.addDocument(doc);
		indexWriter.forceMerge(1);
		indexWriter.commit();
	}
	
	/**
	 * 删除索引
	 * @param words
	 */
	public void deleteIndex(String words) throws Exception {
		if(indexWriter == null)
			initIndexWriter(false);
		
		indexWriter.deleteDocuments((new QueryParser("p", analyzer)).parse(words));
		indexWriter.forceMerge(1);
		indexWriter.commit();
	}
	
	/**
	 * 更新索引
	 * @param id
	 * @param newSection
	 * @throws Exception
	 */
	public void updateIndex(Novel article) throws Exception {
		if(indexWriter == null)
			initIndexWriter(false);
		
		Document doc = new Document();
		doc.add(new IntField("pid", article.getPid(), Store.YES));
		doc.add(new IntField("tid", article.getTid(), Store.YES));
		doc.add(new IntField("aid", article.getAid(), Store.YES));
		doc.add(new IntField("sid", article.getSid(), Store.YES));
		doc.add(new TextField("p", article.getParagraph(), Store.YES));
		doc.add(new TextField("s", article.getSection(), Store.YES));
		doc.add(new TextField("a", article.getArticle(), Store.YES));
		doc.add(new TextField("t", article.getType(), Store.YES));
		
		indexWriter.updateDocument(new Term("pid", article.getPid() + ""), doc);
		indexWriter.commit();
	}
	
	/**
	 * 实例化indexwriter
	 * @param create
	 * @throws Exception
	 */
	public void initIndexWriter(boolean create) throws Exception {
		// mmseg4j 有几个 analyzer：SimpleAnalyzer、ComplexAnalyzer、MaxWordAnalyzer、MMSegAnalyzer。
		analyzer = new ComplexAnalyzer();
		directory = FSDirectory.open(FileSystems.getDefault().getPath("d:", "Novel-resource", "lucene", "index", "novelIndex"));
		
		config = new IndexWriterConfig(analyzer);
		
		if (create) {
			config.setRAMBufferSizeMB(128.0);
			config.setOpenMode(OpenMode.CREATE);
	      } else {
	    	config.setOpenMode(OpenMode.CREATE_OR_APPEND);
	      }
		
		indexWriter = new IndexWriter(directory, config);
	}
	
	public void close() throws Exception {
		indexWriter.close();
	}
}
