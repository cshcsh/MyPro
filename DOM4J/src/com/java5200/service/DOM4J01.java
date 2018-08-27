package com.java5200.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class DOM4J01 {

	public static void main(String[] args) {
		Document document=DocumentHelper.createDocument();
		Element studentsElement=document.addElement("students");
		
		Element studentElement=studentsElement.addElement("student");
		studentElement.addAttribute("id", "007");
		
		Element name=studentElement.addElement("name");
		name.setText("sbbb");
		
		Element sex=studentElement.addElement("sex");
		sex.setText("男");
		
		Element age=studentElement.addElement("age");
		age.setText("22");
		
		OutputFormat format=OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		try {
			XMLWriter xmlWriter=new XMLWriter(new FileOutputStream("src/students3.xml"), format);
			try {
				xmlWriter.write(document);
				xmlWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
