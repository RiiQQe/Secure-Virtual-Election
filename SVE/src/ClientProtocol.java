import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.net.ssl.SSLSocket;

public class ClientProtocol {
	
	public void sendMessage(SSLSocket socket, String msg ){
			
			try {
				PrintWriter socketOut = new PrintWriter( socket.getOutputStream(), true );
				socketOut.println(msg);
				socketOut.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	public String[] getMessage(SSLSocket socket){
		String[] messageArray = null;
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inStr = in.readLine();
			StringTokenizer st = new StringTokenizer(inStr);
			int i = 0;
			messageArray = new String[st.countTokens()];
			
			while(st.hasMoreTokens()){
				messageArray[i] = new String(st.nextToken());
				i++;
				System.out.println("st = " + st.toString());
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
