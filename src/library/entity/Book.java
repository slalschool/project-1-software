package library.entity;

public class Book implements java.io.Serializable{
	
	private String bookId;
	private String title;
	private String author;
	private String isbnNumber;
	private String subject;
	private String publisher;
	private int copies;

	public Book(String bookId, String title, String author, String isbnNumber, String subject, String publisher) {
		
		this.setBookId(bookId);
		this.setTitle(title);
		this.setAuthor(author);
		this.setIsbnNumber(isbnNumber);
		this.setSubject(subject);
		this.setPublisher(publisher);
		this.setCopies(2);
	}
	
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}	
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbnNumber() {
		return isbnNumber;
	}

	public void setIsbnNumber(String isbnNumber) {
		this.isbnNumber = isbnNumber;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

}
