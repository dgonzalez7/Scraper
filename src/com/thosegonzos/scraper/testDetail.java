package com.thosegonzos.scraper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class testDetail {

	public static void main(String[] args) throws FileNotFoundException 
	{
		// File htmlOut = new File("data/detailhtml.txt");
		// File bggParsed = new File("data/BGG.txt");
		// String bgg = "http://boardgamegeek.com";
		// String page = "/page/1";
		String filePath = "data/detailHTMLSAVED.txt";

		@SuppressWarnings("resource")
		String html = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
		
        // System.out.println(html);
		
		Document doc = Jsoup.parse(html);
		
		Elements found = doc.select("a[href^=/boardgamecategory]");
		System.out.println(found);
		
		String categoryString = "";
		if (found.size() != 0)
		{
			for (int i = 0;i < found.size();i++)
			{
				Element category = found.get(i);
				categoryString += "<(" + category.text() + ")>";
			}
		}
		System.out.println(categoryString);
		
		Elements subdomains = doc.select("a[href^=/boardgamesubdomain]");
		System.out.println(subdomains);
		
		String subdomainString = "";
		if (subdomains.size() != 0)
		{
			for (int i = 0;i < subdomains.size();i++)
			{
				Element subdomain = subdomains.get(i);
				subdomainString += "<(" + subdomain.text()  + ")>";
			}
		}
		System.out.println(subdomainString);
		
		Elements mechanics = doc.select("a[href^=/boardgamemechanic]");
		System.out.println(mechanics);
		
		String mechanicString = "";
		if (mechanics.size() != 0)
		{
			for (int i = 0;i < mechanics.size();i++)
			{
				Element mechanic = mechanics.get(i);
				mechanicString += "<(" + mechanic.text()  + ")>";
			}
		}
		System.out.println(mechanicString);
	}
}


