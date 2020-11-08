package library.process;
import java.util.HashMap;
import java.util.ArrayList;
import library.entity.*;

public class LibraryData {
	
	private static HashMap<String, User> users = new HashMap();
	private static HashMap<String, Book> books = new HashMap();
	
	public LibraryData() {
		// TODO Auto-generated constructor stub
	}
	
	public static void initiatlizeData() {
		users.put("sidlal", new User("slal", "Siddharth", "Lal", "password", "CRD01", false));
		users.put("jsnli", new User("jli", "Jason", "Li", "password", "CRD02", false));
		users.put("admin", new User("admin", "sys", "Administrator", "welcome234", "ADMCARD", true));

		books.put("LIB003", new Book("LIB003", "Harry Potter and the Chamber of Secrets", "J.K. Rowling", "ISBN003", "Fantasy", "MacMillian"));
		books.put("LIB004", new Book("LIB004", "Harry Potter and the Prisoner of Azkhaban", "J.K. Rowling", "ISBN004", "Fantasy", "MacMillian"));
		books.put("LIB005", new Book("LIB005", "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "ISBN005", "Fantasy", "MacMillian"));
		books.put("LIB006", new Book("LIB006", "The Fellowship of the Ring", "J.R.R. Tolkien", "ISBN006", "Fantasy", "MacMillian"));
		books.put("LIB007", new Book("LIB007", "The Two Towers", "J.R.R. Tolkien", "ISBN007", "Fantasy", "MacMillian"));
		books.put("LIB008", new Book("LIB008", "The Return of the King", "J.R.R. Tolkien", "ISBN008", "Fantasy", "MacMillian"));
		books.put("LIB009", new Book("LIB009", "The Hobbit", "J.R.R. Tolkien", "ISBN009", "Fantasy", "MacMillian"));
	}

	public static HashMap<String, User> getUsers() {
		return users;
	}

	public static void setUsers(HashMap<String, User> users) {
		LibraryData.users = users;
	}

	public static HashMap<String, Book> getBooks() {
		return books;
	}

	public static void setBooks(HashMap<String, Book> books) {
		LibraryData.books = books;
	}

	
	
}
