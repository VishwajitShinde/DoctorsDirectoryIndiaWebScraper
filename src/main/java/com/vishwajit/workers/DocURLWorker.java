package com.vishwajit.workers;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DocURLWorker implements Runnable {

	private String url; 
	private ConcurrentHashMap<String, HashSet<String>> urlsMap;
	
	public DocURLWorker(String url, ConcurrentHashMap<String, HashSet<String>> urlsMap){
		this.url = url;
		this.urlsMap = urlsMap;
	}
	
	public void run() {

	     try {
			Document doc = Jsoup.connect(url).get();
			Elements elems = doc.select("a.cbp-caption");
			HashSet<String> docsUrl = new HashSet<String>();
			
			for (Element ele : elems) {
				
				String docUrl = ele.attr("href");
				docsUrl.add(docUrl);

				//System.out.println("DocUrl: " + docUrl);
			}

			urlsMap.putIfAbsent(url, docsUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
