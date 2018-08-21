package com.java5200.test;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
	
	public static void search(String indexDir,String q)throws Exception{
		
		Directory directory=FSDirectory.open(Paths.get(indexDir));
		DirectoryReader reader=DirectoryReader.open(directory);
		IndexSearcher searcher=new IndexSearcher(reader);
		Analyzer analyzer=new StandardAnalyzer();
		QueryParser parser=new QueryParser("content", analyzer);
		Query query=parser.parse(q);
		TopDocs topDocs=searcher.search(query, 10);
		System.out.println("查询到了 "+topDocs.totalHits+" 个");
		for(ScoreDoc s:topDocs.scoreDocs){
			Document document=searcher.doc(s.doc);
			System.out.println(document.get("filePath"));
		}
	}

	public static void main(String[] args)throws Exception {

		String indexDir="E:\\lucene";
		String q="Zygmunt Saloni";
		search(indexDir,q);
	}

}
