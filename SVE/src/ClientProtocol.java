import java.io.IOException;
import java.io.PrintWriter;

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
}
