package com.java5200.test;


import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LuceneTest2 {
	
	private String ids[]={"1","2","3","4"};
	private String authors[]={"Jack","Marry","John","Json"};
	private String positions[]={"accounting","technician","salesperson","boss"};
	private String titles[]={"Java is a good language.","Java is a cross platform language","Java powerful","You should learn java"};
	private String contents[]={
			"If possible, use the same JRE major version at both index and search time.",
			"When upgrading to a different JRE major version, consider re-indexing. ",
			"Different JRE major versions may implement different versions of Unicode,",
			"For example: with Java 1.4, `LetterTokenizer` will split around the character U+02C6,"
	};
	
	private Directory directory;
	private IndexWriter writer;
	
	@Before
	public void setUp() throws Exception {
		directory=FSDirectory.open(Paths.get("E:\\Lucene3"));
		writer=getWriter();
		for (int i = 0; i < ids.length; i++) {
			Document document=new Document();
			document.add(new StringField("id", ids[i], Field.Store.YES));
			document.add(new StringField("author", authors[i], Field.Store.YES));
			document.add(new StringField("position", positions[i], Field.Store.YES));
			TextField field=new TextField("title", titles[i], Field.Store.NO);
			/*if("boss".equals(positions[i])){
				field.setIntValue(100);
			}*/
			document.add(field);
			document.add(new TextField("content", contents[i], Field.Store.NO));
			writer.addDocument(document);
		}
		writer.close();
	}
	

	@Test
	public void search() throws Exception {
		directory=FSDirectory.open(Paths.get("E:\\Lucene3"));
		DirectoryReader reader=DirectoryReader.open(directory);
		IndexSearcher searcher=new IndexSearcher(reader);
		String searchField="title";
		String qString="java";
		Term term=new Term(searchField, qString);
		Query query=new TermQuery(term);
		TopDocs topDocs=searcher.search(query, 10);
		System.out.println("查询到了 "+topDocs.totalHits+" 个文档");
		for(ScoreDoc scoreDoc:topDocs.scoreDocs){
			Document document=searcher.doc(scoreDoc.doc);
			System.out.println(document.get("author"));
		}
	}
	
	@Test
	public void search2() throws Exception {
		directory=FSDirectory.open(Paths.get("E:\\Lucene3"));
		DirectoryReader reader=DirectoryReader.open(directory);
		IndexSearcher searcher=new IndexSearcher(reader);
		Analyzer analyzer=new StandardAnalyzer();
		String searchField="title";
		String qString="Marry";
		QueryParser queryParser=new QueryParser(searchField, analyzer);
		Query query=queryParser.parse(qString);
		TopDocs topDocs=searcher.search(query, 10);
		for(ScoreDoc scoreDoc:topDocs.scoreDocs){
			
			Document document=searcher.doc(scoreDoc.doc);
			System.out.println(document.get("author"));
		}
		System.out.println("test");
	}

	
	@After
	public void tearDown() throws Exception{
		writer.close();
	}
	
	//获取IndexWriter实例
	public IndexWriter getWriter()throws Exception{
		Analyzer analyzer=new StandardAnalyzer();
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(directory, config);
		return writer;
	}

}
