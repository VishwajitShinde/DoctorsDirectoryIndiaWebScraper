package com.vishwajit.workers;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vishwajit.ddi.entity.DoctorInfo;

public class DocInfoGetter implements Runnable {

	DoctorInfo docInfo;
	Queue<String> globalQueue;
	String url = "";
	static int index;
	
	
	public DocInfoGetter(String url, Queue<String> globalQueue){
		this.url = url;
		this.globalQueue = globalQueue;
	}
	
	
	public void run() {
	     scrap();
	}


	private void scrap() {
		String name = "";
		String img = "";
		String gender = "";
		String location = "";
		String phone = "";
		String mailId = "";
		String website = "";
		String degree = "";
		String specialization = "";
		String desc = "";
		
		try {
			Document doc = Jsoup.connect(url).get();
			
			Element nameElem = doc.select("div.breadcrumb-content").first();
			name = nameElem.select("h3").textNodes().get(0).text().trim();
			
			Element degreeElem = nameElem.select("h3 span").first();
			degree = degreeElem.text().trim();
			degree = ((degree == null | degree.equals("")) && !(degree.length() >= 2)) ? "" : degree.substring(2, degree.length());
			degree = degree.trim();
			
			specialization = doc.select("div.breadcrumb-content h6").first().text();
			
			Elements contElems = doc.select("div.sidebar-content ul.cbp-l-project-details-list li");
			
			location = contElems.get(0).text().replaceAll("Location ", "").replaceAll("Location", "").trim();
			
			phone = contElems.get(1).text().replaceAll("Phone ", "").replaceAll("Phone", "").trim();
			phone = phone == null | phone.equals("") ? "" : phone;
			phone = phone.replaceAll(" ", ", ");
			
			mailId = contElems.get(3).text().replaceAll("Email ", "").replaceAll("Email", "").trim();
			mailId = mailId == null | mailId.equals("") ? "" : mailId;
			mailId = mailId.replaceAll(" ", ", ");
			
			website = contElems.get(4).text().replaceAll("Web Site ", "").replaceAll("Web Site", "").trim();
			website = website == null | website.equals("") ? "" : website;
			website = website.replaceAll(" ", ", ");

			
			Elements qualiElems = doc.select("ul.qualification-list li");
			gender = qualiElems.get(2).text().replaceAll("Gender ", "").trim();
			
			
			
			DoctorInfo dInfo = new DoctorInfo(++index, name, gender, location, phone, mailId, website, degree, specialization, desc);
			
//			  System.out.println(contElems);
//			  
//			  
//			  System.out.println(name); 
//			  System.out.println(degree);
//			  System.out.println(specialization); 
//			  System.out.println(location);
//			  
//			  System.out.println(phone); System.out.println(mailId);
//			  System.out.println(website); System.out.println(gender);
//			  
//			  System.out.println(desc);
//			 			
			System.out.println(dInfo);
			
		} catch (IOException e) {
			//e.printStackTrace();
			//System.out.println("Error: " + url);
			globalQueue.add(url);
		}
	}

	

	
	public static void main(String[] args) {
		Queue<String> globalQueue = new ConcurrentLinkedQueue<String>();
		DocInfoGetter obj = new DocInfoGetter("http://www.doctorsdirectoryindia.com/doctors/9365/dr.-alkesh-shah-dental-surgeon-orthodontist---", globalQueue);
		//obj = new DocInfoGetter("http://www.doctorsdirectoryindia.com/doctors/9393/dr.-anish-kulkarni-dental-surgeon-maharashtra-pune-411045");
		obj = new DocInfoGetter("", globalQueue);
		//obj.scrap();
		
		String[] urls = {"http://www.doctorsdirectoryindia.com/doctors/9291/best-medical-oncologist-in-bangalore-%7C-dr.-kishore-kumar--karnataka-bengaluru-560038",
				"http://www.doctorsdirectoryindia.com/doctors/9203/dr.-tapasya-mundhra%C2%A0-accupressure-cardiologist-obesity-consultant-occupational-therapist-physician-delhi-new%20delhi-110013",
				"http://www.doctorsdirectoryindia.com/doctors/9131/dr.-suman-cps---orthopedic-surgeon-%7C-ortho-doctor-in-coimbatore-orthopaedic-surgeon-tamil"};
		
		for(String url : urls) {
			
			try {
				obj.url = url;
				obj.scrap();	
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
