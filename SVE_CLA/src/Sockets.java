import javax.net.ssl.SSLSocket;
import javax.swing.JOptionPane;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/

/*
 * Handles sockets from client and CTF-server
 * */
public class Sockets implements Runnable{
	
	//Sockets to client and CTF.
	private SSLSocket socToClient;
	private SSLSocket socToCTF;
	
	private CLAProtocol protocol;
	private User user;
	
	public Sockets(SSLSocket socToClient, SSLSocket socToCTF){
        
		this.socToClient = socToClient;
		this.socToCTF = socToCTF;
	}
	
	public void run(){
		protocol = new CLAProtocol();
		
		while(true){
			if(userAuthorization()){
				
				//Sends confirmation to client that the login succeeded.
				protocol.sendMessage(socToClient, "LoginSucceeded");
				//Sends validationID to client and CTF.
				protocol.sendValidationId(socToClient, user.getValidId());
				protocol.sendValidationId(socToCTF, user.getValidId());
								
			}else protocol.sendMessage(socToClient, "LoginFailed, try again");
		}
	}
	
	/*
	 * Returns true or false if the user can/can´t be found in the DB
	*/
	
	public boolean userAuthorization(){
		
		String[] userInfo = protocol.getMessage(socToClient);
		
		user = Database.instance().verifyUser(userInfo);
		
		if(user == null){
			System.out.println("Something went wrong in Sockets.java");
			return false;
		}
		
		return true;
	}
	
}
