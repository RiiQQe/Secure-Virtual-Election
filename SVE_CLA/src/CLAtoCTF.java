import java.io.FileInputStream;
import java.net.InetAddress;
import java.security.KeyStore;

import javax.net.ssl.*;


public class CLAtoCTF {

	private InetAddress host;
	private int port;
	
	private SSLSocket CTFConn = null;
	
	static final String KEYSTORE = "CLAkeystore.ks";
	static final String TRUSTSTORE = "CLAtruststore.ks";	
	static final String KEYSTOREPASSWORD = "password123";
	static final String TRUSTSTOREPASSWORD = "password123";
	static final String ALIASPASSWORD = "password123";
	
	public CLAtoCTF(InetAddress host, int port){
		this.host = host;
		this.port = port;
	}
	
	
	public void run(){
		try{
			KeyStore ks = KeyStore.getInstance( "JCEKS" );
			ks.load(new FileInputStream( KEYSTORE ), KEYSTOREPASSWORD.toCharArray() );
			
			KeyStore ts = KeyStore.getInstance( "JCEKS" );
			ts.load(new FileInputStream( TRUSTSTORE ), TRUSTSTOREPASSWORD.toCharArray() );
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance( "SUNX509" );
			kmf.init(ks, ALIASPASSWORD.toCharArray() );
			
			TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SUNX509" );
			tmf.init(ts);
			
			SSLContext sslContext = SSLContext.getInstance( "TLS" );
			sslContext.init(kmf.getKeyManagers() , tmf.getTrustManagers() , null);
			SSLSocketFactory sslFactory = sslContext.getSocketFactory();
			System.out.println("HOST: " + host + " PORT: " + port);
			//HERE THE PROBLEM IS..
			
			CTFConn = (SSLSocket)sslFactory.createSocket(host, port);
			CTFConn.setEnabledCipherSuites( CTFConn.getEnabledCipherSuites() );
			
			System.out.println(">>>>> Handshake between CLA and CTF completed..");
			
		}catch( Exception x ){
			System.out.println("Problems in CLAtoCTF conn" + x);
			x.printStackTrace();
		}
	}
	public SSLSocket getSocket(){
		if(CTFConn == null) {
			System.out.println("Connection lost..");
			return null;
		}
		
		return CTFConn;
	}
	
}
