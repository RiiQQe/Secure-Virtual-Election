//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
///*
//*
//*   Author: Rickard & Michael
//*   Liu-Student id: ricli877 & micsj823
//*   
//*
//*/
//
//
//public class client {
//	//Testing
//	private int port;
//	private InetAddress host;
//	
//	static final int DEFAULT_PORT = 8189;
//	
//	public client(InetAddress host, int port){
//		this.host = host;
//		this.port = port;
//	}
//	
//	public void run(){
//		System.out.println("Client up'n running");
//	}
//	
//	public static void main(String args[]){
//		try {
//			InetAddress host = InetAddress.getLocalHost();
//			int port = DEFAULT_PORT;
//			
//			if ( args.length > 0 ) {
//				port = Integer.parseInt( args[0] );
//			}
//			if ( args.length > 1 ) {
//				host = InetAddress.getByName( args[1] );
//			}
//			client addClient = new client( host, port );
//			addClient.run();
//		}
//		catch ( UnknownHostException uhx ) {
//			System.out.println( uhx );
//			uhx.printStackTrace();
//		}
//	}
//}
