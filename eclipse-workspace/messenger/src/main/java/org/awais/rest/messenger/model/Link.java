package org.awais.rest.messenger.model;

// Not xmlRootElement because it is going to be a member attribute of message class.
public class Link {
	private String link;
	private String rel;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	
}
