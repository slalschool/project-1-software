package library;
import library.process.*;
import library.view.*;

public class Starter {

	public static String userName = "sidlal";
	public static String password = "welcome123";
	public static String bookId = "LIB001";
	
	public Starter() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		LibraryFrontEnd view = new LibraryFrontEnd();
		//login();
		//borrowBooks();
		//returnBook();
		//viewAvailableBooks();
	}
	
	private static void login() {
		UserLogin tryLogin = new UserLogin();
		
		boolean result = tryLogin.verifyUser(userName, password);
		
		if(result)
		{
			System.out.println("Valid user. Login succeeded.");
		}
		else
		{
			System.out.println("Login failure.");
		}
		
	}
	
	private static void borrowBooks() {
		Transaction.checkInventory(bookId);
		Transaction.addBookToCart(bookId);
		Transaction.checkOut(userName, Transaction.cart);
	}

	
	private static void returnBook() {
		Transaction.checkInventory(bookId);
		Transaction.returnBook(userName, bookId);
	}
	
	private static void viewAvailableBooks()
	{
		Transaction.viewAvailableBooks();
	}
	
}
