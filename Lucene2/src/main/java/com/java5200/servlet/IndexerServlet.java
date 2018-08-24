package com.java5200.servlet;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexerServlet extends HttpServlet {

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
		// 获取IndexWriter实例
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		//Directory directory = FSDirectory.open(Paths.get("E:\\Lucene5"));/usr/local/java/lucene5
		Directory directory = FSDirectory.open(Paths.get("/usr/local/java/lucene5"));
		IndexWriter writer = new IndexWriter(directory, config);
		Document document=new Document();
		String city = req.getParameter("city");
		String desc = req.getParameter("desc");
		if("".equals(city)||"".equals(desc)){
			req.setAttribute("message1", "城市或描述为空");
			req.setAttribute("message2", "生成索引失败");
		}else{
			document.add(new StringField("city", city, Field.Store.YES));
			document.add(new TextField("desc", desc, Field.Store.YES));
			writer.addDocument(document);
			req.setAttribute("message2", "生成索引成功");
		}
		writer.close();
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

}
