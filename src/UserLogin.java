package library.process;

import library.entity.*;
import java.util.HashMap;

public class UserLogin {

	/**
	 * Upon initiation of Login process, initialize the LibraryData 
	 */
	public UserLogin() {
		
		LibraryData.initiatlizeData(); 
	}
	
	/**
	 * Verify user credentials and return boolean variable based on whether credentials match
	 */
	public static boolean verifyUser(String userNameParam, String passwordParam) {
		boolean result = false;
		
		HashMap<String, User> users = (HashMap<String, User>)LibraryData.getUsers(); 
		User user = null;
		String password =null;
        for(int i=0;i<users.size();i++) {
        	
        	user = users.get(userNameParam);
        }
        if(user!=null)
        {
        	System.out.println("User exists");
        	if(passwordParam.equals(user.getPassword()))
        	{
        		System.out.println("Password valid");
        		result=true;
        	}
        }
		
		return result;
	}

}
