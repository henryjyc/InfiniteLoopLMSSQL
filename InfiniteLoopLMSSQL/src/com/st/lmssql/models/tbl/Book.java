package com.st.lmssql.models.tbl;

public class Book {
	private Integer bookId;
	private String title;
	private Integer authorId;
	private Integer pubId;
	
	public Book() {
	}
	
	public Book(Integer bookId, String title, Integer authoId, Integer pubId) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.authorId = authoId;
		this.pubId = pubId;
	}
	
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthoId(Integer authorId) {
		this.authorId = authorId;
	}
	public Integer getPubId() {
		return pubId;
	}
	public void setPubId(Integer pubId) {
		this.pubId = pubId;
	}
	
	public String toString() {
		String output = "";
		
		return output;
	}
}