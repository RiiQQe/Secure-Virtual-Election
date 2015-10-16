import java.io.*;
import java.util.*;
import javax.net.ssl.*;
import javax.swing.JOptionPane;

/*
 * 	Protocol for CLA, to recieve and send messages
 * 
 * */

public class CLAProtocol {

	public String[] getMessage(SSLSocket socketIn){
		
		String msgArr[] = null;
		System.out.println("Getting message...");
		
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
	
	public void sendMessage(SSLSocket socketOut, String msg){
		try{
			PrintWriter pw = new PrintWriter( socketOut.getOutputStream(), true );
			pw.println(msg);
			pw.flush();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
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
