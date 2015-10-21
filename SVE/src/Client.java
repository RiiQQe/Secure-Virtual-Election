import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.*;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/


public class Client {
	
	private int port;
	private InetAddress host;
	
	private SSLSocket client;
	
	static final String KEYSTORE = "clientkeystore.ks";
	static final String TRUSTSTORE = "clienttruststore.ks";
	static final String STOREPASSWORD = "password123";
	static final String ALIASPASSWORD = "password123";
	
	static final int CLAClientPort = 8189;
	
	public Client(InetAddress host, int port){
		this.host = host;
		this.port = port;
	}
	
	
	public SSLSocket run(){
		
		try{
			
			System.out.println("Running client");
			KeyStore ks = KeyStore.getInstance("JCEKS");
			ks.load(new FileInputStream( KEYSTORE ), STOREPASSWORD.toCharArray() );
			
			KeyStore ts = KeyStore.getInstance("JCEKS");
			ts.load(new FileInputStream( TRUSTSTORE ), STOREPASSWORD.toCharArray() );

			KeyManagerFactory kmf = KeyManagerFactory.getInstance( "SUNX509" );
			kmf.init(ks, ALIASPASSWORD.toCharArray() );
			
			TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SUNX509" );
			tmf.init( ts );
			
			SSLContext sslContext = SSLContext.getInstance( "TLS" );
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers() , null);
			
			SSLSocketFactory sslFactory = sslContext.getSocketFactory();
			client = (SSLSocket)sslFactory.createSocket(host, port);
			
			client.setEnabledCipherSuites( client.getEnabledCipherSuites() );
			
			client.getSession();
			
			System.out.println("Handshake completed" + port);
		
			return client;
			

		}catch(Exception x){
			System.out.println("Problem : " + x);
			x.printStackTrace();
		}
		return null;
		
	}
	/*
	public static void main(String args[]){
		try {
			InetAddress host = InetAddress.getLocalHost();
			int port = CLAClientPort;
			
			if ( args.length > 0 ) {
				port = Integer.parseInt( args[0] );
			}
			if ( args.length > 1 ) {
				host = InetAddress.getByName( args[1] );
			}
			Client addClient = new Client( host, port );
			addClient.run();
		}
		catch ( UnknownHostException uhx ) {
			System.out.println( uhx );
			uhx.printStackTrace();
		}
	}*/
}
