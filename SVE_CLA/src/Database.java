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
		JOptionPane.showMessageDialog(null, "Creating Dataabse2");
		return SingletonHolder.INSTANCE;
	}
	
	private Database (){

		JOptionPane.showMessageDialog(null, "Creating Dataabse3");
		User user = new User("Kalle", "password", null);
		userDB.add(user);
	}
	
	public User verifyUser(String[] user){

		System.out.println("Veryfiying user..: Username:" + user[0] + "password:" + user[1]);
        
		for(User tmpUser : userDB){
			
			System.out.println(tmpUser.getPassword() + "!");
			if(tmpUser.getName().equals(user[0]) && tmpUser.getPassword().equals(user[1]))
				return tmpUser;
		}
		
		System.out.println("Cannot Find");
		return null;
	}
	public int getSize(){
		return userDB.size();
	}
	
}

