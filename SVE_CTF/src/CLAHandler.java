import java.util.UUID;

import javax.net.ssl.SSLSocket;

public class CLAHandler implements Runnable{
	private SSLSocket socketToCla;
	private CTFProtocol protocol = new CTFProtocol();
	
	public CLAHandler(SSLSocket socketCla){
		this.socketToCla = socketCla;
	}
	
	public void run(){
		while(true){
			try {
				System.out.println("Hejsan" + socketToCla);
				UUID verifyNr = protocol.getVerificationNr(socketToCla);
				if(ValidationNrContainer.instance().verifyNewNr(verifyNr)){
					ValidationNrContainer.instance().addNr(verifyNr);
					System.out.println("CTL recived validationnumber " + verifyNr+  " from CLA");
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}