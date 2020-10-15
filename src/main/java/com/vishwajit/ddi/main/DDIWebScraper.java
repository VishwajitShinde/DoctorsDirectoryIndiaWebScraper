package com.vishwajit.ddi.main;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.vishwajit.workers.DocInfoGetter;
import com.vishwajit.workers.DocURLWorker;

public class DDIWebScraper {

	static String baseURL = "http://www.doctorsdirectoryindia.com";
	public static void main(String[] args) {
		
		// Provide you Gecko driver path
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver-v0.27.0-win64\\geckodriver.exe");
		
		List<String> urls = prepareURLs();
		
		ConcurrentHashMap<String, HashSet<String>> doctorsUrlsMap = new ConcurrentHashMap<String, HashSet<String>>();
		Queue<String> failedURLsQueue = new ConcurrentLinkedQueue<String>();
		
		int index = 0;
		
		ExecutorService profileURLExecutor = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 498; i++) {
			
			DocURLWorker urlworker = new DocURLWorker(urls.get(i), doctorsUrlsMap);
			profileURLExecutor.execute(urlworker);
		}
		profileURLExecutor.shutdown();
		
		while (!profileURLExecutor.isTerminated()) {
			try {
				Thread.sleep(5000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Finished all URL Fetcher threads");
		
		 
		ExecutorService doctorInfoExecutor = Executors.newFixedThreadPool(50);
		for (String keyUrl : doctorsUrlsMap.keySet()) {
			for(String url : doctorsUrlsMap.get(keyUrl)){
			
				DocInfoGetter urlworker = new DocInfoGetter(url, failedURLsQueue);
				doctorInfoExecutor.execute(urlworker);
				
			}
		}
		doctorInfoExecutor.shutdown();
		
		while (!doctorInfoExecutor.isTerminated()) {
			try {
				Thread.sleep(5000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Finished all URL Fetcher threads");
		
		
		//If any URLs failed - need to scrap again for those URLs
		while(failedURLsQueue.size() > 0){
			System.out.println("GlobalQueue size " + failedURLsQueue.size()); 
			doctorInfoExecutor = Executors.newFixedThreadPool(50);
			for (int i = 0; i < failedURLsQueue.size(); i++) {
				String url = failedURLsQueue.poll();
				DocInfoGetter urlworker = new DocInfoGetter(url, failedURLsQueue);
				doctorInfoExecutor.execute(urlworker);
					
			}
			doctorInfoExecutor.shutdown();
			
			while (!doctorInfoExecutor.isTerminated()) {
				try {
					Thread.sleep(5000l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Finished all Global Queue URL Fetcher threads");
			System.out.println(failedURLsQueue);
			
		}	
	}

	private static List<String> prepareURLs() {
		List<String> urlList = new ArrayList<String>();
		String doctorURL = baseURL + "/doctor/index";
		urlList.add(doctorURL);
		int index = 12;
		String url = "";
		for(int i = 1; i < 498; i++) {
			index = 12 * i;
			url = doctorURL + "/" + index;
			urlList.add(url);
		}
		return urlList;
	}

}
