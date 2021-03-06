import java.io.*;
import java.util.*;
import javax.net.ssl.*;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/

/*
 * Protocol for CTF that receives and sends messages and receives validationIDs
 */
public class CTFProtocol {
	
	/*
	 * getMessages receives messages from the specified socket
	 */
	public String[] getMessage(SSLSocket socketIn){
		String msgArr[] = null;
		
		try{
			BufferedReader brIn = new BufferedReader( new InputStreamReader( socketIn.getInputStream() ) );
			
			String str = brIn.readLine();
			StringTokenizer token = new StringTokenizer( str );
			
			int i = 0; 
			
			msgArr = new String[ token.countTokens() ];
			
			while( token.hasMoreTokens() ){
				msgArr[i] = token.nextToken();
				i++;
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if(msgArr == null) System.out.println("msgArr empty in CTFProtocol");
		
		return msgArr;
	}
	
	/*
	 * sendMessage sends messages to the specified socket with the given string
	 */
	public void sendMessage(SSLSocket socketOut, String msg){
		
		try{
			PrintWriter pw = new PrintWriter( socketOut.getOutputStream(), true);
			pw.println(msg);
			pw.flush();
			
		}catch(IOException e){
			System.out.println("Somthing is wrong in sendMessage in CTFProtocol");
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Receives a validationID from the specified socket
	 */
	public UUID getValidationId(SSLSocket socketIn){
		UUID validId = null;
		
		try{
			BufferedReader bfIn = new BufferedReader( new InputStreamReader( socketIn.getInputStream() ) );
			
			validId = UUID.fromString(bfIn.readLine());
			
		}catch(Exception e){
			System.out.println("Something went wrong while reading ValidID");
			e.printStackTrace();
		}
		
		if(validId == null) System.out.println("ValidID not set in CTFProtocol");
		
		return validId;
	}
	
}
