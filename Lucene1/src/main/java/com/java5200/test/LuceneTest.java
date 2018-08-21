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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LuceneTest {
	
	private String ids[]={"1","2","3"};
	private String citys[]={"qingdao","nanjing","shanghai"};
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
			document.add(new TextField("id", descs[i], Field.Store.NO));
			writer.addDocument(document);
		}
		writer.close();
	}
	

	@Test
	public final void testWriter() throws Exception {
		writer=getWriter();
		System.out.println("写了 "+writer.numDocs()+" 个文档");
		//writer.close();
	}
	
	@Test
	public final void testReader() throws Exception {
		DirectoryReader reader=DirectoryReader.open(directory);
		System.out.println("最大文档数："+reader.maxDoc());
		System.out.println("实际文档数："+reader.numDocs());
	}
	
	@Test
	public final void testDeleteAfterMerge() throws Exception {
		writer=getWriter();
		System.out.println("删除前："+writer.numDocs());
		writer.deleteDocuments(new Term("id", "1"));
		writer.forceMergeDeletes();
		writer.commit();
		System.out.println("删除后："+writer.numDocs());
	}
	
	@Test
	public final void testUpdate() throws Exception {
		writer=getWriter();
		Document document=new Document();
		document.add(new StringField("id", "1", Field.Store.YES));
		document.add(new StringField("city", "qingdao", Field.Store.YES));
		document.add(new TextField("desc", "dsss is a city", Field.Store.NO));
		writer.updateDocument(new Term("id", "1"), document);
		System.out.println(writer.numDocs());
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
