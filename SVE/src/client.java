import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.*;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/


public class client {
	//Testing
	private int port;
	private InetAddress host;
	
	private SSLSocket client;
	
	static final String KEYSTORE = "clientkeystore.ks";
	static final String TRUSTSTORE = "clienttruststore.ks";
	static final String STOREPASSWORD = "password123";
	static final String ALIASPASSWORD = "password123";
	
	static final int CLAClientPort = 8189;
	
	public client(InetAddress host, int port){
		this.host = host;
		this.port = port;
	}
	
	public void run(){
		
		try{
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
			client = (SSLSocket)sslFactory.createSocket(host, CLAClientPort);
			
			client.setEnabledCipherSuites( client.getEnabledCipherSuites() );
			
			client.getSession();
			
			System.out.println("Handshake completed");
			
			
		}catch(Exception x){
			System.out.println("Problem : " + x);
			x.printStackTrace();
		}
		
	}
	
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
			client addClient = new client( host, port );
			addClient.run();
		}
		catch ( UnknownHostException uhx ) {
			System.out.println( uhx );
			uhx.printStackTrace();
		}
	}
}
