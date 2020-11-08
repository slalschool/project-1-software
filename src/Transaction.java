package library.process;

import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import library.entity.Book;
import library.entity.User;

public class Transaction {

	public static HashMap<String, ArrayList> currentBookings = new HashMap();
	
	public static ArrayList cart = new ArrayList();
	
	private Transaction() {
		// No construtor calls
	}
	
	public static HashMap<String, ArrayList> getCurrentBookings() {
		return currentBookings;
	}
	public static void setCurrentBookings(HashMap<String, ArrayList> currentBookings) {
		Transaction.currentBookings = currentBookings;
	}

	public static boolean checkInventory(String bookIdParam) {
		boolean result=false;
		Book book = LibraryData.getBooks().get(bookIdParam);
		if(book.getCopies()>0)
		{
			//System.out.println("Currently Available Copies: "+book.getCopies());
			result = true;
		}
		return result;
	}
	
	public static void addBookToCart(String bookIdParam)
	{
		cart.add(bookIdParam);
	}
	
	/**
	 * Checkout books that were added to the cart
	 */
	public static void checkOut(String userNameParam, ArrayList cart) {
		HashMap<String, Book> booksInventory = LibraryData.getBooks();
		
		Book book = null;
		for(int i=0; i<cart.size(); i++)
		{
			String bookId = (String)cart.get(i);
			
			book = booksInventory.get(bookId);
			book.setCopies(book.getCopies()-1);
			booksInventory.put(bookId, book);
			
			//System.out.println("Book Title: "+book.getTitle());
			//System.out.println("Balance Copies Available: "+book.getCopies());
		}
		LibraryData.setBooks(booksInventory);
		ArrayList bookingsTillDate = currentBookings.get(userNameParam);
		if(bookingsTillDate==null)
		{
			bookingsTillDate = new ArrayList();
		}
		bookingsTillDate.addAll(cart);
	}
	
	public static void returnBook(String userNameParam, String bookIdParam) {
		HashMap<String, Book> booksInventory = LibraryData.getBooks();
		Book book = booksInventory.get(bookIdParam);
		book.setCopies(book.getCopies()+1);
		booksInventory.put(bookIdParam, book);

		ArrayList bookingsTillDate = currentBookings.get(userNameParam);
		bookingsTillDate.remove(book.getBookId());
		currentBookings.put(userNameParam, bookingsTillDate);
		
		System.out.println("Book Title: "+book.getTitle());
		System.out.println("Balance Copies Available: "+book.getCopies());
	}
	
	public static void viewAvailableBooks() {
		HashMap<String, Book> books = LibraryData.getBooks();
		Book book = null;
		java.util.Set<String> keys = books.keySet();
		java.util.Iterator keyIterator = keys.iterator();
		
		while(keyIterator.hasNext())
		{
			String key = (String)keyIterator.next();
			book = books.get(key);
			
			System.out.println("Book ID: "+book.getBookId());
			System.out.println("Title: "+book.getTitle());
			System.out.println("Author: "+book.getAuthor());
			System.out.println("ISBN Number: "+book.getIsbnNumber());
			System.out.println("Subject: "+book.getSubject());
			System.out.println("Publisher: "+book.getPublisher());
			System.out.println("Copies: "+book.getCopies());
			System.out.println("------------------------------- ");
		}
	}
	
	public static boolean registerNewUser(String userName, String firstName, String lastName, String password) {
		boolean result = false;
		Random r = new Random( System.currentTimeMillis() );
	    int cardNumber = 10000 + r.nextInt(20000);
		
		User member = new User();
		member.setUserName(userName);
		member.setFirstName(firstName);
		member.setLastName(lastName);
		member.setPassword(password);
		member.setCardNumber(cardNumber+"");
		member.setAdmin(false);
		
		HashMap<String, User> currentUsers = LibraryData.getUsers();
		currentUsers.put(member.getUserName(), member);
		
		LibraryData.setUsers(currentUsers);
		
		return result;
	}
	
}
