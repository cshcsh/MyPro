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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.NumericUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LuceneTest3 {
	
	private String ids[]={"1","2","3"};
	private String citys[]={"qingdao","nanjing","ahanghai"};
	private String descs[]={
			"Qingdao is a beautiful city.",
			"Nanjing is a city of culture.",
			"Shanghai is a bustling city."
	};
	
	private Directory directory;
	private IndexWriter writer;
	
	@Before
	public void setUp() throws Exception {
		directory=FSDirectory.open(Paths.get("E:\\Lucene2"));
		writer=getWriter();
		for (int i = 0; i < ids.length; i++) {
			Document document=new Document();
			document.add(new StringField("id", ids[i], Field.Store.YES));
			document.add(new StringField("city", citys[i], Field.Store.YES));
			document.add(new TextField("desc", descs[i], Field.Store.YES));
			writer.addDocument(document);
		}
		writer.close();
	}
	

	@Test
	public final void testBooleanQuery() throws Exception {
		directory=FSDirectory.open(Paths.get("E:\\Lucene2"));
		DirectoryReader reader=DirectoryReader.open(directory);
		IndexSearcher searcher=new IndexSearcher(reader);
		PrefixQuery query2=new PrefixQuery(new Term("city","a"));
		TopDocs hits=searcher.search(query2, 10);
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			Document doc=searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("id"));
			System.out.println(doc.get("city"));
			System.out.println(doc.get("desc"));
		}
	}
	
	
	@After
	public void tearDown() throws Exception{
		writer.close();
	}
	
	//»ñÈ¡IndexWriterÊµÀý
	public IndexWriter getWriter()throws Exception{
		Analyzer analyzer=new StandardAnalyzer();
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(directory, config);
		return writer;
	}

}
