/*
 * 
 * 	Database for Users that are authorized to vote
 * 
 * 
 * */
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Database {
	
	private LinkedList<User> userDB = new LinkedList<User>();
	
	private static class SingletonHolder {
		public static final Database INSTANCE = new Database();
	}
	
	public static Database instance(){
		return SingletonHolder.INSTANCE;
	}
	
	private Database (){
		User user = new User("Kalle", "password", null);
		userDB.add(user);
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

