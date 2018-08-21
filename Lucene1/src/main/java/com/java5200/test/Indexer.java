package com.java5200.test;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	
	private IndexWriter indexWriter;//д����ʵ��
	
	//ʵ����indexWriter
	public Indexer(String indexDir)throws Exception{
		Directory dir=FSDirectory.open(Paths.get(indexDir));
		Analyzer analyzer=new StandardAnalyzer();
		IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);
		indexWriter=new IndexWriter(dir, indexWriterConfig);
	}
	
	//�ر�indexWriter
	public void close()throws Exception{
		indexWriter.close();
	}
	
	//����ָ��Ŀ¼�µ�ȫ���ļ�
	public int index(String dataDir)throws Exception {
		File[] files=new File(dataDir).listFiles();
		for(File f:files){
			indexFile(f);
		}
		return indexWriter.numDocs();
	}

	//����ָ���ĵ�
	public void indexFile(File f)throws Exception {
		System.out.println("�����ĵ���"+f.getCanonicalPath());
		Document document=getDocument(f);
		indexWriter.addDocument(document);
	}

	//��ȡ�ĵ�
	public Document getDocument(File f)throws Exception {
		Document doc=new Document();
		doc.add(new TextField("content", new FileReader(f)));
		doc.add(new TextField("fileName", f.getName(), Field.Store.YES));
		doc.add(new TextField("filePath", f.getCanonicalPath(),Field.Store.YES));
		return doc;
	}

	public static void main(String[] args)throws Exception {

		String indexDir="E:\\Lucene";
		String dataDir="E:\\Lucene\\data";
		
		Indexer indexer=new Indexer(indexDir);
		long start=System.currentTimeMillis();
		int numIndexed=indexer.index(dataDir);
		long end=System.currentTimeMillis();
		System.out.println("������ "+numIndexed+" ���ļ�,������ "+(end-start)+" ����");
		indexer.close();
	}

}
