package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

public class LonelyTweetModel {
	private String text;
	private Date timestamp;
	public String getText() {
		return text;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public LonelyTweetModel(String text) {
		super();
		this.text = text;
		timestamp = new Date();
	}
	
	public LonelyTweetModel(String text, Date timestamp) {
		super();
		this.text = text;
		this.timestamp = timestamp;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	//public abstract Date getTimestamp();
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
