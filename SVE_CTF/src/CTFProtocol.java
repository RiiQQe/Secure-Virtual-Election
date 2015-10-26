import java.io.*;
import java.util.*;
import javax.net.ssl.*;

public class CTFProtocol {

	public String[] getMessage(SSLSocket socketIn){
		String msgArr[] = null;
		
		try{
			BufferedReader brIn = new BufferedReader( new InputStreamReader( socketIn.getInputStream() ) );
			
			String str = brIn.readLine();
			System.out.println("hello3");
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
