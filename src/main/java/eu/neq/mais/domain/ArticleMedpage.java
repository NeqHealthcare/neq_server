package eu.neq.mais.domain;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Date;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.net.URL;
import java.util.List;
import java.util.Iterator;



public class ArticleMedpage extends Article {

	
	private String title,guid,link,description;

	private Date pubDate;


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getGuid() {
		return guid;
	}


	public void setGuid(String guid) {
		this.guid = guid;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public Date getPubDate() {
		return pubDate;
	}


	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	 public static void main(String args[]) throws Exception {
		    String feed = "http://www.medpagetoday.com/rss/Cardiology.xml";

		    URL feedUrl = new URL(feed);

		    SyndFeedInput input = new SyndFeedInput();
		    SyndFeed sf = input.build(new XmlReader(feedUrl));

		    List entries = sf.getEntries();
		    Iterator it = entries.iterator();
		    while (it.hasNext()) {
		      SyndEntry entry = (SyndEntry)it.next();
		      System.out.println(entry.getTitle());
		      System.out.println(entry.getLink());
		      SyndContent description = entry.getDescription();
		      System.out.println(description.getValue());
		      System.out.println();
		    }
		    
	 }

	
}

