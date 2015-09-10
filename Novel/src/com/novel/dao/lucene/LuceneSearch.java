package com.novel.dao.lucene;

import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.novel.model.Novel;

@Component("luceneSearch")
public class LuceneSearch {

	private Analyzer analyzer;
	private Directory directory;
	private DirectoryReader ireader;
	private IndexSearcher isearcher;
	
	/**
	 * 直接查询
	 * @param words
	 * @param pageSize
	 * @param pageNow
	 * @return
	 * @throws Exception
	 */
	public List<Novel> search(String words, int pageSize, int pageNow) throws Exception {
		if(isearcher == null)
			initIndexSearcher();
		
		QueryParser parser = new QueryParser("p", analyzer);
		Query query = parser.parse(words);
		
		return resultHandle(query, pageNow, pageSize);
	}
	
	/**
	 * 限定tid查询
	 * @param words
	 * @param tid
	 * @param pageSize
	 * @param pageNow
	 * @return
	 * @throws Exception
	 */
	public List<Novel> searchType(String words, int tid, int pageSize, int pageNow) throws Exception {
		if(isearcher == null)
			initIndexSearcher();
		
		QueryParser parser1 = new QueryParser("p", analyzer);
		Query query1 = parser1.parse(words);
		Query query2 = NumericRangeQuery.newIntRange("tid", tid, tid + 1, true, false);
		
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, BooleanClause.Occur.MUST);
		booleanQuery.add(query2, BooleanClause.Occur.MUST);
		
		return resultHandle(booleanQuery, pageNow, pageSize);
	}
	
	/**
	 * 限定aid查询
	 * @param words
	 * @param aid
	 * @param pageSize
	 * @param pageNow
	 * @return
	 * @throws Exception
	 */
	public List<Novel> searchArticle(String words, int aid, int pageSize, int pageNow) throws Exception {
		if(isearcher == null)
			initIndexSearcher();
		
		QueryParser parser1 = new QueryParser("p", analyzer);
		Query query1 = parser1.parse(words);
		Query query2 = NumericRangeQuery.newIntRange("aid", aid, aid + 1, true, false);
		
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, BooleanClause.Occur.MUST);
		booleanQuery.add(query2, BooleanClause.Occur.MUST);
		
		return resultHandle(booleanQuery, pageNow, pageSize);
	}
	
	/**
	 * 限定sid查询
	 * @param words
	 * @param sid
	 * @param pageSize
	 * @param pageNow
	 * @return
	 * @throws Exception
	 */
	public List<Novel> searchSection(String words, int sid, int pageSize, int pageNow) throws Exception {
		if(isearcher == null)
			initIndexSearcher();
		
		QueryParser parser1 = new QueryParser("p", analyzer);
		Query query1 = parser1.parse(words);
		Query query2 = NumericRangeQuery.newIntRange("sid", sid, sid + 1, true, false);
		
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, BooleanClause.Occur.MUST);
		booleanQuery.add(query2, BooleanClause.Occur.MUST);
		
		return resultHandle(booleanQuery, pageNow, pageSize);
	}
	
	/**
	 * 对结果进行处理
	 * @param query
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	private List<Novel> resultHandle(Query query, int pageNow, int pageSize) throws Exception {
		Sort sort = new Sort(new SortField[]{SortField.FIELD_SCORE ,new SortField("sortId", SortField.Type.INT)});
		
		ScoreDoc[] hits = isearcher.search(query, pageSize * (pageNow + 1), sort).scoreDocs;
		
		List<Novel> result = new ArrayList<>();
		
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span>", "</span>");
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));  
		
		for (ScoreDoc scoreDoc : hits) {
			Document hitDoc = isearcher.doc(scoreDoc.doc);
			TokenStream tokenStream = analyzer.tokenStream("name", new StringReader(hitDoc.get("p")));
			String formattedP = highlighter.getBestFragment(tokenStream,  hitDoc.get("p"));
			
			Novel article = new Novel(Integer.parseInt(hitDoc.get("tid")), Integer.parseInt(hitDoc.get("aid")), 
					Integer.parseInt(hitDoc.get("sid")),Integer.parseInt(hitDoc.get("pid")), 
					hitDoc.get("t"), hitDoc.get("a"), hitDoc.get("s"), formattedP);
			result.add(article);
		}
		
		if(result.size() <= pageSize) {
			return result;
		}
		
		int endBound = (pageNow + 1) * pageSize > result.size() ? result.size() : (pageNow + 1) * pageSize;
		
		return result.subList(pageNow * pageSize, endBound);
	}
	
	/**
	 * 获取查询条数
	 * @param words
	 * @return
	 * @throws Exception
	 */
	public int count(String words) throws Exception {
		if(isearcher == null)
			initIndexSearcher();
		
		QueryParser parser = new QueryParser("p", analyzer);
		Query query = parser.parse(words);
		
		int total = isearcher.count(query);
		
		return total;
	}
	
	/**
	 * 根据tid获取查询条数
	 * @param words
	 * @param tid
	 * @return
	 * @throws Exception
	 */
	public int countType(String words, int tid) throws Exception {
		if(isearcher == null)
			initIndexSearcher();
		
		QueryParser parser = new QueryParser("p", analyzer);
		Query query1 = parser.parse(words);
		Query query2 = NumericRangeQuery.newIntRange("tid", tid, tid + 1, true, false);
		
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, BooleanClause.Occur.MUST);
		booleanQuery.add(query2, BooleanClause.Occur.MUST);
		
		int total = isearcher.count(booleanQuery);
		
		return total;
	}
	
	/**
	 * 根据aid获取查询条数
	 * @param words
	 * @param aid
	 * @return
	 * @throws Exception
	 */
	public int countArticle(String words, int aid) throws Exception {
		if(isearcher == null)
			initIndexSearcher();
		
		QueryParser parser = new QueryParser("p", analyzer);
		Query query1 = parser.parse(words);
		Query query2 = NumericRangeQuery.newIntRange("aid", aid, aid + 1, true, false);
		
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, BooleanClause.Occur.MUST);
		booleanQuery.add(query2, BooleanClause.Occur.MUST);
		
		int total = isearcher.count(booleanQuery);
		
		return total;
	}
	
	/**
	 * 根据sid获取查询条数
	 * @param words
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public int countSection(String words, int sid) throws Exception {
		if(isearcher == null)
			initIndexSearcher();
		
		QueryParser parser = new QueryParser("p", analyzer);
		Query query1 = parser.parse(words);
		Query query2 = NumericRangeQuery.newIntRange("sid", sid, sid + 1, true, false);
		
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, BooleanClause.Occur.MUST);
		booleanQuery.add(query2, BooleanClause.Occur.MUST);
		
		int total = isearcher.count(booleanQuery);
		
		return total;
	}
	
	/**
	 * 查询总条数
	 * @return
	 * @throws Exception
	 */
	public int regCount() throws Exception {
		if(isearcher == null)
			initIndexSearcher();
		
		Query query = new RegexpQuery(new Term("pid", "[0-9]+"));
		
		int total = isearcher.count(query);
		
		return total;
	}
	
	/**
	 * 初始化IndexSearcher
	 * @throws Exception
	 */
	public void initIndexSearcher() throws Exception {
		// mmseg4j impleAnalyzer complexAnalyzer maxWordAnalyzer MSegAnalyzer
		analyzer = new ComplexAnalyzer();
		directory = FSDirectory.open(FileSystems.getDefault().getPath("d:/", "Novel-resource", "lucene", "index", "novelIndex"));
		
		ireader = DirectoryReader.open(directory);
		
		isearcher = new IndexSearcher(ireader);
	}
	
	/**
	 * 关闭资源
	 * @throws Exception
	 */
	public void close() throws Exception {
		ireader.close();
		directory.close();
	}
	
}
