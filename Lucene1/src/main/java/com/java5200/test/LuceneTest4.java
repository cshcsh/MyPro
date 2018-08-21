package com.java5200.test;


import java.io.StringReader;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
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
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.NumericUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LuceneTest4 {
	
	private String ids[]={"1","2","3"};
	private String citys[]={"青岛","南京","上海"};
	private String descs[]={
			"青岛是一个美丽的城市。",
			"南京是一个有文化的城市。南京是一个文化的城市南京，简称宁，是江苏省会，地处中国东部地区，长江下游，濒江近海。全市下辖11个区，总面积6597平方公里，2013年建成区面积752.83平方公里，常住人口818.78万，其中城镇人口659.1万人。[1-4] “江南佳丽地，金陵帝王州”，南京拥有着6000多年文明史、近2600年建城史和近500年的建都史，是中国四大古都之一，有“六朝古都”、“十朝都会”之称，是中华文明的重要发祥地，历史上曾数次庇佑华夏之正朔，长期是中国南方的政治、经济、文化中心，拥有厚重的文化底蕴和丰富的历史遗存。[5-7] 南京是国家重要的科教中心，自古以来就是一座崇文重教的城市，有“天下文枢”、“东南第一学”的美誉。截至2013年，南京有高等院校75所，其中211高校8所，仅次于北京上海；国家重点实验室25所、国家重点学科169个、两院院士83人，均居中国第三。[8-10] 。",
			"上海是一个繁华的城市。"
	};
	
	private Directory directory;
	private IndexWriter writer;
	
	@Before
	public void setUp() throws Exception {
		directory=FSDirectory.open(Paths.get("E:\\Lucene4"));
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
	public final void search() throws Exception {
		String indexDir="E:\\Lucene4";
		String q="南京文化";
		//directory=FSDirectory.open(Paths.get(indexDir));
		DirectoryReader reader=DirectoryReader.open(directory);
		IndexSearcher searcher=new IndexSearcher(reader);
		
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		QueryParser parser=new QueryParser("desc", analyzer);
		Query query=parser.parse(q);
		TopDocs hits=searcher.search(query, 10);
		
		QueryScorer scorer=new QueryScorer(query);
		Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
		SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
		Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);
		
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			
			Document doc=searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("city"));
			//System.out.println(doc.get("desc"));
			String desc=doc.get("desc");
			if(desc!=null){
				TokenStream tokenStream=analyzer.tokenStream("desc", new StringReader(desc));
				System.out.println(highlighter.getBestFragment(tokenStream, desc));
			}
		}
		reader.close();
	}
	
	
	@After
	public void tearDown() throws Exception{
		writer.close();
	}
	
	//获取IndexWriter实例
	public IndexWriter getWriter()throws Exception{
		//Analyzer analyzer=new StandardAnalyzer();
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(directory, config);
		return writer;
	}

}
