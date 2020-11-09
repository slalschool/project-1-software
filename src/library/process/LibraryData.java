package library.process;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import library.entity.*;

public class LibraryData {
	
	private static HashMap<String, User> users = new HashMap();
	private static HashMap<String, Book> books = new HashMap();
	private static Random r = new Random( System.currentTimeMillis() );
	private static Random r1 = new Random( System.currentTimeMillis() );
	
	public LibraryData() {

	}
	
	public static void initiatlizeData() {
		users.put("slal", new User("slal", "Siddharth", "Lal", "password", "CRD01", false));
		users.put("jli", new User("jli", "Jason", "Li", "password", "CRD02", false));
		users.put("admin", new User("admin", "Administrator", "", "sys", "ADMCARD", true));

		books.put("LIB003", new Book("LIB003", "Harry Potter and the Chamber of Secrets", "J.K. Rowling", "ISBN003", "Fantasy", "MacMillian"));
		books.put("LIB004", new Book("LIB004", "Harry Potter and the Prisoner of Azkhaban", "J.K. Rowling", "ISBN004", "Fantasy", "MacMillian"));
		books.put("LIB005", new Book("LIB005", "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "ISBN005", "Fantasy", "MacMillian"));
		books.put("LIB006", new Book("LIB006", "The Fellowship of the Ring", "J.R.R. Tolkien", "ISBN006", "Fantasy", "MacMillian"));
		books.put("LIB007", new Book("LIB007", "The Two Towers", "J.R.R. Tolkien", "ISBN007", "Fantasy", "MacMillian"));
		books.put("LIB008", new Book("LIB008", "The Return of the King", "J.R.R. Tolkien", "ISBN008", "Fantasy", "MacMillian"));
		books.put("LIB009", new Book("LIB09", "And Then There Were None", "Agatha Christie", "ISBN009", "Fantasy", "MacMillian"));
		books.put("LIB010", new Book("LIB010", "Alice's Adventure in Wonderland", "Lewis Caroll", "ISBN010", "Fantasy", "MacMillian"));
		books.put("LIB011", new Book("LIB011", "The Lion, the Witch, and the Wardrobe", "C.S. Lewis", "ISBN011", "Fantasy", "MacMillian"));
		books.put("LIB012", new Book("LIB012", "Twenty Thousand Leagues Under the Sea", "Jules Vernes", "ISBN012", "Fantasy", "MacMillian"));
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
