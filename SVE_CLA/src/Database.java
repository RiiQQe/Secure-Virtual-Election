/*
 * 
 * 	Database for Users that are authorized to vote
 * 
 * 
 * */
import java.util.LinkedList;

import javax.swing.JOptionPane;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/

public class Database {
	
	private LinkedList<User> userDB = new LinkedList<User>();
	
	private static class SingletonHolder {
		public static final Database INSTANCE = new Database();
	}
	
	/*
	 * Returns the singleton instance
	 */
	
	public static Database instance(){
		return SingletonHolder.INSTANCE;
	}
	
	/*
	 * Adds a user to the database
	 */
	
	private Database (){
		User user = new User("Pier", "icecream", null);
		userDB.add(user);
		
		User user2 = new User("test", "test", null);
		userDB.add(user2);
		
		User user3 = new User("test2", "test2", null);
		userDB.add(user3);
	}
	
	/*
	 * Used to find if User is in DB
	 * return user if found and null if not 
	*/
	
	public User verifyUser(String[] user){
		for(User tmpUser : userDB){
			if(tmpUser.getName().equals(user[0]) && tmpUser.getPassword().equals(user[1]))
				return tmpUser;
		}
		
		System.out.println("Cannot Find");
		return null;
	}
	
	
}

