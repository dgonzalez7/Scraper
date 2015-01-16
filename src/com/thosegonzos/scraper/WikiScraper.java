package com.thosegonzos.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.net.*;
import java.io.*;
// import java.util.Random;

public class WikiScraper {
	
	// private static Random generator;
	
	public static void main(String[] args) 
	{
		// generator = new Random(31415926);
		int startPage = 1;
		if (args.length != 0)
		{
			startPage = Integer.parseInt(args[0]);
		}
		
		scrapeBGG(startPage);
	}
	
	public static void scrapeBGG(int startPage)
	{
		File htmlOut = new File("data/html.txt");
		File bggParsed = new File("data/BGG.txt");
		String bgg = "http://boardgamegeek.com/browse/boardgame/page/";
		// String page = "/page/1";
		
		System.out.println("***********" + bgg + startPage);
		String html = getUrl(bgg + 1);
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
		
		// Get last page number
		Element lastPage = doc.select("p > a").last();
		System.out.println(lastPage.text());
		int lastPageInt = Integer.parseInt(lastPage.text().substring(1, 4));
		System.out.println("Int: " + lastPageInt);
		
		try
		{
			if (!htmlOut.exists()) 
			{
				bggParsed.createNewFile();
			}
			FileWriter fw = new FileWriter(bggParsed);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int p = startPage; p <= lastPageInt; p++)
			{
				System.out.println("***********" + bgg + p);
				html = getUrl(bgg + p);
				doc = Jsoup.parse(html);

				for (int i = 1; doc.select("div#results_objectname" + i + " > a").first() != null;i++)
				{
					// Get rank
					Elements ranks = doc.select("td.collection_rank a");
					Element rank = ranks.get(i - 1);
					String gameRank = rank.attr("name");
					System.out.println(gameRank);

					// Get name and link
					Element findGridStart = doc.select("div#results_objectname" + i + " > a").first();
					// System.out.println(findGridStart);
					String gameName = findGridStart.text();
					String gameLink = findGridStart.attr("href");
					System.out.println(gameName);
					// System.out.println(gameLink);

					// Get rankings
					Element geekRating = doc.select("td.collection_bggrating").get((i - 1) * 3);
					String gameGeekRating = geekRating.text();
					// System.out.println(gameGeekRating);
					// System.out.println(geekRating);
					Element avgRating = doc.select("td.collection_bggrating").get((3 * i) - 2);
					String gameAvgRating = avgRating.text();
					// System.out.println(gameAvgRating);
					// System.out.println(avgRating);
					Element numVoters = doc.select("td.collection_bggrating").get((i * 3) - 1);
					String gameNumVoters = numVoters.text();
					// System.out.println(gameNumVoters);
					// System.out.println(numVoters);

					// Get price
					// ****
					
					// Get data from item detail page
					ItemPage ip = new ItemPage(gameLink);
					// System.out.println(ip.itemYearPublished);
					// System.out.println(ip.itemNumPlayers);
					// System.out.println(ip.itemPlayerAges);
					// System.out.println(ip.itemPlayingTime);
					
					// System.out.println("Stop here!");
					
					// Save Data
					String row = gameRank + ", " + gameName + ", " 
							+ gameLink + ", " + gameGeekRating + ", "
							+ gameAvgRating + ", " + gameNumVoters + ", "
							+ ip.itemYearPublished + ", " + ip.itemNumPlayers + ", "
							+ ip.itemBestNumPlayers + ", "
							+ ip.itemPlayerAges + ", " + ip.itemPlayingTime;
					bw.write(row);
					bw.newLine();
                    // bw.newLine();
					
					// Pause for good luck
					try
					{
						Thread.sleep(500);
					}
					catch (InterruptedException e)
					{
						Thread.currentThread().interrupt();
					}
				}
			}
            bw.close();
		}
		catch (IOException e)
		{
			System.out.println("ERROR!!!");
			e.printStackTrace();
		}
		
        
		// System.out.println(findTEST.firstElementSibling());
		/*
		
		Elements links = doc.select("#mw-content-text [href~=^/wiki/((?!:).)*$]");

		if(links.size() == 0){
			System.out.println("No links found at "+url+". Going back to Java!");
			scrapeTopic("wiki/Java");
		}
		int r = generator.nextInt(links.size());
		System.out.println("Random link is: "+links.get(r).text()+" at url: "+links.get(r).attr("href"));
		scrapeTopic(links.get(r).attr("href"));
		*/
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
			in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String line = "";

			while((line = in.readLine()) != null)
			{
				outputText += line;
			}
			in.close();
		}catch(IOException e){
			System.out.println("There was an error connecting to the URL");
			return "";
		}
		return outputText;
	}
} 
