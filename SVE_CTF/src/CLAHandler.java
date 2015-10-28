import java.util.UUID;

import javax.net.ssl.SSLSocket;

/*
*   Author: Rickard & Michael
*   Liu-Student id: ricli877 & micsj823  
*/

/*
 * Class that handles connection between CTF and CLA
 */
public class CLAHandler implements Runnable{
	private SSLSocket socketToCla;
	private CTFProtocol protocol = new CTFProtocol();
	
	public CLAHandler(SSLSocket socketCla){
		this.socketToCla = socketCla;
	}
	
	public void run(){
		//Loop that receives validation numbers from CLA.
		while(true){
			try {
				UUID validId = protocol.getValidationId(socketToCla);
				//Only adds validation number if it does not already exist.
				if(ValIDNrContainer.instance().verifyNewNr(validId)){
					ValIDNrContainer.instance().addNr(validId);
					System.out.println("CTF received validationnumber " + validId +  " from CLA");
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}