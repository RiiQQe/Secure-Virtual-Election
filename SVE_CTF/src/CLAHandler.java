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
				UUID validId = protocol.getValidationId(socketToCla);
				if(ValidationNrContainer.instance().verifyNewNr(validId)){
					ValidationNrContainer.instance().addNr(validId);
					System.out.println("CTL recived validationnumber " + validId +  " from CLA");
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}