package eu.neq.mais.domain;

import java.util.Date;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.net.URL;
import java.util.List;
import java.util.Iterator;



public class ArticleMedpage extends Article {

	
	private String title,link,description,content;
	
	private long pubDate;


	public ArticleMedpage(String title,String link, String description,Date date){
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = date.getTime();
	}


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


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public long getPubDate() {
		return pubDate;
	}


	public void setPubDate(long pubDate) {
		this.pubDate = pubDate;
	}
	
	 public static void main(String args[]) throws Exception {

		    URL feedUrl = new URL("http://www.medpagetoday.com/rss/Cardiology.xml");

		    SyndFeedInput input = new SyndFeedInput();
		    SyndFeed sf = input.build(new XmlReader(feedUrl));

		    List entries = sf.getEntries();
		    Iterator it = entries.iterator();
		    while (it.hasNext()) {
		      SyndEntry entry = (SyndEntry)it.next();
		      System.out.println(entry.getTitle());
		      System.out.println(entry.getLink());
		      System.out.println(entry.getPublishedDate());
		      SyndContent description = entry.getDescription();
		      System.out.println(description.getValue());
		      System.out.println();
		    }
		    
	 }


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	
}

