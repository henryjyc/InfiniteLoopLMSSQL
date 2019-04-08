package com.st.lmssql.models.dto;

import com.st.lmssql.models.tbl.*;

//dto class that contains 3 objects
//1. Book
//2. Author
//3. Pub
public class BkAuthPub {
	private Book book;
	private Author author;
	private Publisher publisher;
	
	public BkAuthPub(Book book, Author author, Publisher publisher) {
		super();
		this.book = book;
		this.author = author;
		this.publisher = publisher;
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
}
