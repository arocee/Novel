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
import org.apache.lucene.search.NumericRangeQuery;
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
	 * @param novels
	 * @throws Exception
	 */
	public void createIndex(List<Novel> novels) throws Exception {
		if(indexWriter == null)
			initIndexWriter(true);
		
		indexWriter.deleteAll();
		
		for (Novel novel: novels) {
			Document doc = new Document();
			doc.add(new IntField("pid", novel.getPid(), Store.YES));
			doc.add(new IntField("tid", novel.getTid(), Store.YES));
			doc.add(new IntField("aid", novel.getAid(), Store.YES));
			doc.add(new IntField("sid", novel.getSid(), Store.YES));
			doc.add(new TextField("p", novel.getParagraph(), Store.YES));
			doc.add(new TextField("s", novel.getSection(), Store.YES));
			doc.add(new TextField("a", novel.getArticle(), Store.YES));
			doc.add(new TextField("t", novel.getType(), Store.YES));
			
			doc.add(new TextField("ppid", novel.getPid() + "", Store.YES));  // 更新索引用
			
			doc.add(new NumericDocValuesField("sortId", novel.getPid()));

			indexWriter.addDocument(doc);				
		}
		
		indexWriter.forceMerge(1);
		indexWriter.commit();
	}
	
	/**
	 * 添加索引
	 * @param novel
	 * @throws Exception
	 */
	public void addIndex(Novel novel) throws Exception {
		if(indexWriter == null)
			initIndexWriter(false);
		
		Document doc = new Document();
		doc.add(new IntField("pid", novel.getPid(), Store.YES));
		doc.add(new IntField("tid", novel.getTid(), Store.YES));
		doc.add(new IntField("aid", novel.getAid(), Store.YES));
		doc.add(new IntField("sid", novel.getSid(), Store.YES));
		doc.add(new TextField("p", novel.getParagraph(), Store.YES));
		doc.add(new TextField("s", novel.getSection(), Store.YES));
		doc.add(new TextField("a", novel.getArticle(), Store.YES));
		doc.add(new TextField("t", novel.getType(), Store.YES));
		
		doc.add(new TextField("ppid", novel.getPid() + "", Store.YES));  // 更新索引用
		
		doc.add(new NumericDocValuesField("sortId", novel.getPid()));
		
		indexWriter.addDocument(doc);
		indexWriter.forceMerge(1);
		indexWriter.commit();
	}
	
	/**
	 * 删除索引
	 * @param words
	 */
	public void deleteIndex(int pid) throws Exception {
		if(indexWriter == null)
			initIndexWriter(false);
		
		indexWriter.deleteDocuments(NumericRangeQuery.newIntRange("pid", pid, pid + 1, true, false));
		indexWriter.forceMerge(1);
		indexWriter.commit();
	}
	
	/**
	 * 更新索引
	 * @param id
	 * @param newSection
	 * @throws Exception
	 */
	public void updateIndex(Novel novel) throws Exception {
		if(indexWriter == null)
			initIndexWriter(false);
		
		Document doc = new Document();
		doc.add(new IntField("pid", novel.getPid(), Store.YES));
		doc.add(new IntField("tid", novel.getTid(), Store.YES));
		doc.add(new IntField("aid", novel.getAid(), Store.YES));
		doc.add(new IntField("sid", novel.getSid(), Store.YES));
		doc.add(new TextField("p", novel.getParagraph(), Store.YES));
		doc.add(new TextField("s", novel.getSection(), Store.YES));
		doc.add(new TextField("a", novel.getArticle(), Store.YES));
		doc.add(new TextField("t", novel.getType(), Store.YES));
		
		doc.add(new TextField("ppid", novel.getPid() + "", Store.YES));  // 更新索引用
		
		doc.add(new NumericDocValuesField("sortId", novel.getPid()));
		
		indexWriter.updateDocument(new Term("ppid", novel.getPid() + ""), doc);
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
		if(indexWriter != null)
			indexWriter.close();
	}
}
