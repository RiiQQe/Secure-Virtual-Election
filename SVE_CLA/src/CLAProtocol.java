import java.io.*;
import java.util.*;
import javax.net.ssl.*;
import javax.swing.JOptionPane;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/

/*
 * 	Protocol for CLA, to receive and send messages
 * 
 * */

public class CLAProtocol {

	/*
	 * Used to retrieve messages from sockets
	 * Returns String of the message
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
				
				msgArr[i] = (new String( token.nextToken() )).trim();
				i++;
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if(msgArr == null) System.out.println("CLAProtocol getMessage not working..");
		
		return msgArr;
	}
	
	/*
	 * Used to send message to sockets
	 * takes socket to send message to and message to be sent
	 * */
	
	public void sendMessage(SSLSocket socketOut, String msg){
		try{
			PrintWriter pw = new PrintWriter( socketOut.getOutputStream(), true );
			pw.println(msg);
			pw.flush();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/*
	 * Used to send validationId to sockets
	 * since validId is not a string a separate is needed
	 * */
	
	public void sendValidationId(SSLSocket socketOut, UUID validId){
		try{
			PrintWriter pw = new PrintWriter( socketOut.getOutputStream(), true );
			pw.println(validId);
			pw.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
}
