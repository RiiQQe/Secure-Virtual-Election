/*
*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823
*   
*
*/

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.JOptionPane;

public class CLA {

	private static SSLServerSocket sss;
	
	private int port;
	// This is not a reserved port number
	static final int DEFAULT_CLIENT_PORT = 8189; //Client to CLA
	static final int DEFAULT_CTF_PORT = 8191; 	//CLA to CTF
	
	static final String KEYSTORE = "CLAkeystore.ks";
	static final String TRUSTSTORE = "CLAtruststore.ks";
	static final String STOREPASSWD = "password123";
	static final String ALIASPASSWD = "password123";
	
	OutputStream outS;
	
	CLA( int port ) {
		this.port = port;
	}
	
	public void run(){
		try {

		KeyStore ks = KeyStore.getInstance( "JCEKS" );
		ks.load( new FileInputStream( KEYSTORE ), STOREPASSWD.toCharArray() );

		KeyStore ts = KeyStore.getInstance( "JCEKS" );
		ts.load( new FileInputStream( TRUSTSTORE ), STOREPASSWD.toCharArray() );
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance( "SunX509" );
		kmf.init( ks, ALIASPASSWD.toCharArray() );
		
		TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
		tmf.init( ts );
			
		SSLContext sslContext = SSLContext.getInstance( "TLS" );
		sslContext.init( kmf.getKeyManagers(), tmf.getTrustManagers(), null );
		SSLServerSocketFactory sslServerFactory = sslContext.getServerSocketFactory();
		sss = (SSLServerSocket) sslServerFactory.createServerSocket( port );
		sss.setEnabledCipherSuites( sss.getSupportedCipherSuites() );

		System.out.println("\n>>>> CLAServer: active at port : " + port);
		 
		/*SSLSocket incoming = (SSLSocket)sss.accept();
                    
        incoming.setNeedClientAuth(true);
        
        //Declare array of certificates
        java.security.cert.Certificate[] peerCertificates;
        
        //See if certifcates was found or not
        try{
            peerCertificates = incoming.getSession().getPeerCertificates();
        }catch(SSLPeerUnverifiedException puv){
            peerCertificates = null;
        }
        //If there were no certificates found,
        //access to server is denied
        if(peerCertificates == null){
            System.out.println("Sorry, authentication failed");
        }else{
        	System.out.println("Authentication succeded");            
		}*/
		}catch( Exception x ) {
			System.out.println( x );
			x.printStackTrace();
		}
	}
	
	/** The test method for the class
	 * @param args[0] Optional port number in place of
	 *        the default
	 */
	public static void main( String[] args ) {
	    
		int clientPort = DEFAULT_CLIENT_PORT;
		int CTFPort = DEFAULT_CTF_PORT;
		InetAddress host;
		CLAtoCTF serverConn = null;
		
		Database db = Database.instance();
		
		/*
		 * Use this to connect with CTF 
		*/
		try{
			host = InetAddress.getLocalHost();
			serverConn = new CLAtoCTF(host, CTFPort);
			//System.out.println("Running CTF again");
			serverConn.run();
			//System.out.println("After..");
		}catch(UnknownHostException e){
			e.printStackTrace();
		}
		
		
		CLA addServer = new CLA( clientPort );
		
		addServer.run();
		
		while(true){
			try{
				SSLSocket socToClient = (SSLSocket)sss.accept();
				
				/*Glöm inte socToClient.setAutho(true)*/
				socToClient.setNeedClientAuth(true);
				(new Thread(new Sockets(socToClient, serverConn.getSocket()))).start();

				
			}catch(IOException e){
				System.out.println("Failed to authenticate client");
				
			}
		}
	
	
	}

}
