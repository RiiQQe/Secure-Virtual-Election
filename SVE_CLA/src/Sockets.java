import javax.net.ssl.SSLSocket;
import javax.swing.JOptionPane;


/*
 * Handles sockets from client and CTF-server
 * */
public class Sockets implements Runnable{

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
				
				protocol.sendMessage(socToClient, "LoginSucceded");
				protocol.sendValidationId(socToClient, user.getValidId());
				
				//Send to CTF also

				protocol.sendValidationId(socToCTF, user.getValidId());
				
				//protocol.sendMessage(socToCTF, "msg");
				
			}else protocol.sendMessage(socToClient, "Login failed, try again");
		}
	}
	
	/*
	 * Returns true or false if the user can be found in the DB
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
