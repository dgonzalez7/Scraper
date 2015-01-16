package com.thosegonzos.scraper;

import java.io.*;
import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ItemPage {
	
	public String itemYearPublished;
	public String itemNumPlayers;
	public String itemPlayerAges;
	public String itemPlayingTime;
	public String itemBestNumPlayers;

	public ItemPage(String detailURL) 
	{
		File htmlOut = new File("data/detailhtml.txt");
		// File bggParsed = new File("data/BGG.txt");
		String bgg = "http://boardgamegeek.com";
		// String page = "/page/1";
		
		
		System.out.println("URL Scraping: " + bgg + detailURL);
		String html = getUrl(bgg + detailURL);
		Document doc = Jsoup.parse(html);
			
		
		/*
		// print all HTML
		System.out.println(doc);
		
		try
		{
			if (!htmlOut.exists()) {
				htmlOut.createNewFile();
			}
 
			FileWriter fw = new FileWriter(htmlOut);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(doc.toString());
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		// Element yearPublished = doc.select("div#results_yearpublished_thing_12333").first();
		// Element yearPublished = doc.select("div[^status_yearpublished_thing]").first();
		Element yearPublished = doc.select("div#edit_yearpublished").first();
		itemYearPublished = yearPublished.text();
        // System.out.println(itemYearPublished);
        
		Element numPlayers = doc.select("div#edit_players").first();
		itemNumPlayers = numPlayers.text();
        // System.out.println(itemNumPlayers);
		
		Element bestNumPlayers = doc.select("table.geekitem_infotable").select("tr").first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().select("div").first();
		itemBestNumPlayers = bestNumPlayers.text();
		// Element bestNumPlayers = table.get(5);
		// System.out.println("*****");
		// System.out.println(itemBestNumPlayers);
		// System.out.println("*****");
		
		Element playerAges = doc.select("div#edit_minage").first();
		itemPlayerAges = playerAges.text();
        // System.out.println(itemPlayerAges);
        
		Element playingTime = doc.select("div#edit_playingtime").first();
		itemPlayingTime = playingTime.text();
        // System.out.println(itemPlayingTime);
	}
	
	public static String getUrl(String url){
		URL urlObj = null;
		try{
			urlObj = new URL(url);
		}catch(MalformedURLException e){
			System.out.println("The url was malformed!");
			return "";
		}
		URLConnection urlCon = null;
		BufferedReader in = null;
		String outputText = "";
		try{
			urlCon = urlObj.openConnection();
			urlCon.setConnectTimeout(15000);
			urlCon.setReadTimeout(15000);
			in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String line = "";

			while((line = in.readLine()) != null)
			{
				outputText += line;
			}
			in.close();
		}catch(IOException e){
			System.out.println("Error in ItemPage");
			System.out.println("There was an error connecting to the URL");
			return "";
		}
		return outputText;
	}
}
