package library.entity;

public class User implements java.io.Serializable{
	
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String cardNumber;
	private boolean isAdmin;
	
	public User(String userName, String firstName, String lastName, String password, String cardNumber, boolean isAdmin) {
		this.setUserName(userName);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPassword(password);
		this.setCardNumber(cardNumber);
		this.setAdmin(isAdmin);
	}

	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isAdmin() {
		return isAdmin;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public User() {
		// TODO Auto-generated constructor stub
	}

}
