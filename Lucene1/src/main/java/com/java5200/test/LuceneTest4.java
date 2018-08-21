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
	private String citys[]={"�ൺ","�Ͼ�","�Ϻ�"};
	private String descs[]={
			"�ൺ��һ�������ĳ��С�",
			"�Ͼ���һ�����Ļ��ĳ��С��Ͼ���һ���Ļ��ĳ����Ͼ�����������ǽ���ʡ�ᣬ�ش��й������������������Σ�����������ȫ����Ͻ11�����������6597ƽ�����2013�꽨�������752.83ƽ�������ס�˿�818.78�����г����˿�659.1���ˡ�[1-4] �����ϼ����أ���������ݡ����Ͼ�ӵ����6000��������ʷ����2600�꽨��ʷ�ͽ�500��Ľ���ʷ�����й��Ĵ�Ŷ�֮һ���С������Ŷ�������ʮ�����ᡱ֮�ƣ����л���������Ҫ����أ���ʷ�������α��ӻ���֮��˷���������й��Ϸ������Ρ����á��Ļ����ģ�ӵ�к��ص��Ļ����̺ͷḻ����ʷ�Ŵ档[5-7] �Ͼ��ǹ�����Ҫ�Ŀƽ����ģ��Թ���������һ�������ؽ̵ĳ��У��С��������ࡱ�������ϵ�һѧ��������������2013�꣬�Ͼ��иߵ�ԺУ75��������211��У8���������ڱ����Ϻ��������ص�ʵ����25���������ص�ѧ��169������ԺԺʿ83�ˣ������й�������[8-10] ��",
			"�Ϻ���һ�������ĳ��С�"
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
		String q="�Ͼ��Ļ�";
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
	
	//��ȡIndexWriterʵ��
	public IndexWriter getWriter()throws Exception{
		//Analyzer analyzer=new StandardAnalyzer();
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(directory, config);
		return writer;
	}

}
