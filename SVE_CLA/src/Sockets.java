import javax.net.ssl.SSLSocket;

/*
 * Handles sockets from client and CTF-server
 * */
public class Sockets {

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
				protocol.sendMessage(socToClient, "Login succeded");
				protocol.sendValidationId(socToClient, user.getValidId());
				
				//Send to CTF also
				//Later problem..
				
			}
		}
	}
	
	public boolean userAuthorization(){
		String[] userInfo = protocol.getMessage(socToClient);
		
		user = Database.instance().verifyUser(userInfo);
		
		if(user == null){
			System.out.println("Something went wrong in Sockets.java");
			return false;
		}
		System.out.println("name : " + user.getName() + " password: " + user.getPassword() 
						+ " validId: " + user.getValidId());
		
		
		return true;
	}
	
}
