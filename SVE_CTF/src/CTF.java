import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/*
*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823
*   
*
*/

public class CTF {
	private int port;
	
	static final String KEYSTORE = "CTFkeystore.ks";
	static final String TRUSTSTORE =  "CTFtruststore.ks";
	static final String STOREPASSWD = "password123";
	static final String ALIASPASSWD = "password123";
	
	private static SSLServerSocket sss;
	
	CTF(int port){
		this.port = port;
	}
	
	public void run(){
		try{
			KeyStore ks = KeyStore.getInstance("JCEKS");
			ks.load(new FileInputStream(KEYSTORE), STOREPASSWD.toCharArray());
			
			KeyStore ts = KeyStore.getInstance("JCEKS");
			ts.load(new FileInputStream(TRUSTSTORE), STOREPASSWD.toCharArray());
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, ALIASPASSWD.toCharArray());
			
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ts);
			
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			SSLServerSocketFactory sslServerFactory = sslContext.getServerSocketFactory();
			sss = (SSLServerSocket) sslServerFactory.createServerSocket(port);
			sss.setEnabledCipherSuites(sss.getSupportedCipherSuites());
			
			System.out.println("\n>>>> CTFServer: active at port : " + port);
	
			
		}catch(EOFException e){
			System.out.println(e);
		}
		catch( Exception e ) {
			System.out.println( e );
			e.printStackTrace();
		}
	}
	
	static final int DEFAULT_CLIENT_PORT = 8190; // CTF to Client 8190
	static final int DEFAULT_CLA_PORT = 8191;	//CLA to CTF 8191
	
	public static void main(String[] args){
		int portClient = DEFAULT_CLIENT_PORT;
		int portCLA = DEFAULT_CLA_PORT;
		
		SSLSocket socketToCLA = null;
		
		ValidationNrContainer vc = ValidationNrContainer.instance();
		Tabulation tb = Tabulation.instance();
		
		CTF CTFtoCLA = new CTF(portCLA);
		CTFtoCLA.run();
		
		try{
			socketToCLA = (SSLSocket)sss.accept();
			//sss.setNeedClientAuth(true);
			System.out.println("CTF server socket to CLA established");
			(new Thread(new CLAHandler(socketToCLA))).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CTF CTFToClient = new CTF(portClient);
		CTFToClient.run();
		
		while(true){
			try {
				System.out.println("beefore");
				SSLSocket socketToClient = (SSLSocket)sss.accept();
				System.out.println("HEEJSAN");
				//sss.setNeedClientAuth(true);
				System.out.println("CTF server socket to Client established");
				(new Thread(new ClientHandler(socketToClient))).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		}
		
		
	}
}

