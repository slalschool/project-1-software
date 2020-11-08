package library.view;
import library.*;
import library.view.listener.*;
import library.process.*;
import library.entity.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.tree.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LibraryFrontEnd extends MouseHandler implements ActionListener{
	
	private JFrame jf;
	private Container cp;
	private CardLayout cl;
	
	private static final String LOGIN_PANEL="LOGIN_PANEL";
	private static final String REGISTER_PANEL="REGISTER_PANEL";
	private static final String HOME_PANEL = "HOME_PANEL";
	private static final String SEARCH_BOOKS_PANEL = "SEARCH_BOOKS_PANEL";
	
	JPanel mainPanel = new JPanel();
	JPanel loginPanel;
	JPanel registerPanel;
	JPanel homePanel;
	JPanel searchBooksPanel;
	
    int v1 = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int h1 = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

	
	//Login form components
	JTextField loginUserNameTF;
	JPasswordField loginPasswordPF;
	JLabel errorMessage;
	JButton submitLoginBtn;
	JButton loginRegisterBtn;
	
	//Account registration form components
	JTextField registerUserNameTF;
	JTextField registerFirstNameTF;
	JTextField registerLastNameTF;
	JPasswordField registerPasswordPF;
	JPasswordField registerReenterPF;
	JButton registerBtn;
	
	//Home page buttons
	JButton searchBooksBtn;
	JButton logoutBtn;
	
	//Search Books page buttons
	JButton addToCartBtn;
	JButton checkoutBtn;
	private JTable allBooksList;
	String chosenBookId;
	int numberOfBooksInCart=0;
	JPanel cartBooksCountPanel = new JPanel();
	
	//User specific details
	private User loggedInUser;
	private HashMap<String, Book> borrowedBooks;
	private JTable borrowedBooksList;
	
	public LibraryFrontEnd() {
		
		LibraryData.initiatlizeData();
		buildWindow();
	}
	
	private void buildWindow() {
		
		try {
			jf = new JFrame("Library Application");
            cp = jf.getContentPane();
            cp.setLayout(new BorderLayout());
            cl = new CardLayout();
            mainPanel.setLayout(cl);

            buildLoginScreen();
            mainPanel.add(loginPanel, this.LOGIN_PANEL);
            buildAccountRegistrationScreen();
            mainPanel.add(registerPanel, REGISTER_PANEL);

            WindowHandler wh0 = new WindowHandler();

            jf.add(mainPanel);
            cl.show(mainPanel, LOGIN_PANEL);

            jf.addWindowListener(wh0);            
            jf.setBounds(50,50,1200,800);
            jf.setResizable(false);
            jf.setVisible(true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void buildSearchBooksScreen() {
		searchBooksPanel = new JPanel();
		searchBooksPanel.setLayout(new BorderLayout());
		
		// Header Panel with Heading
		JPanel headerPanel = buildHeaderPanel();
		searchBooksPanel.add(BorderLayout.NORTH, headerPanel);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		//Search Books page header
		JPanel centerPanelNorth = new JPanel();
		centerPanelNorth.setPreferredSize(new Dimension(1190, 50));
		centerPanelNorth.setMinimumSize(new Dimension(1190, 50));
		centerPanelNorth.setMaximumSize(new Dimension(1190, 50));
		centerPanelNorth.setLayout(new GridLayout(1,3));
		JLabel welcomeGreeting = new JLabel("   Welcome "+loggedInUser.getFirstName()+"!");
		setFormFont(welcomeGreeting,false);
		centerPanelNorth.add(welcomeGreeting);
		JLabel cartCntLabel = new JLabel("Cart ("+numberOfBooksInCart+")");
		setFormFont(cartCntLabel,false);
		cartBooksCountPanel.add(cartCntLabel);
		centerPanelNorth.add(cartBooksCountPanel);
		addToCartBtn = new JButton("ADD TO CART");
		addToCartBtn.addActionListener(this);
		setFormFont(addToCartBtn,false);
		checkoutBtn = new JButton("CHECKOUT");
		checkoutBtn.addActionListener(this);
		setFormFont(checkoutBtn,false);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addToCartBtn);
		buttonPanel.add(checkoutBtn);
		buttonPanel.add(logoutBtn);
		centerPanelNorth.add(buttonPanel);
		centerPanel.add(BorderLayout.NORTH, centerPanelNorth);

		//List of all books
		JPanel centerPanelMiddle = new JPanel();
		centerPanelMiddle.setLayout(new GridLayout(1,1));
		centerPanelMiddle.setPreferredSize(new Dimension(1190, 600));
		centerPanelMiddle.setMinimumSize(new Dimension(1190, 600));
		centerPanelMiddle.setMaximumSize(new Dimension(1190, 600));
		String[] columnNames = {"Book ID", "Title", "Subject", "Publisher", "Author", "ISBN"};
		allBooksList = new JTable(buildAllBooksArray(), columnNames);
		allBooksList.setPreferredScrollableViewportSize(new Dimension(1190, 600));
		allBooksList.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(allBooksList, v1, h1);
		allBooksList.addMouseListener(this);
		 
		centerPanelMiddle.add(scrollPane);
		centerPanel.add(BorderLayout.CENTER, centerPanelMiddle);
		
		searchBooksPanel.add(BorderLayout.CENTER, centerPanel);
		searchBooksPanel.add(BorderLayout.SOUTH, buildFooterPanel());
		
	}
	
	private String[][] buildAllBooksArray() {
		HashMap<String, Book> allBooks = LibraryData.getBooks();
		int bookNum =  allBooks.size();
		String[][] allBooksDetails = new String[bookNum][6];
		
		Book book = null;
		java.util.Set<String> keys = allBooks.keySet();
		java.util.Iterator keyIterator = keys.iterator();

		int i=0;
		while(keyIterator.hasNext()) {
			String key = (String)keyIterator.next();
			book = allBooks.get(key);

			allBooksDetails[i][0] = book.getBookId();
			allBooksDetails[i][1] = book.getTitle();
			allBooksDetails[i][2] = book.getSubject();
			allBooksDetails[i][3] = book.getPublisher();
			allBooksDetails[i][4] = book.getAuthor();
			allBooksDetails[i][5] = book.getIsbnNumber();
			i++;
		}
		
		return allBooksDetails;
	}
	
	
	private void buildHomeScreen() {
		homePanel = new JPanel();
		homePanel.setLayout(new BorderLayout());
		
		// Header Panel with Heading
		JPanel headerPanel = buildHeaderPanel();
		homePanel.add(BorderLayout.NORTH, headerPanel);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		//Home page header
		JPanel centerPanelNorth = new JPanel();
		centerPanelNorth.setPreferredSize(new Dimension(1190, 50));
		centerPanelNorth.setMinimumSize(new Dimension(1190, 50));
		centerPanelNorth.setMaximumSize(new Dimension(1190, 50));
		centerPanelNorth.setLayout(new GridLayout(1,4));
		JLabel welcomeGreeting = new JLabel("   Welcome "+loggedInUser.getFirstName()+"!");
		setFormFont(welcomeGreeting,false);
		centerPanelNorth.add(welcomeGreeting);
		addSpacerLabel(centerPanelNorth,2);
		searchBooksBtn = new JButton("SEARCH BOOKS");
		searchBooksBtn.addActionListener(this);
		setFormFont(searchBooksBtn,false);
		logoutBtn = new JButton("LOGOUT");
		logoutBtn.addActionListener(this);
		setFormFont(logoutBtn,false);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(searchBooksBtn);
		buttonPanel.add(logoutBtn);
		centerPanelNorth.add(buttonPanel);
		centerPanel.add(BorderLayout.NORTH, centerPanelNorth);

		//Home page currently borrowed books section
		JPanel centerPanelMiddle = new JPanel();
		centerPanelMiddle.setPreferredSize(new Dimension(1190, 300));
		centerPanelMiddle.setMinimumSize(new Dimension(1190, 300));
		centerPanelMiddle.setMaximumSize(new Dimension(1190, 300));
		centerPanelMiddle.setLayout(new BorderLayout());
		
		JLabel borrowedBooksHeading=null;
		String[] columnNames = {"Book ID", "Title", "Subject", "Publisher", "Author", "ISBN"};
		borrowedBooksList = new JTable(buildBorrowedBooksArrary(), columnNames);
		JScrollPane scrollPane = new JScrollPane(borrowedBooksList, v1, h1);
		setFormFont(borrowedBooksList,false);
		JPanel borrowedBooksHeadingPanel = new JPanel();
		borrowedBooksHeadingPanel.setLayout(new GridLayout(1,1));

		if(borrowedBooksList.size().height==0) {
			borrowedBooksHeading = new JLabel("   You have no borrowed books.\n Click SEARCH to find books to borrow.");
		}
		else {
			borrowedBooksHeading = new JLabel("   You have borrowed the following books:");
		}
		setFormFont(borrowedBooksHeading,false);
		//addSpacerLabel(borrowedBooksHeadingPanel,1);
		borrowedBooksHeadingPanel.add(borrowedBooksHeading);
		//addSpacerLabel(borrowedBooksHeadingPanel,1);
		
		centerPanelMiddle.add(BorderLayout.NORTH, borrowedBooksHeadingPanel);
		centerPanelMiddle.add(BorderLayout.CENTER, scrollPane);
		centerPanel.add(BorderLayout.CENTER, centerPanelMiddle);
		
		homePanel.add(BorderLayout.CENTER, centerPanel);
		homePanel.add(BorderLayout.SOUTH, buildFooterPanel());
	}
	
	private String[][] buildBorrowedBooksArrary() {
		ArrayList currentlyBorrowedBooks = Transaction.getCurrentBookings().get(loggedInUser.getUserName());

		if(currentlyBorrowedBooks==null) {
			currentlyBorrowedBooks = new ArrayList();
		}
		int bookNum = currentlyBorrowedBooks.size();
		String[][] myBorrowedBooks = new String[bookNum][6];

		for(int i=0; i<currentlyBorrowedBooks.size(); i++) {
			myBorrowedBooks[i][0] = LibraryData.getBooks().get(currentlyBorrowedBooks.get(i)).getBookId();
			myBorrowedBooks[i][1] = LibraryData.getBooks().get(currentlyBorrowedBooks.get(i)).getTitle();
			myBorrowedBooks[i][2] = LibraryData.getBooks().get(currentlyBorrowedBooks.get(i)).getSubject();
			myBorrowedBooks[i][3] = LibraryData.getBooks().get(currentlyBorrowedBooks.get(i)).getPublisher();
			myBorrowedBooks[i][4] = LibraryData.getBooks().get(currentlyBorrowedBooks.get(i)).getAuthor();
			myBorrowedBooks[i][5] = LibraryData.getBooks().get(currentlyBorrowedBooks.get(i)).getIsbnNumber();
		}
		
		return myBorrowedBooks;
	}
	
	
	
	private void buildAccountRegistrationScreen() {
		registerPanel = new JPanel();
		registerPanel.setLayout(new BorderLayout());
		
		// Header Panel with Heading
		JPanel headerPanel = buildHeaderPanel();
		registerPanel.add(BorderLayout.NORTH, headerPanel);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		JPanel centerPanelNorth = new JPanel();
		centerPanelNorth.setPreferredSize(new Dimension(1190, 300));
		centerPanelNorth.setMinimumSize(new Dimension(1190, 300));
		centerPanelNorth.setMaximumSize(new Dimension(1190, 300));
		centerPanelNorth.setLayout(new GridLayout(9, 6));
		
		JLabel accountRegistrationText = new JLabel("Register Account");
		setFormFont(accountRegistrationText,true);
		
		JLabel userNameText = new JLabel("Username");
		setFormFont(userNameText,false);

		JLabel userFNText = new JLabel("First Name");
		setFormFont(userFNText,false);
		
		JLabel userLNText = new JLabel("Last Name");
		setFormFont(userLNText,false);
		
		JLabel passwordText = new JLabel("Password");
		setFormFont(passwordText,false);

		JLabel reenterText = new JLabel("Re-enter Password");
		setFormFont(reenterText,false);
		
		//row 1
		addSpacerLabel(centerPanelNorth,6);

		//row 2
		addSpacerLabel(centerPanelNorth,2); 
		centerPanelNorth.add(accountRegistrationText);
		addSpacerLabel(centerPanelNorth,3);

		//row 3
		registerUserNameTF = new JTextField(10);
		addSpacerLabel(centerPanelNorth,2);
		centerPanelNorth.add(userNameText);
		centerPanelNorth.add(registerUserNameTF);
		addSpacerLabel(centerPanelNorth,2);
		
		//row 4
		registerFirstNameTF  = new JTextField(20);
		addSpacerLabel(centerPanelNorth,2);
		centerPanelNorth.add(userFNText);
		centerPanelNorth.add(registerFirstNameTF);
		addSpacerLabel(centerPanelNorth,2);
		
		//row 5
		registerLastNameTF  = new JTextField(20);
		addSpacerLabel(centerPanelNorth,2);
		centerPanelNorth.add(userLNText);
		centerPanelNorth.add(registerLastNameTF);
		addSpacerLabel(centerPanelNorth,2);
		
		//row 6
		registerPasswordPF = new JPasswordField(20);
		addSpacerLabel(centerPanelNorth,2);
		centerPanelNorth.add(passwordText);
		centerPanelNorth.add(registerPasswordPF);
		addSpacerLabel(centerPanelNorth,2);
		
		//row 7
		registerReenterPF = new JPasswordField(20);
		addSpacerLabel(centerPanelNorth,2);
		centerPanelNorth.add(reenterText);
		centerPanelNorth.add(registerReenterPF);
		addSpacerLabel(centerPanelNorth,2);

		//row 8
		addSpacerLabel(centerPanelNorth,6);
		
		//row 9
		registerBtn = new JButton("SUBMIT REGISTRATIONT");
		setFormFont(registerBtn,false);
		registerBtn.setPreferredSize(new Dimension(100, 20));
		registerBtn.addActionListener(this);
		addSpacerLabel(centerPanelNorth,3);
		centerPanelNorth.add(new JPanel().add(registerBtn));
		addSpacerLabel(centerPanelNorth,2);


		centerPanel.add(BorderLayout.NORTH, centerPanelNorth);
		registerPanel.add(BorderLayout.CENTER, centerPanel);

		JPanel spl = new JPanel();
		spl.setPreferredSize(new Dimension(1190, 100));
		spl.setMinimumSize(new Dimension(1190, 100));
		spl.setBackground(new Color(150, 150, 150));
		registerPanel.add(BorderLayout.SOUTH, buildFooterPanel());

		System.out.println("Registration screen built");
	
	}
	
	
	private void buildLoginScreen() {
		loginPanel = new JPanel();
		loginPanel.setLayout(new BorderLayout());
		
		// Header Panel with Heading
		JPanel headerPanel = buildHeaderPanel();
		loginPanel.add(BorderLayout.NORTH, headerPanel);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		JPanel centerPanelNorth = new JPanel();
		centerPanelNorth.setPreferredSize(new Dimension(1190, 300));
		centerPanelNorth.setMinimumSize(new Dimension(1190, 300));
		centerPanelNorth.setMaximumSize(new Dimension(1190, 300));
		centerPanelNorth.setLayout(new GridLayout(9, 8));
		
		JLabel loginText = new JLabel("Login");
		setFormFont(loginText,true);
		
		JLabel userNameText = new JLabel("Username");
		setFormFont(userNameText,false);
		
		JLabel passwordText = new JLabel("Password");
		setFormFont(passwordText,false);
		
		JLabel registerText = new JLabel("No Account?");
		setFormFont(registerText,false);


		//row 1
		addSpacerLabel(centerPanelNorth,8);

		//row 2
		addSpacerLabel(centerPanelNorth,3); 
		centerPanelNorth.add(loginText);
		addSpacerLabel(centerPanelNorth,4);

		//row 3
		loginUserNameTF = new JTextField(10);
		addSpacerLabel(centerPanelNorth,3);
		centerPanelNorth.add(userNameText);
		centerPanelNorth.add(loginUserNameTF);
		addSpacerLabel(centerPanelNorth,3);
		
		//row 4
		loginPasswordPF = new JPasswordField(20);
		addSpacerLabel(centerPanelNorth,3);
		centerPanelNorth.add(passwordText);
		centerPanelNorth.add(loginPasswordPF);
		addSpacerLabel(centerPanelNorth,3);
		
		//row 5
		addSpacerLabel(centerPanelNorth,8);

		//row 6
		submitLoginBtn = new JButton("SUBMIT");
		setFormFont(submitLoginBtn,false);
		submitLoginBtn.setPreferredSize(new Dimension(100, 20));
		submitLoginBtn.addActionListener(this);
		addSpacerLabel(centerPanelNorth,4);
		centerPanelNorth.add(new JPanel().add(submitLoginBtn));
		addSpacerLabel(centerPanelNorth,3);

		//row 7
		addSpacerLabel(centerPanelNorth,8);

		//row 8
		addSpacerLabel(centerPanelNorth,8);

		//row 9
		loginRegisterBtn = new JButton("REGISTER");
		setFormFont(loginRegisterBtn,false);
		addSpacerLabel(centerPanelNorth,3);
		centerPanelNorth.add(registerText);
		centerPanelNorth.add(new JPanel().add(loginRegisterBtn));
		loginRegisterBtn.addActionListener(this);
		addSpacerLabel(centerPanelNorth,3);
		
		centerPanel.add(BorderLayout.NORTH, centerPanelNorth);
		
		loginPanel.add(BorderLayout.CENTER, centerPanel);
		loginPanel.add(BorderLayout.SOUTH, buildFooterPanel());

		
		System.out.println("Login screen built");
	}
	
	//Footer panel
	private JPanel buildFooterPanel() {
		JPanel footer = new JPanel();
		footer.setPreferredSize(new Dimension(1190, 50));
		footer.setMinimumSize(new Dimension(1190, 50));
		footer.setBackground(new Color(125, 125, 125));
		return footer;
	}
	
	// Header Panel with Heading
	private JPanel buildHeaderPanel() {
		JPanel header = new JPanel();
		JLabel headerLabel = new JLabel("LIBRARY APPLICATION");
		headerLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 30));
		headerLabel.setForeground(Color.WHITE);
		
		header.setLayout(new GridLayout(3, 3));
		addSpacerLabel(header,4);
		header.add(headerLabel);
		addSpacerLabel(header,4);
		
		header.setPreferredSize(new Dimension(1190, 65));
		header.setMinimumSize(new Dimension(1190, 65));
		header.setBackground(new Color(0, 0, 255));
		
		return header;
	}
	
	private void addSpacerLabel(JComponent comp, int count) {
		for(int i=0; i<count; i++)
		{
			comp.add(new JLabel("               "));
		}
	}
	
	public void setFormFont(JComponent component, boolean heading) {
		if(!heading)
		{
			component.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		}
		else
		{
			component.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
		}
	}

	
	public void actionPerformed(ActionEvent ae) {
		boolean error = false;
		
        if(ae.getSource()==submitLoginBtn) {
        	String inputUserName = loginUserNameTF.getText();
        	String inputPassword = loginPasswordPF.getText();
        	
        	if(inputUserName==null || inputUserName.trim().equals("")) {
        		error = true;
        		JOptionPane.showMessageDialog(new JFrame(),"Username is required","Error",JOptionPane.ERROR_MESSAGE);
        	}
        	if(!error) {
	        	if(inputPassword==null || inputPassword.trim().equals(""))
	        	{
	        		error = true;
	        		JOptionPane.showMessageDialog(new JFrame(),"Password is required","Error",JOptionPane.ERROR_MESSAGE);
	        	}
        	}
        	if(!error) {
        		boolean result = UserLogin.verifyUser(inputUserName, inputPassword);
        		if(!result) {
            		error = true;
            		JOptionPane.showMessageDialog(new JFrame(),"Invalid credentials","Error",JOptionPane.ERROR_MESSAGE);
        		}
        		else {
        			loginUserNameTF.setText("");
        			loginPasswordPF.setText("");
        			//show Home screen logic goes here
        			loggedInUser = LibraryData.getUsers().get(inputUserName);
        			System.out.println("User "+loggedInUser.getFirstName()+" logged in.");
                    buildHomeScreen();
                    mainPanel.add(homePanel, HOME_PANEL);
                    cl.show(mainPanel, HOME_PANEL);
        		}
        	}
        	
        }	
        if(ae.getSource()==loginRegisterBtn) {
        	System.out.println("Opening Account Registration");
        	cl.show(mainPanel, REGISTER_PANEL);

        }
        if(ae.getSource()==registerBtn) {
        	String inputUsername = registerUserNameTF.getText();
        	String inputFN = registerFirstNameTF.getText();
        	String inputLN = registerLastNameTF.getText();
        	String inputPassword = registerPasswordPF.getText();
        	String inputReenter = registerReenterPF.getText();

        	if(inputUsername==null || inputUsername.trim().equals("")) {
        		error = true;
        		JOptionPane.showMessageDialog(new JFrame(),"Username is required","Error",JOptionPane.ERROR_MESSAGE);
        	}
        	if(!error) {
            	if(inputFN==null || inputFN.trim().equals(""))
            	{
            		error = true;
            		JOptionPane.showMessageDialog(new JFrame(),"First name is required","Error",JOptionPane.ERROR_MESSAGE);
            	}
        	}
        	if(!error) {
            	if(inputLN==null || inputLN.trim().equals(""))
            	{
            		error = true;
            		JOptionPane.showMessageDialog(new JFrame(),"Last name is required","Error",JOptionPane.ERROR_MESSAGE);
            	}
        	}
        	if(!error) {
            	if(inputPassword==null || inputPassword.trim().equals(""))
            	{
            		error = true;
            		JOptionPane.showMessageDialog(new JFrame(),"Password is required","Error",JOptionPane.ERROR_MESSAGE);
            	}
        	}
        	if(!error) {
            	if(inputReenter==null || inputReenter.trim().equals(""))
            	{
            		error = true;
            		JOptionPane.showMessageDialog(new JFrame(),"Password re-entry is required","Error",JOptionPane.ERROR_MESSAGE);
            	}
        	}
        	if(!error) {
        		if(!inputPassword.equals(inputReenter))
        		{
            		error = true;
        			JOptionPane.showMessageDialog(new JFrame(),"Passwords do not match","Error",JOptionPane.ERROR_MESSAGE);
        		}
        	}
        	if(!error) {
        		Transaction.registerNewUser(inputUsername, inputFN, inputLN, inputPassword);
        		System.out.println("Total users: "+LibraryData.getUsers().size());
        		String successMsg = "User "+inputFN+" "+inputLN+" created";
        		JOptionPane.showMessageDialog(new JFrame(),successMsg,"Success",JOptionPane.INFORMATION_MESSAGE);
        		cl.show(mainPanel, LOGIN_PANEL);
        	}
        }
        if(ae.getSource()==logoutBtn) {
        	System.out.println("User "+loggedInUser.getFirstName()+" logged out.");
        	loggedInUser = null;
        	homePanel = null;
        	cl.show(mainPanel, LOGIN_PANEL);
        }
        if(ae.getSource()==searchBooksBtn) {
            buildSearchBooksScreen();
            mainPanel.add(searchBooksPanel, SEARCH_BOOKS_PANEL);
            cl.show(mainPanel, SEARCH_BOOKS_PANEL);
        }
        if(ae.getSource()==addToCartBtn) {
        	if(chosenBookId==null || chosenBookId.equals(""))
        	{
        		JOptionPane.showMessageDialog(new JFrame(),"No book selected","Error",JOptionPane.ERROR_MESSAGE);
        	}
        	else
        	{
        		Transaction.addBookToCart(chosenBookId);
        		numberOfBooksInCart = Transaction.cart.size(); 
        		System.out.println("Cart Size: "+numberOfBooksInCart);
        		JLabel cartCntLabel = new JLabel("Cart ("+numberOfBooksInCart+")");
        		setFormFont(cartCntLabel,false);
        		cartBooksCountPanel.removeAll();
        		cartBooksCountPanel.add(cartCntLabel);
        		cp.repaint();
        		jf.setVisible(false);
        		jf.setVisible(true);
        	}
        }
        if(ae.getSource()==checkoutBtn) {
        	if(numberOfBooksInCart==0)
        	{
        		JOptionPane.showMessageDialog(new JFrame(),"Cart is empty. Add at least 1 book to cart.","Error",JOptionPane.ERROR_MESSAGE);
        	}
        	else
        	{
        		Transaction.checkOut(loggedInUser.getUserName(), Transaction.cart);
        		JOptionPane.showMessageDialog(new JFrame(),numberOfBooksInCart + " books checked out by "+loggedInUser.getFirstName(),"Success",JOptionPane.INFORMATION_MESSAGE);
        		Transaction.cart.clear();
        		numberOfBooksInCart=0;
        		cartBooksCountPanel.removeAll();
        	}
        }
        
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		int row = allBooksList.rowAtPoint(evt.getPoint());
		int col = 0;
		chosenBookId = (String)allBooksList.getValueAt(row, col);
		System.out.println("Selected Book ID: "+chosenBookId);
	}

}
