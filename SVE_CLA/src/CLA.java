/*
*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823
*   
*
*/

import java.io.*;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class CLA {

	private int port;
	// This is not a reserved port number
	static final int DEFAULT_PORT = 8189;
	static final String KEYSTORE = "serverkeystore.ks";
	static final String TRUSTSTORE = "servertruststore.ks";
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
			SSLServerSocket sss = (SSLServerSocket) sslServerFactory.createServerSocket( port );
			sss.setEnabledCipherSuites( sss.getSupportedCipherSuites() );
			
			System.out.println("\n>>>> SecureAdditionServer: active ");
                        
			SSLSocket incoming = (SSLSocket)sss.accept();
                        
                        incoming.setNeedClientAuth(true);

                        System.out.println("");
                        
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
                            System.out.println("Authentication OK!");
                            BufferedReader in = new BufferedReader( new InputStreamReader( incoming.getInputStream() ) );
                            PrintWriter out = new PrintWriter( incoming.getOutputStream(), true );		

                            FileOutputStream fos;
                            BufferedOutputStream bos;

                            //Input stream in bytes
                            InputStream is = incoming.getInputStream();
                            //Output stream in bytes
                            outS = incoming.getOutputStream(); 

                            byte[] fileName = new byte[100]; //Size of filename has a limit of 100

                            //Reads the filename from the stream
                            is.read(fileName, 0, fileName.length);

                            //Creating string from the bytes
                            String fileNameString = new String(fileName);

                            //Trimming the filename, in case there's a blankspace or something..
                            fileNameString = fileNameString.trim();

                            //The bytesize of the file
                            int size = is.read();

                            //whattodo tells the server what the client want to do
                            //1 = upload
                            //2 = download
                            //3 = delete
                            int whattodo = is.read();

                            if(whattodo == 1){                                      //Uploading to server
                                System.out.println("Uploading..");
                                //Byte array to store the file in
                                byte [] mybyteArrayToUpload = new byte [size];

                                //Reading the file from the stream
                                is.read(mybyteArrayToUpload, 0, size);

                                //Creating file to write to
                                fos = new FileOutputStream(fileNameString);

                                //Creating Stream to the file
                                bos = new BufferedOutputStream(fos);

                                //Write the content of file
                                bos.write(mybyteArrayToUpload,0,size);
                                fos.flush();
                                bos.flush();

                                fos.close();
                                bos.close();

                            }else if(whattodo == 2){                                //Downloading from server
                                System.out.println("Downloading..");

                                //Finding the file
                                File textfileToDownload = new File(fileNameString);

                                //Byte array to store the files content in
                                byte[] byteArrayToDownload = new byte[(int)textfileToDownload.length()];

                                FileInputStream fis = new FileInputStream(textfileToDownload);
                                BufferedInputStream bis = new BufferedInputStream(fis);

                                //Reading from file and storing in bytes array
                                int fileSize = bis.read(byteArrayToDownload, 0, byteArrayToDownload.length);

                                //Writing the size to the stream
                                outS.write(fileSize);

                                //Writing to the outstream
                                outS.write(byteArrayToDownload, 0, byteArrayToDownload.length);

                                outS.flush();

                            }else if(whattodo == 3){                                //Deleting from server
                                System.out.println("Deleting file..");

                                File file = new File(fileNameString);
                                file.delete();

                            }
                            else{
                                System.out.println("Something went wrong..");

                                incoming.close();
                            }    
                        }
                        
                        
                        /*
                        if(peerCertificates != null){
                            for (java.security.cert.Certificate peerCertificate : peerCertificates) {
                                System.out.println(peerCertificate);
                            }
                            
                            System.out.println("Lyckades");
                        }else{
                            System.out.println("Misslyckades");
                        }*/
                        
		}
		catch( Exception x ) {
			System.out.println( x );
			x.printStackTrace();
		}
	}
	
	/** The test method for the class
	 * @param args[0] Optional port number in place of
	 *        the default
	 */
	public static void main( String[] args ) {
	    
		int port = DEFAULT_PORT;
		if (args.length > 0 ) {
			port = Integer.parseInt( args[0] );
		}
		CLA addServe = new CLA( port );
		addServe.run();
	}

	//Kalle
}
