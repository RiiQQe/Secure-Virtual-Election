import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.net.ssl.SSLSocket;

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
				
			}catch(IOException e){
				e.printStackTrace();
				
			}
			
		
		if(messageArray == null){
			System.out.println("ClientProtocol: messageArray == null");
		}
		return messageArray;

		
	}
}
