import java.io.*;
import java.util.*;

import javax.net.ssl.SSLSocket;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/

public class ClientProtocol {
	
	//Method to send messages from Client to CLA&CTF. 
	public void sendMessage(SSLSocket socket, String msg ){
			//Sending message with to CLA or CTF server(depending on the socket),
			//using a PrintWriter.
			try {
				PrintWriter socketOut = new PrintWriter( socket.getOutputStream(), true );
				socketOut.println(msg);
				socketOut.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	//Method to receive messages from CLA and CTF servers.
	public String[] getMessage(SSLSocket socket){
		String[] messageArray = null;
			
			//Receiving messages using a Bufferedreader.
			try{
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String inStr = in.readLine();
				StringTokenizer st = new StringTokenizer(inStr);
				int i = 0;
				messageArray = new String[st.countTokens()];
				//Storing the message as an array.
				while(st.hasMoreTokens()){
					messageArray[i] = new String(st.nextToken());
					i++;
				}
				
				System.out.println(messageArray);
				
			}catch(IOException e){
				e.printStackTrace();
			}
			
		
		if(messageArray == null){
			System.out.println("ClientProtocol: messageArray == null");
		}
		return messageArray;

	}
	
	//Returns a UUID(Universal Unique Identifier) which is a
	//128-bit number to identify a user.
	public UUID getValidationId(SSLSocket socketIn){
		UUID validId = null;
		
		try{
			BufferedReader bfIn = new BufferedReader( new InputStreamReader( socketIn.getInputStream() ) );
			
			validId = UUID.fromString(bfIn.readLine());
		}catch(Exception e){
			System.out.println("Something went wrong in ClientProtocol while reading validId..");
			e.printStackTrace();
		}
		return validId;
		
	}
}
