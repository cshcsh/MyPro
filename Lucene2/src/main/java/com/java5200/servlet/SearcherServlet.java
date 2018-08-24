package com.java5200.servlet;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearcherServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Directory directory=FSDirectory.open(Paths.get("E:\\Lucene5"));/usr/local/java/lucene5
		Directory directory=FSDirectory.open(Paths.get("/usr/local/java/lucene5"));
		DirectoryReader reader=DirectoryReader.open(directory);
		IndexSearcher searcher=new IndexSearcher(reader);
		
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		QueryParser parser=new QueryParser("desc", analyzer);
		String keyWord=req.getParameter("keyWord");
		if("".equals(keyWord)||keyWord==null){
			req.setAttribute("message5", "关键字为空或不存在");
		}else{
			Query query = null;
			try {
				query = parser.parse(keyWord);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			TopDocs hits=searcher.search(query, 10);
			
			QueryScorer scorer=new QueryScorer(query);
			Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
			SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
			Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
			highlighter.setTextFragmenter(fragmenter);
			
			StringBuffer stringBuffer=new StringBuffer();
			for(ScoreDoc scoreDoc:hits.scoreDocs){
				
				Document doc=searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("city"));
				System.out.println(doc.get("desc"));
				String desc=doc.get("desc");
				
				if(desc!=null){
					TokenStream tokenStream=analyzer.tokenStream("desc", new StringReader(desc));
					try {
						stringBuffer.append(highlighter.getBestFragment(tokenStream, desc)+"<br>");
					} catch (InvalidTokenOffsetsException e) {
						e.printStackTrace();
					}
					//System.out.println(string);
				}
			}
			reader.close();
			req.setAttribute("message4", stringBuffer.toString());
			req.setAttribute("message3", "关键字 "+keyWord+" 查询到了 "+hits.totalHits+" 个");
		}
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

}
