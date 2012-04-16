package eu.neq.mais.domain;

import java.util.Date;

public class Article implements Comparable<Article> {

	private String title,link,description;
	
	private Long pubDate;
	
	public Article(String title,String link, String description,Date date){
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


	public Long getPubDate() {
		return pubDate;
	}


	public void setPubDate(Long pubDate) {
		this.pubDate = pubDate;
	}

	
	public int compareTo(Appointment o) {
		return 0;
	}
	

	@Override
	public int compareTo(Article o) {
		Article g = (Article) o;
	    return (this.getPubDate()).compareTo(g.getPubDate());
	}

}
